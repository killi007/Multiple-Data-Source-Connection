package com.MultipleDataSourceConnection.orderentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_view")
public class UserEntity {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	//@Column(insertable = false)
	// private Integer id;

	private String name;
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "age", insertable = false)
	private Integer age;

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
