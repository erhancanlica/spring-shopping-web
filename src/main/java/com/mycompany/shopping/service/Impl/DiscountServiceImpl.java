package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.domain.Discount;
import com.mycompany.shopping.repository.DiscountRepository;
import com.mycompany.shopping.service.dto.DiscountDto;
import com.mycompany.shopping.service.mapper.DiscountMapper;
import com.mycompany.shopping.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;

    private final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountDto> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findById(id).map(discountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountDto> findAll() {
        log.debug("Request to get all Discounts");
        return discountRepository.findAll().stream().map(discountMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DiscountDto save(DiscountDto discountDto) {
        log.debug("Request to save Discount : {}", discountDto);
        Discount discount = discountMapper.toEntity(discountDto);
        discount = discountRepository.save(discount);
        return discountMapper.toDto(discount);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        discountRepository.deleteById(id);
    }
}
