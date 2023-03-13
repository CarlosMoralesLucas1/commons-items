package com.inditex.commonsitems.repository;

import com.inditex.commonsitems.dto.PriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<PriceDto, Long> {

    List<PriceDto> findByProductIdAndBrandId(int productId, int brandId);
}
