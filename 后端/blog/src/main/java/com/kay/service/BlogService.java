package com.kay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kay.common.Result;
import com.kay.pojo.Blog;

public interface BlogService extends IService<Blog> {
    Result getBlogs(Integer curPage);

    Result getBlogById(String id);

    Result getBlogByKey(Integer curPage, String text);

    Result getBlogByUId(Integer curPage, String token);

    Result issue(Blog blog, String token);

    Result isMyBlog(String id, String token);

    Result updateBlog(Blog blog, String authorization);
}
