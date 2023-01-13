package io.vendas.application.service;

import io.vendas.application.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomer(Integer id);

    void updateCustomer(Integer id, CustomerDTO customerDTO);

    void deleteCustomer(Integer id);
}
