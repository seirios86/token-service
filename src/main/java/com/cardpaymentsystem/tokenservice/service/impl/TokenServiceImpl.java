package com.cardpaymentsystem.tokenservice.service.impl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cardpaymentsystem.tokenservice.controller.dto.TokenRequestDto;
import com.cardpaymentsystem.tokenservice.controller.dto.TokenResponseDto;
import com.cardpaymentsystem.tokenservice.controller.dto.TokenValidationDto;
import com.cardpaymentsystem.tokenservice.entity.Card;
import com.cardpaymentsystem.tokenservice.entity.Token;
import com.cardpaymentsystem.tokenservice.exception.NotFoundException;
import com.cardpaymentsystem.tokenservice.exception.ValidationException;
import com.cardpaymentsystem.tokenservice.repository.CardRepository;
import com.cardpaymentsystem.tokenservice.repository.TokenRepository;
import com.cardpaymentsystem.tokenservice.service.TokenService;
import com.cardpaymentsystem.tokenservice.util.EncryptionUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final CardRepository cardRepository;
	private final TokenRepository tokenRepository;
	private final EncryptionUtil encryptionUtil;

	public TokenResponseDto createToken(TokenRequestDto tokenRequestDto) throws Exception {

		validateCreateToken(tokenRequestDto);

		Optional<Card> optionalCard = cardRepository.findById(tokenRequestDto.getCardRefId());
		if (optionalCard.isEmpty()) {
			throw new NotFoundException("Card not found");
		}

		Card foundCard = optionalCard.get();
		if (!tokenRequestDto.getUserCi().equals(encryptionUtil.decrypt(foundCard.getEncryptedUserCi()))) {
			throw new ValidationException("User CI does not match");
		}

		String cardInfo = foundCard.getEncryptedCardNumber() + "|" + foundCard.getExpirationYearMonth();
		String token = encryptionUtil.encrypt(cardInfo, tokenRequestDto.getTokenIv());

		tokenRepository.save(Token.builder().token(token).build());

		return TokenResponseDto.builder()
			.token(token)
			.build();
	}

	public void validateCreateToken(TokenRequestDto tokenRequestDto) throws Exception {

		String cardRefId = tokenRequestDto.getCardRefId();
		if (cardRefId == null) {
			throw new ValidationException("No card reference ID");
		}

		String tokenIv = tokenRequestDto.getTokenIv();
		if (tokenIv == null || tokenIv.isBlank()) {
			throw new ValidationException("No IV for token");
		}
	}

	public TokenValidationDto verifyToken(String token) throws Exception {

		if (token == null || token.isBlank()) {
			throw new ValidationException("No token");
		}
		String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);

		return TokenValidationDto.builder()
			.isValid(tokenRepository.existsById(decodedToken))
			.build();
	}

	public void deleteToken(String token) throws Exception {

		if (token == null || token.isBlank()) {
			throw new ValidationException("No token");
		}
		String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
		if (!tokenRepository.existsById(decodedToken)) {
			throw new NotFoundException("Token not found");
		}
		tokenRepository.deleteById(decodedToken);
	}
}
