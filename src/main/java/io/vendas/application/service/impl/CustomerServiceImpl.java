package io.vendas.application.service.impl;

import io.vendas.application.dto.CustomerDTO;
import io.vendas.application.entity.Customer;
import io.vendas.application.exceptions.CustomerNotFoundException;
import io.vendas.application.repository.CustomerRepository;
import io.vendas.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final String MESSAGE_CUSTOMER_NOT_FOUND = "Customer not found";

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customerSaved = customerRepository.save(new Customer(null, customerDTO.getName(), customerDTO.getCpf(), customerDTO.getOrders()));
        customerDTO.setId(customerSaved.getId());
        return customerDTO;
    }

    @Override
    public CustomerDTO getCustomer(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return new CustomerDTO(customer.getId(), customer.getName(), customer.getCpf(), customer.getOrders());
        }
        throw new CustomerNotFoundException(MESSAGE_CUSTOMER_NOT_FOUND);
    }

    @Override
    public void updateCustomer(Integer id, CustomerDTO customerDTO) {
        customerRepository.findById(id).map(customerFound -> {
            customerFound.setCpf(customerDTO.getCpf());
            customerFound.setName(customerDTO.getName());
            customerRepository.save(customerFound);
            return customerFound;
        }).orElseThrow(() -> new CustomerNotFoundException(MESSAGE_CUSTOMER_NOT_FOUND));
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.findById(id).map(customerFound -> {
            customerRepository.deleteById(id);
            return customerFound;
        }).orElseThrow(() -> new CustomerNotFoundException(MESSAGE_CUSTOMER_NOT_FOUND));
    }
}
