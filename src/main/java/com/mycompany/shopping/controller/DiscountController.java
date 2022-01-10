package com.mycompany.shopping.controller;

import com.mycompany.shopping.repository.DiscountRepository;
import com.mycompany.shopping.service.DiscountService;
import com.mycompany.shopping.service.dto.DiscountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DiscountController {

    private final Logger log = LoggerFactory.getLogger(DiscountController.class);

    private final DiscountService discountService;

    private final DiscountRepository discountRepository;

    public DiscountController(DiscountService discountService, DiscountRepository discountRepository) {
        this.discountService = discountService;
        this.discountRepository = discountRepository;
    }

    @GetMapping("/discount/{id}")
    public ResponseEntity<Optional<DiscountDto>> getDiscount(@PathVariable Long id) {
        log.debug("REST request to Discount : {}", id);
        Optional<DiscountDto> discountDto = discountService.findOne(id);
        return ResponseEntity.ok().body(discountDto);
    }

    @GetMapping("/discounts")
    public ResponseEntity<List<DiscountDto>> getAllDiscounts() {
        log.debug("REST request to all Discounts");
        List<DiscountDto> discountDtoList = discountService.findAll();
        return ResponseEntity.ok().body(discountDtoList);
    }

    @PostMapping("/discount")
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto discountDto) throws Exception {
        log.debug("REST request to create Discount");

        if (discountDto.getId() != null) {
            throw new Exception("A new discount cannot already have an ID");
        }

        DiscountDto discount = discountService.save(discountDto);
        return ResponseEntity
                .created(new URI("/api/discount/" + discount.getId()))
                .body(discount);
    }

    @PutMapping("/discount/{id}")
    public ResponseEntity<DiscountDto> updateDiscount(@PathVariable(value = "id", required = false) final Long id,
                                                  @RequestBody DiscountDto discountDto) throws Exception {
        log.debug("REST request to update Discount : {}", id);

        if (discountDto.getId() != null) {
            throw new Exception("A new discount cannot already have an ID");
        }
        if (!Objects.equals(id, discountDto.getId())) {
            throw new Exception("Invalid id");
        }
        if (!discountRepository.existsById(discountDto.getId())) {
            throw new Exception("Entity Not Found");
        }

        DiscountDto discount = discountService.save(discountDto);
        return ResponseEntity.ok().body(discount);
    }

    @DeleteMapping("/discount/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        log.debug("REST request to delete Discount : {}", id);
        discountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
