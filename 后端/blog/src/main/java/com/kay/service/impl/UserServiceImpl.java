package com.kay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kay.common.Result;
import com.kay.common.StatusCode;
import com.kay.dao.UserDao;
import com.kay.pojo.User;
import com.kay.service.UserService;
import com.kay.utils.JwtUtils;
import com.kay.utils.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public Result login(User user) {
        //登录
        SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getName(), user.getPwd()));
        //查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, user.getName());
        User reUser = baseMapper.selectOne(wrapper);
        //根据查到的id生成token
        String token = JwtUtils.generateToken(reUser.getId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        return new Result(StatusCode.success, "登录成功!", map);
    }

    @Override
    public Result register(User user) {
        if (user.getPwd().trim().length() < 4)
            return new Result(StatusCode.fail, "参数不合法");
        String salt = SaltUtils.getSalt();
        Md5Hash md5Hash = new Md5Hash(user.getPwd(), ByteSource.Util.bytes(salt));
        user.setPwd(md5Hash.toString());
        user.setSalt(salt);
        baseMapper.insert(user);
        return new Result(StatusCode.success, "注册成功!");
    }

    @Override
    public Result verify(String token) {
        JwtUtils.verifyToken(token);
        return new Result(StatusCode.success, null, null);
    }

    @Override
    public Result uploadImg(MultipartFile file, HttpServletRequest request) {
        String realPath = "/www/wwwroot/images/";
//        String realPath = "D:\\uploadFile/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if (!folder.isDirectory())
            folder.mkdirs();
        String oldName = file.getOriginalFilename();
        String newName;
        try {
            assert oldName != null;
            newName = UUID.randomUUID() +
                    oldName.substring(oldName.lastIndexOf('.'));
            file.transferTo(new File(folder + "/" + newName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/images/" + format + "/" + newName;
        return new Result(StatusCode.success, path, null);
    }

    @Override
    public Result edit(User user, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String uId = JwtUtils.analysisToken(token);
        User reUser = baseMapper.selectById(uId);
        user.setId(uId);
        Md5Hash md5Hash = new Md5Hash(user.getPwd(), ByteSource.Util.bytes(reUser.getSalt()));
        user.setPwd(md5Hash.toString());
        baseMapper.updateById(user);
        return new Result(StatusCode.success, null, null);
    }

    @Override
    public Result getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String uId = JwtUtils.analysisToken(token);
        User user = baseMapper.selectById(uId);
        user.setPwd(null);
        user.setSalt(null);
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        return new Result(StatusCode.success, "success", map);
    }
}
