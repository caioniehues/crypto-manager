package com.pharmaparterns.cryptomanager.repository;

import com.pharmaparterns.cryptomanager.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
