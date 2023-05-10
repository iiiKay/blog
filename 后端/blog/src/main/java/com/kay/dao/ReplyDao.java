package com.kay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kay.pojo.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/8
 */
@Mapper
public interface ReplyDao extends BaseMapper<Reply> {
    Integer getCountByCid(@Param("id") String id);

}
