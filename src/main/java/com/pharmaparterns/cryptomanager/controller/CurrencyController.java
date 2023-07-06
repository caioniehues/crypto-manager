package com.pharmaparterns.cryptomanager.controller;

import com.pharmaparterns.cryptomanager.model.Currency;
import com.pharmaparterns.cryptomanager.repository.CurrencyRepository;
import com.pharmaparterns.cryptomanager.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This class is responsible for handling HTTP requests and responses.
 */
@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    public CurrencyController(CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<Page<Currency>> getAllCurrencies(Pageable pageable){
        return  currencyService.getAllCurrencies(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable Long id){
        return currencyService.getCurrencyById(id);
    }

    @PostMapping
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency newCurrency){
        return currencyService.createCurrency(newCurrency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable Long id, @RequestBody Currency updatedCurrency){
        return currencyService.updateCurrency(id, updatedCurrency);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id){
       return currencyService.deleteCurrency(id);
    }
}
