package com.hashedin.hu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;

	private String emailId;
	private String name;
	private String password;
	private int phoneNumber;
	private String address;
	private Boolean isLogIn;
}
