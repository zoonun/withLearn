package com.ssafy.api.service;

import com.ssafy.api.response.UserLoginPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	UserRepositorySupport userRepositorySupport;

	@Autowired
	PasswordEncoder passwordEncoder;

	User user = new User();

	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		user.setUserId(userRegisterInfo.getId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setDepartment(userRegisterInfo.getDepartment());
		user.setName(userRegisterInfo.getName());
		user.setPosition(userRegisterInfo.getPosition());
		//
		return userRepository.save(user);
	}

	@Override
	public void patchUser(UserRegisterPostReq userRegisterInfo, String userId) {
		try {
			Optional<User> user = userRepository.findById(userRepositorySupport.findUserByUserId(userId).get().getId());
			user.get().setDepartment(userRegisterInfo.getDepartment());
			user.get().setName(userRegisterInfo.getName());
			user.get().setPosition(userRegisterInfo.getPosition());
			userRepository.save(user.get());
		}catch (NoSuchElementException e){

		}
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	@Override
	public void deleteUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 삭제)
		userRepository.deleteById(userRepositorySupport.findUserByUserId(userId).get().getId());
	}

  @Override
  public Boolean getUserIdByUserId(String userId) {
    try {
        String ID = userRepository.findByUserId(userId).get().getUserId();
        if(ID.isEmpty())
          return true;
        else
          return false;
    }catch (Exception e){
        return true;
    }
  }
}
