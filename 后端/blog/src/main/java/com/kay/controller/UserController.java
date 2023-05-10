package com.kay.controller;

import com.kay.common.Result;
import com.kay.pojo.User;
import com.kay.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService service;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return service.login(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return service.register(user);
    }

    @GetMapping("/verify")
    public Result verify(String token) {
        return service.verify(token);
    }

    @PutMapping("/edit")
    public Result edit(@RequestBody User user, HttpServletRequest request) {
        return service.edit(user, request);
    }

    @PostMapping("/uploadImg")
    public Result uploadImg(MultipartFile file, HttpServletRequest request) {
        return service.uploadImg(file, request);
    }

    @GetMapping("/getInfo")
    public Result getInfo(HttpServletRequest request){
        return service.getInfo(request);
    }
}
