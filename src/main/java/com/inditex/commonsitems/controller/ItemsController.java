package com.inditex.commonsitems.controller;

import com.inditex.commonsitems.domain.ItemRequest;
import com.inditex.commonsitems.domain.ItemResponse;
import com.inditex.commonsitems.service.ItemsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @PostMapping(value = "/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemResponse> retrieveItemData(@RequestBody final ItemRequest itemRequest) {
        log.info(String.format("%s: item received: %s", this.getClass().getSimpleName(), itemRequest));
        validateItemRequest(itemRequest);
        final var result = this.itemsService.retrieveItemData(itemRequest);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result.get());
    }

    private void validateItemRequest(final ItemRequest itemRequest) {
        if (null == itemRequest) {
            throw new IllegalArgumentException("Invalid ItemRequest received. ItemRequest cannot be null.");
        }
    }
}
