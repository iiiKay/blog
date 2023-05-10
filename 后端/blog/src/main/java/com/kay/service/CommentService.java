package com.kay.service;

import com.kay.common.Result;
import com.kay.pojo.Comment;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/8
 */
public interface CommentService {
    Result getCommentByBid(String bId);

    Result addComment(Comment comment, String authorization);
}
