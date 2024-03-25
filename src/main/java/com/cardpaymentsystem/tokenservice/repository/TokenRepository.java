package com.cardpaymentsystem.tokenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cardpaymentsystem.tokenservice.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

}
