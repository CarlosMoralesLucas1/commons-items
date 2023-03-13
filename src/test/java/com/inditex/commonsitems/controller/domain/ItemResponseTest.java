package com.inditex.commonsitems.controller.domain;

import com.inditex.commonsitems.domain.ItemResponse;
import com.inditex.commonsitems.dto.PriceDto;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ItemResponseTest {

    @Test
    public void itShouldAllowNeededFields() {
        final ItemResponse itemResponse = new ItemResponse(35455, 1, 3,
                "2020-06-15-00.00.00", "2020-06-15-11.00.00", 30.50);
        assertEquals(itemResponse.toString(), "ItemResponse(productId=35455, brandId=1, priceList=3, " +
                "startDate=2020-06-15-00.00.00, endDate=2020-06-15-11.00.00, price=30.5)");
    }

    @Test
    public void itShouldBeAbleToTranslateFromPriceDto() {
        final PriceDto priceDto = new PriceDto(34L, 1, "2020-06-15-00.00.00",
                "2020-06-15-11.00.00", 35455, 1, 1, 30.5, "EUR");
        final ItemResponse expectedItemResponse = new ItemResponse(35455, 1, 1,
                "2020-06-15-00.00.00", "2020-06-15-11.00.00", 30.5);
        final ItemResponse actualItemResponse = ItemResponse.fromPriceDto(priceDto);
        assertEquals(actualItemResponse, expectedItemResponse);
    }

    @Test
    public void itShouldDetermineIfIsEmpty() {
        final ItemResponse itemResponse = new ItemResponse(0, 0, 0, null, null, 0.0);
        assertTrue(itemResponse.isEmpty());
    }
}