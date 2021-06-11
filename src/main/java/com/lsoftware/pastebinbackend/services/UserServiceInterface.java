package com.lsoftware.pastebinbackend.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lsoftware.pastebinbackend.shared.dto.PostDto;
import com.lsoftware.pastebinbackend.shared.dto.UserDTO;

public interface UserServiceInterface extends UserDetailsService {
	public UserDTO createUser(UserDTO user);
	public UserDTO getUser(String email);
	public List<PostDto> getUserPosts(String email);
}
