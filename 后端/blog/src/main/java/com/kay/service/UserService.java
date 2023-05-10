package com.kay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kay.common.Result;
import com.kay.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    Result login(User user);

    Result register(User user);

    Result verify(String token);

    Result uploadImg(MultipartFile file, HttpServletRequest request);

    Result edit(User user, HttpServletRequest request);

    Result getInfo(HttpServletRequest request);
}
