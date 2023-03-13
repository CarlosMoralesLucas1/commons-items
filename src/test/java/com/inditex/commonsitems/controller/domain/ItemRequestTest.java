package com.inditex.commonsitems.controller.domain;

import com.inditex.commonsitems.domain.ItemRequest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ItemRequestTest {

    @Test
    public void itShouldAllowValidDateProductAndBrandId() {
        final var itemRequest = new ItemRequest("2020-06-14-00.00.00", 35455, 1);
        assertEquals(itemRequest.toString(), "ItemRequest{date='2020-06-14-00.00.00', productId=35455, brandId=1}");
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Invalid date: 2020-06-14-00 00.00. Valid dates need to follow the " +
                    "format yyyy-MM-dd-HH.mm.ss")
    public void itShouldThrowExceptionIfMalformedDateGiven() {
        new ItemRequest("2020-06-14-00 00.00", 35455, 1);
    }
}