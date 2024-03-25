package com.cardpaymentsystem.tokenservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String cardRefId;
	private String encryptedCardNumber;
	private String hashedCardNumber;
	private String expirationYearMonth;
	private String encryptedUserCi;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	@PrePersist
	public void prePersist() {

		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {

		this.updatedDate = LocalDateTime.now();
	}
}
