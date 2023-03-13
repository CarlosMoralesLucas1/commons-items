package com.inditex.commonsitems.service;

import com.inditex.commonsitems.domain.ItemRequest;
import com.inditex.commonsitems.domain.ItemResponse;
import com.inditex.commonsitems.dto.PriceDto;
import com.inditex.commonsitems.repository.ItemsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
@Slf4j
public class ItemsService {

    private static final int HIGH_PRIORITY = 1;

    @Autowired
    private ItemsRepository itemsRepository;

    public Optional<ItemResponse> retrieveItemData(final ItemRequest itemRequest) {
        final var priceDtoList = itemsRepository.findByProductIdAndBrandId(itemRequest.getProductId(),
                itemRequest.getBrandId());
        log.info(String.format("%s: retrieved: %s", this.getClass().getSimpleName(), priceDtoList));
        final var result = calculateClosestDateItemResponse(itemRequest.getDate(), priceDtoList);
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    private ItemResponse calculateClosestDateItemResponse(final String date, final List<PriceDto> priceDtoList) {
        ItemResponse itemResponse = null;
        try {
            final Date currentDate = new SimpleDateFormat(ItemRequest.VALID_DATE_PATTERN).parse(date);
            final List<PriceDto> possiblePriceDtoList = new ArrayList<>();
            final PriceDto priceDtoResult = filterPossiblePriceDtos(priceDtoList, currentDate, possiblePriceDtoList);
            itemResponse = ItemResponse.fromPriceDto(priceDtoResult);
        } catch (ParseException e) {
            log.warn(String.format("%s: error parsing invalid date: %s", this.getClass().getSimpleName(), date));
        }
        return itemResponse;
    }

    private PriceDto filterPossiblePriceDtos(final List<PriceDto> priceDtoList, final Date currentDate,
                                             final List<PriceDto> possiblePriceDtoList) throws ParseException {
        for (final PriceDto currentPriceDto : priceDtoList) {
            if (isWithinDateRange(currentDate, currentPriceDto)) {
                possiblePriceDtoList.add(currentPriceDto);
            }
        }
        return possiblePriceDtoList.isEmpty() ? new PriceDto() : selectHighestPriorityPriceDto(possiblePriceDtoList);
    }

    private static PriceDto selectHighestPriorityPriceDto(final List<PriceDto> possiblePriceDtoList) {
        final Predicate<PriceDto> highestPriorityPriceDtoPredicate = i -> i.getPriority() == HIGH_PRIORITY;
        return possiblePriceDtoList.size() == 1 ? possiblePriceDtoList.get(0) : possiblePriceDtoList.stream().
                filter(highestPriorityPriceDtoPredicate).findFirst().get();
    }

    private boolean isWithinDateRange(final Date currentDate, final PriceDto currentPriceDto) throws ParseException {
        final Date startDate = new SimpleDateFormat(ItemRequest.VALID_DATE_PATTERN).parse(currentPriceDto.getStartDate());
        final Date endDate = new SimpleDateFormat(ItemRequest.VALID_DATE_PATTERN).parse(currentPriceDto.getEndDate());
        return currentDate.after(startDate) && currentDate.before(endDate);
    }
}
