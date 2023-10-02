package com.example.stocksdatamanager.model;

import lombok.*;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "stocks")
public class Stock {

    @Id
    String symbol;

    double latestPrice;

    double change;

    String companyName;

    Integer previousVolume;

    Integer volume;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        Stock that = (Stock) o;
        return symbol != null && symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return symbol == null ? 0 : symbol.hashCode();
    }
}