package com.kay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kay.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
