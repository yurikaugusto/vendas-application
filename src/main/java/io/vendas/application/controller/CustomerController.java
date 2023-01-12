package io.vendas.application.controller;

import io.vendas.application.dto.CustomerDTO;
import io.vendas.application.entity.Customer;
import io.vendas.application.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    public static final String MESSAGE_CUSTOMER_NOT_FOUND = "Customer not found";
    private final CustomerRepository customerRepository;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return new CustomerDTO(customer.getId(), customer.getName(), customer.getCpf(), customer.getOrders());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_CUSTOMER_NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customerSaved = customerRepository.save(new Customer(null, customerDTO.getName(), customerDTO.getCpf(), customerDTO.getOrders()));
        customerDTO.setId(customerSaved.getId());
        return customerDTO;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        customerRepository.findById(id).map(customerFound -> {
            customerFound.setCpf(customerDTO.getCpf());
            customerFound.setName(customerDTO.getName());
            customerRepository.save(customerFound);
            return customerFound;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_CUSTOMER_NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer id) {
        customerRepository.findById(id).map(customerFound -> {
            customerRepository.deleteById(id);
            return customerFound;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_CUSTOMER_NOT_FOUND));
    }
}
