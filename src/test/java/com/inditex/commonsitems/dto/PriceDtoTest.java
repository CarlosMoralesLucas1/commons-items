package com.inditex.commonsitems.dto;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PriceDtoTest {

    @Test
    public void itShouldMaintainPriceDtoRepresentation() {
        final long id = 34L;
        final int brandId = 1;
        final String startDate = "2020-06-15-00.00.00";
        final String endDate = "2020-06-15-11.00.00";
        final int productId = 35455;
        final int priceList = 1;
        final int priority = 1;
        final double price = 30.5;
        final String currency = "EUR";

        final PriceDto priceDto = new PriceDto(id, brandId, startDate, endDate,
                productId, priceList, priority, price, currency);
        assertEquals(priceDto.toString(), "PriceDto(id=34, brandId=1, startDate=2020-06-15-00.00.00, " +
                "endDate=2020-06-15-11.00.00, productId=35455, priceList=1, priority=1, price=30.5, currency=EUR)");
    }

}