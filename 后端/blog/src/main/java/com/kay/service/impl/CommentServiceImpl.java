package com.kay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kay.common.Result;
import com.kay.common.StatusCode;
import com.kay.dao.CommentDao;
import com.kay.dao.ReplyDao;
import com.kay.dao.UserDao;
import com.kay.pojo.Comment;
import com.kay.pojo.User;
import com.kay.service.CommentService;
import com.kay.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/8
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReplyDao replyDao;

    @Override
    public Result getCommentByBid(String bId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getBId, bId);
        List<Comment> comments = baseMapper.selectList(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        for (Comment comment : comments) {
            User user = userDao.selectById(comment.getUId());
            Integer count = replyDao.getCountByCid(comment.getId());
            if (count != 0)
                comment.setHasReplies(true);
            user.setPwd(null);
            user.setSalt(null);
            comment.setUser(user);
        }
        map.put("comments", comments);
        return new Result(StatusCode.success, null, map);
    }

    @Override
    public Result addComment(Comment comment, String authorization) {
        String uid = JwtUtils.analysisToken(authorization);
        comment.setUId(uid);
        comment.setDate(new Date(System.currentTimeMillis()));
        baseMapper.insert(comment);
        return new Result(StatusCode.success, null, null);
    }
}
