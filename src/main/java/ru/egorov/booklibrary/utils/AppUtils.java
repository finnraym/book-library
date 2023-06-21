package ru.egorov.booklibrary.utils;

import org.springframework.data.domain.Sort;

public class AppUtils {

    public static Sort getSort(String value, String sortDir) {
        return sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(value).ascending()
                : Sort.by(value).descending();
    }
}
