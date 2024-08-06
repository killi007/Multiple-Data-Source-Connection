package com.MultipleDataSourceConnection.productrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MultipleDataSourceConnection.productEntity.Product;

public interface productRepo extends JpaRepository<Product, Integer> {

}
