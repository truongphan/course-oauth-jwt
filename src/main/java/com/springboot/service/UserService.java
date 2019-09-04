package com.springboot.service;

import com.springboot.entity.UserEntity;
import com.springboot.model.CustomUserDetails;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import com.springboot.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		// check user is exist or not in database
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("Bad credentials");
		}
		return new CustomUserDetails(UserUtils.toUserModel(userEntity));
	}

	// JWTAuthenticationFilter will use this method
	@Transactional
	public UserDetails loadUserById(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return new CustomUserDetails(UserUtils.toUserModel(userEntity));
	}

	public User createUser(@Valid User user) {
		UserEntity userEntity = UserUtils.toUserEntity(user);
		userEntity.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(userEntity);
		return UserUtils.toUserModel(userRepository.findById(userEntity.getId()).get());
	}

    public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		List<UserEntity> entities = userRepository.findAll();
		entities.forEach(e -> userList.add(UserUtils.toUserModel(e)));
		return userList;
    }
}