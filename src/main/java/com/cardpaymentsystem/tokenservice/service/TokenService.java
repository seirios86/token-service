package com.cardpaymentsystem.tokenservice.service;

import com.cardpaymentsystem.tokenservice.controller.dto.TokenRequestDto;
import com.cardpaymentsystem.tokenservice.controller.dto.TokenResponseDto;
import com.cardpaymentsystem.tokenservice.controller.dto.TokenValidationDto;

public interface TokenService {

	TokenResponseDto createToken(TokenRequestDto tokenRequestDto) throws Exception;

	TokenValidationDto verifyToken(String token) throws Exception;

	void deleteToken(String token) throws Exception;
}
