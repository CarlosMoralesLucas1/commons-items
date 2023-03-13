package com.inditex.commonsitems.controller;

import com.inditex.commonsitems.domain.ItemRequest;
import com.inditex.commonsitems.domain.ItemResponse;
import com.inditex.commonsitems.service.ItemsService;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

public class ItemsControllerTest {

    private ItemsController itemsController;

    @Mock
    private ItemsService itemsServiceMock;

    @Mock
    private ItemRequest itemRequestMock;

    @Mock
    private ItemResponse itemResponseMock;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
        this.itemsController = new ItemsController(itemsServiceMock);
    }

    @Test
    public void itShouldReturnOkIfFound() {
        when(itemsServiceMock.retrieveItemData(any(ItemRequest.class))).thenReturn(Optional.of(itemResponseMock));
        callControllerAndExpectResponse(ResponseEntity.ok(itemResponseMock));
    }

    @Test
    public void itShouldReturnNoContentIfNotFound() {
        when(itemsServiceMock.retrieveItemData(any(ItemRequest.class))).thenReturn(Optional.empty());
        callControllerAndExpectResponse(ResponseEntity.noContent().build());
    }

    private void callControllerAndExpectResponse(final ResponseEntity expectedResponse) {
        final var itemResponseEntity = this.itemsController.retrieveItemData(itemRequestMock);
        verify(this.itemsServiceMock).retrieveItemData(itemRequestMock);
        verifyNoMoreInteractions(this.itemsServiceMock);
        assertEquals(itemResponseEntity, expectedResponse);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Invalid ItemRequest received. ItemRequest cannot be null.")
    public void itShouldNotForwardInvalidItemRequestToService() {
        this.itemsController.retrieveItemData(null);
        verifyNoInteractions(itemsServiceMock);
    }
}