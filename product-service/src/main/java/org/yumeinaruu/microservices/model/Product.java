package org.yumeinaruu.microservices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Entity(name = "product")
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "productIdSeqGen", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "productIdSeqGen")
    private Long id;


    private String name;

    private String description;

    private BigDecimal price;
}
