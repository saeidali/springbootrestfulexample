package com.example.springbootrestfulexample.service;

import com.example.springbootrestfulexample.domain.UserInfo;
import com.example.springbootrestfulexample.dto.UserInfoDto;

import java.util.Map;
import java.util.Optional;

public interface UserInfoService {
    UserInfoDto addUser(UserInfoDto userInfoDto);

    UserInfoDto editUser(UserInfoDto userInfoDto);

    boolean deleteUserInfo(Long id);

    Optional<UserInfo> findById(Long id);

    Map<String, Object> getUsersByName(String name, int page, int size);

}
