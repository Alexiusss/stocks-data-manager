package com.example.stocksdatamanager.util;

import com.example.stocksdatamanager.error.IllegalRequestDataException;
import com.example.stocksdatamanager.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ValidationUtil {

    public static void checkModification(int count, String id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static <T> T checkNotFoundWithSymbol(Optional<T> optional, String symbol) {
        return optional.orElseThrow(() -> new NotFoundException("Entity with id=" + symbol + " not found"));
    }
}