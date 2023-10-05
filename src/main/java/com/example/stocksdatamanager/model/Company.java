package com.example.stocksdatamanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "companies")
@SQLInsert(sql = "SELECT upsert_company(?, ?, ?, ?)")
@SQLUpdate(sql = "SELECT upsert_company(?, ?, ?, ?)")
public class Company {

    @Id
    @GeneratedValue(generator = "custom_id_generator",
            strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "custom_id_generator",
            strategy = "com.example.stocksdatamanager.model.id.generator.BaseIdentifierGenerator")
    String id;

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
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}