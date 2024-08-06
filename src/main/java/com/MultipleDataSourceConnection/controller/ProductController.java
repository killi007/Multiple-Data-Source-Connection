package com.MultipleDataSourceConnection.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MultipleDataSourceConnection.orderentity.Order;
import com.MultipleDataSourceConnection.orderrepo.OrderRepo;
import com.MultipleDataSourceConnection.productEntity.Product;
import com.MultipleDataSourceConnection.productrepo.productRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private productRepo productRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@GetMapping("save-product")
	public String postMethodName() {
		//TODO: process POST request
		Product product=new Product();
		product.setProductName("milk");
		
		product.setProductPrice(10.0);
		productRepo.save(product);
		
		Order order =new Order();
		order.setUserName("killi");
		orderRepo.save(order);
		return "save";
	}
	
}
