package io.vendas.application.controller;

import io.vendas.application.dto.OrderDTO;
import io.vendas.application.dto.OrderInformationDTO;
import io.vendas.application.dto.UpdateOrderStatusDTO;
import io.vendas.application.entity.Order;
import io.vendas.application.enums.StatusOrder;
import io.vendas.application.mapper.OrderMapper;
import io.vendas.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.save(orderDTO);
        return order.getId();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderInformationDTO getFullOrder(@PathVariable Integer id) {
        return orderService.getFullOrder(id)
                .map(orderFound -> OrderMapper.convert(orderFound))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusOrder(@PathVariable Integer id, @RequestBody UpdateOrderStatusDTO orderStatusDTO) {
        orderService.updateStatusOrder(id, StatusOrder.valueOf(orderStatusDTO.getNewStatus()));
    }

}
