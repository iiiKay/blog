package com.kay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kay.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogDao extends BaseMapper<Blog> {

    Blog getBlogById(@Param("id") String id);

    Page<Blog> getBlogs(Page<Blog> page);

    Page<Blog> getBlogByKey(Page<Blog> page, @Param("text") String text);

    Page<Blog> getBlogsByUId(Page<Blog> page, @Param("uid") String uid);

    Blog isMyBlog(@Param("id") String id, @Param("uid") String uId);
}
