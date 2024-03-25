package com.cardpaymentsystem.tokenservice.service;

import com.cardpaymentsystem.tokenservice.controller.dto.CardDto;
import com.cardpaymentsystem.tokenservice.controller.dto.CardRequestDto;
import com.cardpaymentsystem.tokenservice.controller.dto.CardResponseDto;

public interface CardService {

	CardResponseDto registerCard(CardRequestDto cardRequestDto) throws Exception;

	CardDto searchCard(String cardRefId) throws Exception;
}
