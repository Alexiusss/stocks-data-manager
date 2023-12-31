package com.example.stocksdatamanager.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "stocks")
@SQLInsert(sql = "SELECT upsert_stock(?,?,?,?,?,?,?,?)")
@SQLUpdate(sql = "SELECT upsert_stock(?,?,?,?,?,?,?,?)")
public class Stock {

    @Id
    @GeneratedValue(generator = "custom_generator",
            strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "custom_generator",
            strategy = "com.example.stocksdatamanager.model.id.generator.BaseIdentifierGenerator")
    String id;

    String symbol;

    double latestPrice;

    double change;

    String companyName;

    Integer previousVolume;

    Integer volume;

    String companyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        Stock that = (Stock) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}