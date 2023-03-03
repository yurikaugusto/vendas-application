package io.vendas.application.service.impl;

import io.vendas.application.dto.OrderDTO;
import io.vendas.application.dto.OrderItemDTO;
import io.vendas.application.entity.Customer;
import io.vendas.application.entity.Order;
import io.vendas.application.entity.OrderItem;
import io.vendas.application.entity.Product;
import io.vendas.application.enums.StatusOrder;
import io.vendas.application.repository.CustomerRepository;
import io.vendas.application.repository.OrderItemRepository;
import io.vendas.application.repository.OrderRepository;
import io.vendas.application.repository.ProductRepository;
import io.vendas.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order save(OrderDTO orderDTO) {
        Integer idCustomer = orderDTO.getIdCustomer();
        Customer customer = customerRepository
                .findById(idCustomer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id of customer not valid"));

        Order order = new Order();
        order.setAmount(orderDTO.getAmount());
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);
        order.setStatus(StatusOrder.ACCOMPLISHED);

        List<OrderItem> orderItems = convertItems(order, orderDTO.getItems());
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        order.setItems(orderItems);
        return order;
    }

    @Override
    public Optional<Order> getFullOrder(Integer id) {
        return orderRepository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void updateStatusOrder(Integer id, StatusOrder statusOrder) {
        orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(statusOrder);
                    return orderRepository.save(order);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    private List<OrderItem> convertItems(Order order, List<OrderItemDTO> items) {
        if(items.isEmpty()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Can't accomplish order without items");

        return items.stream().map(orderItemDTO -> {
            Integer idProduct = orderItemDTO.getProduct();
            Product product = productRepository.findById(idProduct)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The product id is invalid"));

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
    }
}
