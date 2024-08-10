package org.yumeinaruu.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.yumeinaruu.orderservice.event.OrderPlacedEvent;
import org.yumeinaruu.orderservice.model.Order;
import org.yumeinaruu.orderservice.model.OrderLineItems;
import org.yumeinaruu.orderservice.model.dto.InventoryResponse;
import org.yumeinaruu.orderservice.model.dto.OrderLineItemsDto;
import org.yumeinaruu.orderservice.model.dto.OrderRequest;
import org.yumeinaruu.orderservice.repository.OrderRepository;

import java.util.Arrays;
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
