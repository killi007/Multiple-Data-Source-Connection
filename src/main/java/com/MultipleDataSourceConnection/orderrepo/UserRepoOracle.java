package com.MultipleDataSourceConnection.orderrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MultipleDataSourceConnection.orderentity.UserEntity;

public interface UserRepoOracle extends JpaRepository<UserEntity, Integer> {

}
