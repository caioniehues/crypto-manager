package com.pharmaparterns.cryptomanager.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "currencies")
@SequenceGenerator(name = "seq_generator", sequenceName = "SEQ_CURRENCY", initialValue = 5, allocationSize = 1)
public class Currency {

    @Id
    @GeneratedValue(generator = "seq_generator",strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "number_of_coins")
    private BigDecimal numberOfCoins;

    @Column(nullable = false, name = "market_cap")
    private BigDecimal marketCap;

    public Currency(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(BigDecimal numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", numberOfCoins=" + numberOfCoins +
                ", marketCap=" + marketCap +
                '}';
    }

    public static class Builder {
        private Currency currency;

        public Builder(){
            this.currency = new Currency();
        }

        public Builder withTicker(String ticker){
            this.currency.ticker = ticker;
            return this;
        }

        public Builder withName(String name){
            this.currency.name = name;
            return this;
        }

        public Builder withNumberOfCoins(BigDecimal numberOfCoins){
            this.currency.numberOfCoins = numberOfCoins;
            return this;
        }

        public Builder withMarketCap(BigDecimal marketCap){
            this.currency.marketCap = marketCap;
            return this;
        }

        public Currency build(){
            return this.currency;
        }
    }
}
