package org.yumeinaruu.microservices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yumeinaruu.microservices.order.event.OrderPlacedEvent;
import org.yumeinaruu.microservices.model.Order;
import org.yumeinaruu.microservices.model.OrderLineItems;
import org.yumeinaruu.microservices.model.dto.OrderLineItemsDto;
import org.yumeinaruu.microservices.model.dto.OrderRequest;
import org.yumeinaruu.microservices.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber());
        kafkaTemplate.send("order-placed", orderPlacedEvent);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
