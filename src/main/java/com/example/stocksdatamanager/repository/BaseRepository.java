package com.example.stocksdatamanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import static com.example.stocksdatamanager.util.ValidationUtil.checkModification;
import static com.example.stocksdatamanager.util.ValidationUtil.checkNotFoundWithSymbol;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.symbol=:symbol")
    int delete(String symbol);

    default void deleteExisted(String id) {
        checkModification(delete(id), id);
    }

    default T getExisted(String id) {
        return checkNotFoundWithSymbol(findById(id), id);
    }
}