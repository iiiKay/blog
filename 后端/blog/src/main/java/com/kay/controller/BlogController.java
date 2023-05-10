package com.kay.controller;

import com.kay.common.Result;
import com.kay.pojo.Blog;
import com.kay.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService service;

    @GetMapping("/getBlogs")
    public Result getBlogs(Integer curPage) {
        return service.getBlogs(curPage);
    }

    @GetMapping("/getBlogById")
    public Result getBlogById(String id) {
        return service.getBlogById(id);
    }

    @GetMapping("/getBlogByKey")
    public Result getBlogByKey(Integer curPage, String text) {
        return service.getBlogByKey(curPage, text);
    }

    @GetMapping("/getBlogByUId")
    public Result getBlogByUId(Integer curPage, HttpServletRequest request) {
        return service.getBlogByUId(curPage, request.getHeader("Authorization"));
    }

    @PostMapping("/issue")
    public Result issue(@RequestBody Blog blog, HttpServletRequest request) {
        return service.issue(blog, request.getHeader("Authorization"));
    }

    @PutMapping("/updateBlog")
    public Result updateBlog(@RequestBody Blog blog, HttpServletRequest request) {
        return service.updateBlog(blog, request.getHeader("Authorization"));
    }

    @GetMapping("/isMyBlog")
    public Result isMyBlog(String id,HttpServletRequest request){
        return service.isMyBlog(id,request.getHeader("Authorization"));
    }
}
