package com.example.stocksdatamanager.model;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "stocks_audit_log")
@TypeDef(name = "jsonb", typeClass = JsonType.class)
public class StockAuditLog {

    @Id
    String companyId;

    DmlType dmlType;

    Instant createdAt;

    // https://vladmihalcea.com/how-to-map-json-collections-using-jpa-and-hibernate/
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    Stock oldStockData;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    Stock newStockData;
}