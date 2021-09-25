package com.example.springbootrestfulexample.dao;

import com.example.springbootrestfulexample.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
    @Query(value = "select c from UserInfo c where c.name like %:name%")
    Page<UserInfo> getUsersByName(@Param("name") String name, Pageable pageable);
}
