package com.kay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kay.common.Result;
import com.kay.common.StatusCode;
import com.kay.dao.ReplyDao;
import com.kay.dao.UserDao;
import com.kay.pojo.Reply;
import com.kay.pojo.User;
import com.kay.service.RepliesService;
import com.kay.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/9
 */
@Service
public class RepliesServiceImpl extends ServiceImpl<ReplyDao, Reply> implements RepliesService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result getRepliesByCid(String id, Integer curPage) {
        Page<Reply> page = new Page<>(curPage, 3);
        LambdaQueryWrapper<Reply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Reply::getCId, id);
        Page<Reply> replyPage = baseMapper.selectPage(page, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        List<Reply> records = replyPage.getRecords();
        map.put("hasNext", replyPage.hasNext());
        for (Reply reply : records) {
            User user = userDao.selectById(reply.getUId());
            User reUser = userDao.selectById(reply.getRUid());
            if (!Objects.isNull(reUser)) {
                reUser.setSalt("");
                reUser.setPwd("");
            }
            user.setSalt("");
            user.setPwd("");
            reply.setUser(user);
            reply.setReUser(reUser);
        }
        map.put("replies", records);
        return new Result(StatusCode.success, null, map);
    }

    @Override
    public Result addReply(Reply reply, String token) {
        String uid = JwtUtils.analysisToken(token);
        reply.setUId(uid);
        reply.setDate(new Date(System.currentTimeMillis()));
        if (reply.getRUid().equals(""))
            reply.setRUid(null);
        baseMapper.insert(reply);
        return new Result(StatusCode.success, null, null);
    }
}
