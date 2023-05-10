package com.kay.controller;

import com.kay.common.Result;
import com.kay.pojo.Comment;
import com.kay.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/8
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService service;

    @GetMapping("/getCommentByBid")
    public Result getCommentByBid(String bId){
        return service.getCommentByBid(bId);
    }

    @PutMapping("/addComment")
    public Result addComment(@RequestBody Comment comment, HttpServletRequest request){
        return service.addComment(comment,request.getHeader("Authorization"));
    }
}
