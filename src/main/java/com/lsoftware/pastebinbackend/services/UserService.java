package com.lsoftware.pastebinbackend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lsoftware.pastebinbackend.entities.PostEntity;
import com.lsoftware.pastebinbackend.entities.UserEntity;
import com.lsoftware.pastebinbackend.exceptions.EmailExistsException;
import com.lsoftware.pastebinbackend.repository.PostRepository;
import com.lsoftware.pastebinbackend.repository.UserRepository;
import com.lsoftware.pastebinbackend.shared.dto.PostDto;
import com.lsoftware.pastebinbackend.shared.dto.UserDTO;

@Service
public class UserService implements UserServiceInterface {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    ModelMapper mapper;

	@Override
	public UserDTO createUser(UserDTO user) {
		
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new EmailExistsException("El correo electronico ya existe");
		}
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		UUID userId = UUID.randomUUID();
		userEntity.setUserId(userId.toString());
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		UserDTO userToReturn = new UserDTO();
		BeanUtils.copyProperties(storedUserDetails, userToReturn);
		
		return userToReturn;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		UserDTO userToReturn = new UserDTO();
		BeanUtils.copyProperties(userEntity, userToReturn);
		return userToReturn;
	}

	@Override
	public List<PostDto> getUserPosts(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

        List<PostEntity> posts = postRepository.getByUserIdOrderByCreatedAtDesc(userEntity.getId());

        List<PostDto> postDtos = new ArrayList<>();

        for (PostEntity post : posts) {
            PostDto postDto = mapper.map(post, PostDto.class);
            postDtos.add(postDto);
        }

        return postDtos;
	}

}
