package com.cardpaymentsystem.tokenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cardpaymentsystem.tokenservice.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

	boolean existsByHashedCardNumber(String hashedCardNumber);
}
