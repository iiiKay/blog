package com.kay;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kay.dao.BlogDao;
import com.kay.pojo.Blog;
import com.kay.pojo.User;
import com.kay.service.BlogService;
import com.kay.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    BlogService blogService;
    @Autowired
    BlogDao blogDao;


    @Autowired
    UserService service;

    @Test
    void contextLoads() {
        Page<Blog> blogsByUId = blogDao.getBlogsByUId(new Page<Blog>(1, 10), "2d78a36c6dcbd553f39545a8b7971692");
        List<Blog> records = blogsByUId.getRecords();
        records.forEach(System.out::println);
    }

}
