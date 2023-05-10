package com.kay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kay.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/8
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {
}
