package com.kay.controller;

import com.kay.common.Result;
import com.kay.pojo.Reply;
import com.kay.service.RepliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/9
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private RepliesService service;

    @GetMapping("/getRepliesByCid")
    public Result getRepliesByCid(String id, Integer curPage) {
        return service.getRepliesByCid(id, curPage);
    }

    @PutMapping("/addReply")
    public Result addReply(@RequestBody Reply reply, HttpServletRequest request) {
        return service.addReply(reply, request.getHeader("Authorization"));
    }
}
