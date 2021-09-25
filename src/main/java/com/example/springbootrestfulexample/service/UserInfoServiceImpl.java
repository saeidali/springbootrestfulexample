package com.example.springbootrestfulexample.service;

import com.example.springbootrestfulexample.dao.UserInfoDao;
import com.example.springbootrestfulexample.domain.UserInfo;
import com.example.springbootrestfulexample.dto.UserInfoDto;
import com.example.springbootrestfulexample.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("userinfoservice")
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoDao userInfoDao;
    private final Mapper mapper;

    @Autowired
    public UserInfoServiceImpl(UserInfoDao userInfoDao, Mapper mapper) {
        this.userInfoDao = userInfoDao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "usersList", allEntries = true),},
            put = {@CachePut(cacheNames = "userInfo", value = "userInfo", key = "#userInfoDto.id")})
    public UserInfoDto addUser(UserInfoDto userInfoDto) {
        UserInfo userinfo = userInfoDao.save(mapper.convert(userInfoDto, UserInfo.class));
        return mapper.convert(userinfo, UserInfoDto.class);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "usersList", allEntries = true),},
            put = {@CachePut(cacheNames = "userInfo", value = "userInfo", key = "#userInfoDto.id")})
    public UserInfoDto editUser(UserInfoDto userInfoDto) {
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(userInfoDto.getId());
        if (userInfoOptional.isEmpty()) {
            return null;
        }
        UserInfo userInfo = mapper.convert(userInfoDto, UserInfo.class);
        userInfo.setId(userInfoOptional.get().getId());
        return mapper.convert(userInfoDao.save(userInfo), UserInfoDto.class);
    }

    @Override
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Caching(evict = {@CacheEvict(value = "usersList", allEntries = true),
            @CacheEvict(cacheNames = "userInfo", value = "userInfo", key = "#id"),})
    public boolean deleteUserInfo(Long id) {
        if (userInfoDao.findById(id).isPresent()) {
            userInfoDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfo> findById(Long id) {
        return userInfoDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "usersList", value = "usersList", key = "{#page,#name}")
    public Map<String, Object> getUsersByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserInfo> userInfoPage = userInfoDao.getUsersByName(name, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("userInfoDtoList", mapper.convertToList(userInfoPage.getContent(), UserInfoDto.class));
        response.put("totalItems", userInfoPage.getTotalElements());
        response.put("totalPages", userInfoPage.getTotalPages());
        return response;
    }
}
