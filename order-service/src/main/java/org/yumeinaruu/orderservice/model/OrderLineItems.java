package org.yumeinaruu.orderservice.model;

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

@Entity(name = "order_line_items")
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {
    @Id
    @SequenceGenerator(name = "orderLineItemsIdSeqGen", sequenceName = "order_line_items_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "orderLineItemsIdSeqGen")
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
