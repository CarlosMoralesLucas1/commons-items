package com.inditex.commonsitems.service;

import com.inditex.commonsitems.domain.ItemRequest;
import com.inditex.commonsitems.domain.ItemResponse;
import com.inditex.commonsitems.dto.PriceDto;
import com.inditex.commonsitems.repository.ItemsRepository;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class ItemsServiceTest {

    private static final int PRODUCT_ID = 35455;
    private static final int BRAND_ID = 1;
    private ItemsService itemsService;

    @Mock
    private ItemsRepository itemsRepositoryMock;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
        this.itemsService = new ItemsService(itemsRepositoryMock);
    }

    @Test
    public void itShouldReturnExpectedItemWithNoPriority() {
        final var itemRequest = new ItemRequest("2020-06-14-10.00.00", PRODUCT_ID, BRAND_ID);
        final var expectedItemResponse = new ItemResponse(PRODUCT_ID, BRAND_ID, 1,
                "2020-06-14-00.00.00", "2020-12-31-23.59.59", 35.5);
        when(itemsRepositoryMock.findByProductIdAndBrandId(anyInt(), anyInt())).thenReturn(assemblePriceDtoList());
        final var itemResponseOptional = this.itemsService.retrieveItemData(itemRequest);
        verify(itemsRepositoryMock).findByProductIdAndBrandId(itemRequest.getProductId(), itemRequest.getBrandId());
        verifyNoMoreInteractions(itemsRepositoryMock);
        assertFalse(itemResponseOptional.isEmpty());
        assertEquals(itemResponseOptional.get(), expectedItemResponse);
    }

    @Test
    public void itShouldReturnExpectedItemWithPriority() {
        final var itemRequest = new ItemRequest("2020-06-14-16.00.00", PRODUCT_ID, BRAND_ID);
        final var expectedItemResponse = new ItemResponse(PRODUCT_ID, BRAND_ID, 2,
                "2020-06-14-15.00.00", "2020-06-14-18.30.00", 25.45);
        when(itemsRepositoryMock.findByProductIdAndBrandId(anyInt(), anyInt())).thenReturn(assemblePriceDtoList());
        final var itemResponseOptional = this.itemsService.retrieveItemData(itemRequest);
        verify(itemsRepositoryMock).findByProductIdAndBrandId(itemRequest.getProductId(), itemRequest.getBrandId());
        verifyNoMoreInteractions(itemsRepositoryMock);
        assertFalse(itemResponseOptional.isEmpty());
        assertEquals(itemResponseOptional.get(), expectedItemResponse);
    }

    @Test
    public void itShouldReturnEmptyItemResponseIfNoUnmatchedDate() {
        final var itemRequest = new ItemRequest("2020-04-11-08.30.00", PRODUCT_ID, BRAND_ID);
        when(itemsRepositoryMock.findByProductIdAndBrandId(anyInt(), anyInt())).thenReturn(assemblePriceDtoList());
        final var itemResponseOptional = this.itemsService.retrieveItemData(itemRequest);
        verify(itemsRepositoryMock).findByProductIdAndBrandId(itemRequest.getProductId(), itemRequest.getBrandId());
        verifyNoMoreInteractions(itemsRepositoryMock);
        assertEquals(itemResponseOptional, Optional.empty());
    }

    private List<PriceDto> assemblePriceDtoList() {
        final var priceDtoWithoutPriority = new PriceDto(35L, BRAND_ID, "2020-06-14-00.00.00",
                "2020-12-31-23.59.59", PRODUCT_ID, 1, 0, 35.5, "EUR");
        final var priceDtoWithPriority = new PriceDto(36L, BRAND_ID, "2020-06-14-15.00.00",
                "2020-06-14-18.30.00", PRODUCT_ID, 2, 1, 25.45, "EUR");
        return List.of(priceDtoWithoutPriority, priceDtoWithPriority);
    }
}