package io.vendas.application.mapper;

import io.vendas.application.dto.OrderInformationDTO;
import io.vendas.application.dto.OrderItemInformationDTO;
import io.vendas.application.entity.Order;
import io.vendas.application.entity.OrderItem;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class OrderMapper {

    public static OrderInformationDTO convert(Order order) {
        return OrderInformationDTO.builder()
                .code(order.getId())
                .cpf(order.getCustomer().getCpf())
                .customerName(order.getCustomer().getName())
                .total(order.getAmount())
                .orderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .status(order.getStatus().name())
                .items(convert(order.getItems()))
                .build();
    }

    private static List<OrderItemInformationDTO> convert(List<OrderItem> items) {
        if (items.isEmpty()) {
            return new ArrayList<>();
        }
        return items.stream().map(item ->
            OrderItemInformationDTO.builder()
                    .productDescription(item.getProduct().getDescription())
                    .unityPrice(item.getProduct().getPrice())
                    .quantity(item.getQuantity())
                    .build()
        ).collect(Collectors.toList());
    }
}
