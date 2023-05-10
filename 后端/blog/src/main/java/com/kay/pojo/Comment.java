package com.kay.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * author: Kay
 * 项目名: blog
 * 开发日期:2023/5/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comments")
public class Comment implements Serializable {
    @TableId(value = "c_id", type = IdType.ASSIGN_UUID)
    private String id;
    @TableField("b_id")
    private String bId;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Boolean hasReplies;
    @TableField("u_id")
    private String uId;
    @TableField("c_content")
    private String content;
    @TableField("c_date")
    private Date date;
}
