package io.vendas.application.repository;

import io.vendas.application.entity.Customer;
import io.vendas.application.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomer(Customer customer);

    @Query("select p from Order p left join fetch p.items where p.id = :id")
    Optional<Order> findByIdFetchItems(@Param("id") Integer id);
}
