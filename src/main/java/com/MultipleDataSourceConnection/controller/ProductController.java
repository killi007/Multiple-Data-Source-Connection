package com.MultipleDataSourceConnection.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MultipleDataSourceConnection.orderentity.Order;
import com.MultipleDataSourceConnection.orderrepo.OrderRepo;
import com.MultipleDataSourceConnection.orderrepo.UserRepoOracle;
import com.MultipleDataSourceConnection.productEntity.Product;
import com.MultipleDataSourceConnection.productEntity.UserEntity;
import com.MultipleDataSourceConnection.productrepo.UserRepoMsSql;
import com.MultipleDataSourceConnection.productrepo.productRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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

	@Autowired
	private UserRepoMsSql userRepoForMsSql;

	@Autowired
	private UserRepoOracle userRepoForOracle;

	@GetMapping("/save")
	public String save() {
		UserEntity userForMsSql = new UserEntity();
		userForMsSql.setName("killi");
		userForMsSql.setAge(211);
		userForMsSql = userRepoForMsSql.save(userForMsSql);
		System.err.println("Successfuly saved the user in the ms-sql");

		List<UserEntity> retrivedUserMsSql = userRepoForMsSql.findAll();
		System.err.println("retriving user from ms-sql");
		System.err.println("there are " + retrivedUserMsSql.size() + " data present");
		List<com.MultipleDataSourceConnection.orderentity.UserEntity> userListForOracle = new ArrayList<>();
		userListForOracle = retrivedUserMsSql.stream().map(user -> {
			com.MultipleDataSourceConnection.orderentity.UserEntity userForOracle = new com.MultipleDataSourceConnection.orderentity.UserEntity();

			BeanUtils.copyProperties(user, userForOracle);
			return userForOracle;

		}).toList();

		userListForOracle=	userRepoForOracle.saveAll(userListForOracle);
		System.err.println("successfuly saved in oracle");
		System.err.println(userListForOracle);
		return "Hello world";
	}

	@GetMapping("save-product")
	public String postMethodName() {
		// TODO: process POST request
		Product product = new Product();
		product.setProductName("milk");

		product.setProductPrice(10.0);
		productRepo.save(product);

		Order order = new Order();
		order.setUserName("killi");
		orderRepo.save(order);
		return "save";
	}

}
