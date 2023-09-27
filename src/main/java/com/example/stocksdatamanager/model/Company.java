package com.example.stocksdatamanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "companies")
public class Company {

    @Id
    String symbol;

    String name;

    @JsonProperty("isEnabled")
    boolean isEnabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        Company that = (Company) o;
        return symbol != null && symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return symbol == null ? 0 : symbol.hashCode();
    }
}