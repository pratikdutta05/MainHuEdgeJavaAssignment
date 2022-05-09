package com.hasedin.hu.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String emailId;
	private String name;
	private String password;
	private int phoneNumber;
	private String address;

	private Boolean isLogIn;

	public User( String emailId, String name, String password, int phoneNumber, String address) {
		this.emailId = emailId;
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
}
