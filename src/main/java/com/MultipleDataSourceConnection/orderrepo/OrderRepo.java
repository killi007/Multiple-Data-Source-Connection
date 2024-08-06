package com.MultipleDataSourceConnection.orderrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MultipleDataSourceConnection.orderentity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

}
