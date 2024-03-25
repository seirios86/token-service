package com.cardpaymentsystem.tokenservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {

	private String cardRefId;
	private String userCi;
	private String tokenIv;
}
