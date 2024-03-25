package com.cardpaymentsystem.tokenservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Token {

	@Id
	private String token;
	private LocalDateTime createdDate;

	@PrePersist
	public void prePersist() {

		this.createdDate = LocalDateTime.now();
	}
}
