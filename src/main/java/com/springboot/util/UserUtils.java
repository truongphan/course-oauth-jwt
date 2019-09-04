package com.springboot.util;


import com.springboot.entity.UserEntity;
import com.springboot.model.User;

public class UserUtils {

	public static User toUserModel(UserEntity entity) {
		return User.builder().id(entity.getId())
				.username(entity.getUsername())
				.email(entity.getEmail())
				.emailVerified(entity.getEmailVerified())
				.provider(entity.getProvider())
				.providerId(entity.getProviderId())
				.password(entity.getPassword())
				.imageUrl(entity.getImageUrl())
				.build();
	}

	public static UserEntity toUserEntity(User model) {
		return UserEntity.builder()
				.username(model.getUsername())
				.email(model.getEmail())
				.emailVerified(model.getEmailVerified())
				.provider(model.getProvider())
				.providerId(model.getProviderId())
				.password(model.getPassword())
				.imageUrl(model.getImageUrl())
				.build();
	}

}
