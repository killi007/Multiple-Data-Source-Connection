package com.MultipleDataSourceConnection.productrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MultipleDataSourceConnection.productEntity.UserEntity;

public interface UserRepoMsSql extends JpaRepository<UserEntity, Integer>{

}
