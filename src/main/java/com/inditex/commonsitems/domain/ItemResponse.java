package com.inditex.commonsitems.domain;

import com.inditex.commonsitems.dto.PriceDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.beans.Transient;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Getter
public class ItemResponse {

    private final int productId;
    private final int brandId;
    private final int priceList;
    private final String startDate;
    private final String endDate;
    private final double price;

    public static ItemResponse fromPriceDto(final PriceDto priceDto) {
        return new ItemResponse(priceDto.getProductId(), priceDto.getBrandId(),
                priceDto.getPriceList(), priceDto.getStartDate(), priceDto.getEndDate(),
                priceDto.getPrice());
    }

    @Transient
    public boolean isEmpty() {
        return this.getProductId() == 0 && this.getBrandId() == 0 && this.getPriceList() == 0
                && this.getPrice() == 0.0 && isEmptyDates();
    }

    @Transient
    private boolean isEmptyDates() {
        return this.getStartDate() == null && this.getEndDate() == null;
    }
}
