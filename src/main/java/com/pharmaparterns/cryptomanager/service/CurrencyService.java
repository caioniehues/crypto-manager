package com.pharmaparterns.cryptomanager.service;

import com.pharmaparterns.cryptomanager.model.Currency;
import com.pharmaparterns.cryptomanager.repository.CurrencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

/**
 * This class encapsulates and executes the CurrencyController methods.
 */
@Service
@Transactional
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * This is a method that returns all the currencies and utilizes pagination.
     *
     * @param pageable The object with all the pagination parameters.
     * @return  The response entity with the query results.
     */
    public ResponseEntity<Page<Currency>> getAllCurrencies(Pageable pageable){
        Page<Currency> currencies = currencyRepository.findAll(pageable);
        return  ResponseEntity.ok(currencies);
    }

    /**
     * This is a method that searches for the currency by id.
     *
     * @param id The id for the requested object.
     * @return  The searched currency, if exists.
     */
    public ResponseEntity<Currency> getCurrencyById(Long id){
        Currency currency = currencyRepository.findById(id).orElse(null);
        if (currency == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(currency);
    }

    /**
     * This is a method that creates and persists a currency in the database.
     *
     * @param newCurrency The object with all the new currency information.
     * @return  The persisted entity.
     */
    public ResponseEntity<Currency> createCurrency(Currency newCurrency){
        Currency currency = new Currency.Builder()
                .withTicker(newCurrency.getTicker())
                .withName(newCurrency.getName())
                .withMarketCap(newCurrency.getMarketCap())
                .withNumberOfCoins(newCurrency.getNumberOfCoins())
                .build();
        Currency savedCurrency = currencyRepository.save(currency);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCurrency);
    }

    /**
     * This is a method that updates a currency, if it exists.
     *
     * @param id The id of the object to be updated.
     * @param updatedCurrency The object containing the new information to be persisted.
     * @return  The updated entity.
     */
    public ResponseEntity<Currency> updateCurrency(Long id, Currency updatedCurrency){
        Currency currency = currencyRepository.findById(id).orElse(null);
        if (currency == null){
            return ResponseEntity.notFound().build();
        }
        currency.setTicker(updatedCurrency.getTicker());
        currency.setName(updatedCurrency.getName());
        currency.setNumberOfCoins(updatedCurrency.getNumberOfCoins());
        currency.setMarketCap(updatedCurrency.getMarketCap());
        Currency savedCurrency = currencyRepository.save(currency);
        return ResponseEntity.ok(savedCurrency);
    }

    /**
     * This is a method that deletes a currency.
     *
     * @param id The id of the object to be deleted.
     * @return  If the entity exists, returns HTTP status code 204. If not, HTTP status code 404.
     */
    public ResponseEntity<Void> deleteCurrency(Long id){
        Currency currency = currencyRepository.findById(id).orElse(null);
        if (currency == null){
            return ResponseEntity.notFound().build();
        }
        currencyRepository.delete(currency);
        return ResponseEntity.noContent().build();
    }
}
