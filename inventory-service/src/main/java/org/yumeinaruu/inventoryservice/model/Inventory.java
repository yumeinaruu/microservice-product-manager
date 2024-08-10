package org.yumeinaruu.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Entity(name = "inventory")
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @SequenceGenerator(name = "inventoryIdSeqGen", sequenceName = "inventory_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "inventoryIdSeqGen")
    private Long id;
    private String skuCode;
    private Integer quantity;
}
