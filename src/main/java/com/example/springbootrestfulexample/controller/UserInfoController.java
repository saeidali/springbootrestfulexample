package com.example.springbootrestfulexample.controller;

import com.example.springbootrestfulexample.dto.UserInfoDto;
import com.example.springbootrestfulexample.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(
        produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class UserInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/userinfo")
    public ResponseEntity<UserInfoDto> create(@Valid @RequestBody UserInfoDto userInfoDto) {
        return new ResponseEntity<>(userInfoService.addUser(userInfoDto), HttpStatus.OK);
    }

    @PutMapping("/userinfoupdate")
    public ResponseEntity<UserInfoDto> update(@Valid @RequestBody UserInfoDto userInfoDto) {
        try {
            UserInfoDto userInfoDtoModel = userInfoService.editUser(userInfoDto);
            return userInfoService.editUser(userInfoDto) == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                    new ResponseEntity<>(userInfoDtoModel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/userinfodelete")
    public ResponseEntity<String> deleteUserInfo(@RequestParam(name = "userInfoId") @Min(value = 1, message = "{min.valid.id}") Long userInfoId) {
        return userInfoService.deleteUserInfo(userInfoId) ? new ResponseEntity<>("User with ID :" + userInfoId + " deleted successfully", HttpStatus.OK) :
                new ResponseEntity<>("could not delete user", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/getuserinfolist")
    public ResponseEntity<Map<String, Object>> getUserInfoByName(@RequestParam(required = false) String name
            , @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "2") int size) {
        Map<String, Object> response = new HashMap<>();
        if (page < 0 || size < 1) {
            response.put("message", HttpStatus.BAD_REQUEST);
            LOGGER.warn("UserInfo controller warning :{}", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = userInfoService.getUsersByName(name, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
