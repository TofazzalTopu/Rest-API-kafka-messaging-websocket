package com.info.demo.viewModel;

import com.info.demo.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private String token;
	private User user;

	public JwtResponse(User user, String token) {
		this.user = user;
		this.token = token;
	}
}
