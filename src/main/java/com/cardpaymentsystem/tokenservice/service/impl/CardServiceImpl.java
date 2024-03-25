package com.cardpaymentsystem.tokenservice.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cardpaymentsystem.tokenservice.controller.dto.CardDto;
import com.cardpaymentsystem.tokenservice.controller.dto.CardRequestDto;
import com.cardpaymentsystem.tokenservice.controller.dto.CardResponseDto;
import com.cardpaymentsystem.tokenservice.entity.Card;
import com.cardpaymentsystem.tokenservice.entity.mapper.CardMapper;
import com.cardpaymentsystem.tokenservice.exception.NotFoundException;
import com.cardpaymentsystem.tokenservice.exception.ValidationException;
import com.cardpaymentsystem.tokenservice.repository.CardRepository;
import com.cardpaymentsystem.tokenservice.service.CardService;
import com.cardpaymentsystem.tokenservice.util.EncryptionUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

	private final CardRepository cardRepository;
	private final EncryptionUtil encryptionUtil;

	public CardResponseDto registerCard(CardRequestDto cardRequestDto) throws Exception {

		validateRegisterCard(cardRequestDto);

		String cleanedCardNumber = cardRequestDto.getCardNumber().replaceAll("[^0-9]+", "");
		String hashedCardNumber = encryptionUtil.hash(cleanedCardNumber);
		if (cardRepository.existsByHashedCardNumber(hashedCardNumber)) {
			throw new ValidationException("Card already registered");
		}

		Card card = Card.builder()
			.encryptedCardNumber(encryptionUtil.encrypt(cleanedCardNumber))
			.hashedCardNumber(hashedCardNumber)
			.expirationYearMonth(cardRequestDto.getExpirationYearMonth())
			.encryptedUserCi(encryptionUtil.encrypt(cardRequestDto.getUserCi()))
			.build();

		return CardResponseDto.builder()
			.cardRefId(cardRepository.save(card).getCardRefId())
			.build();
	}

	public CardDto searchCard(String cardRefId) throws Exception {

		if (cardRefId == null || cardRefId.isBlank()) {
			throw new ValidationException("No card reference ID");
		}

		Optional<Card> optionalCard = cardRepository.findById(cardRefId);
		if (optionalCard.isEmpty()) {
			throw new NotFoundException("No result found");
		}

		return CardMapper.INSTANCE.toDto(optionalCard.get());
	}

	public void validateRegisterCard(CardRequestDto cardRequestDto) throws Exception {

		String cardNumber = cardRequestDto.getCardNumber();
		if (cardNumber == null || cardNumber.isBlank()) {
			throw new ValidationException("No card number");
		}

		String expirationYearMonth = cardRequestDto.getExpirationYearMonth();
		if (expirationYearMonth == null || expirationYearMonth.isBlank()) {
			throw new ValidationException("No expiration month/year");
		}

		String userCi = cardRequestDto.getUserCi();
		if (userCi == null || userCi.isBlank()) {
			throw new ValidationException("No user CI");
		}
	}
}
