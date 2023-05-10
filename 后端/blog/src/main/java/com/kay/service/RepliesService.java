package com.kay.service;

import com.kay.common.Result;
import com.kay.pojo.Reply;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/9
 */
public interface RepliesService {
    Result getRepliesByCid(String id, Integer curPage);

    Result addReply(Reply reply, String token);
}
