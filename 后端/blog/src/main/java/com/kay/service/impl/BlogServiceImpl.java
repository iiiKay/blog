package com.kay.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kay.common.Result;
import com.kay.common.StatusCode;
import com.kay.dao.BlogDao;
import com.kay.pojo.Blog;
import com.kay.service.BlogService;
import com.kay.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {
    @Override
    public Result getBlogs(Integer curPage) {
        Page<Blog> page = new Page<>(curPage, 10);
        Page<Blog> blogPage = baseMapper.getBlogs(page);
        List<Blog> blogs = blogPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("blogs", blogs);
        return new Result(StatusCode.success, null, map);
    }

    @Override
    public Result getBlogById(String id) {
        Blog blog = baseMapper.getBlogById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("blog", blog);
        return new Result(StatusCode.success, null, map);
    }

    @Override
    public Result getBlogByKey(Integer curPage, String text) {
        Page<Blog> page = new Page<>(curPage, 10);
        Page<Blog> blog = baseMapper.getBlogByKey(page, text);
        List<Blog> records = blog.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("blogs", records);
        return new Result(StatusCode.success, null, map);
    }

    @Override
    public Result getBlogByUId(Integer curPage, String token) {
        String uid = JwtUtils.analysisToken(token);
        Page<Blog> page = new Page<>(curPage, 10);
        Page<Blog> blog = baseMapper.getBlogsByUId(page, uid);
        List<Blog> records = blog.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("blogs", records);
        return new Result(StatusCode.success, null, map);
    }

    @Override
    public Result issue(Blog blog, String token) {
        String uid = JwtUtils.analysisToken(token);
        blog.setUId(uid);
        blog.setDate(new Date(System.currentTimeMillis()));
        baseMapper.insert(blog);
        return new Result(StatusCode.success, null, null);
    }

    @Override
    public Result isMyBlog(String id, String token) {
        String uid = JwtUtils.analysisToken(token);
        Blog myBlog = baseMapper.isMyBlog(id, uid);
        if (myBlog != null) {
            return new Result(StatusCode.success, "true", null);
        }
        return new Result(StatusCode.success, "false", null);
    }

    @Override
    public Result updateBlog(Blog blog, String authorization) {
        String uid = JwtUtils.analysisToken(authorization);
        blog.setUId(uid);
        blog.setDate(new Date(System.currentTimeMillis()));
        baseMapper.updateById(blog);
        return new Result(StatusCode.success, null, null);
    }
}
