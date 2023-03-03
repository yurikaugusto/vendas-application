package io.vendas.application.service;

import io.vendas.application.dto.OrderDTO;
import io.vendas.application.entity.Order;
import io.vendas.application.enums.StatusOrder;

import java.util.Optional;

public interface OrderService {

    Order save(OrderDTO orderDTO);

    Optional<Order> getFullOrder(Integer id);

    void updateStatusOrder(Integer id, StatusOrder statusOrder);

}
