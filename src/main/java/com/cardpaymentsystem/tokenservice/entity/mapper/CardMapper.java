package com.cardpaymentsystem.tokenservice.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cardpaymentsystem.tokenservice.controller.dto.CardDto;
import com.cardpaymentsystem.tokenservice.entity.Card;

@Mapper
public interface CardMapper {

	CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

	@Mapping(source = "cardRefId", target = "cardRefId")
	@Mapping(source = "encryptedCardNumber", target = "encryptedCardNumber")
	@Mapping(source = "expirationYearMonth", target = "expirationYearMonth")
	@Mapping(source = "encryptedUserCi", target = "encryptedUserCi")
	@Mapping(source = "createdDate", target = "createdDate")
	@Mapping(source = "updatedDate", target = "updatedDate")
	CardDto toDto(Card card);
}
