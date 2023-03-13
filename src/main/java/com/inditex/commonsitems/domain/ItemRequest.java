package com.inditex.commonsitems.domain;

import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
public class ItemRequest {

    public static final String VALID_DATE_PATTERN = "yyyy-MM-dd-HH.mm.ss";
    private String date;
    private final int productId;
    private final int brandId;

    public ItemRequest(final String date, final int productId, final int brandId) {
        this.setDate(date);
        this.productId = productId;
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return "ItemRequest{" +
                "date='" + date + '\'' +
                ", productId=" + productId +
                ", brandId=" + brandId +
                '}';
    }

    private void setDate(final String date) {
        validateDate(date);
        this.date = date;
    }

    private void validateDate(final String date) {
        try {
            new SimpleDateFormat(VALID_DATE_PATTERN).parse(date);
        } catch (ParseException pe) {
            final var errorMessage = String.format("Invalid date: %s. Valid dates need to follow the " +
                    "format %s", date, VALID_DATE_PATTERN);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
