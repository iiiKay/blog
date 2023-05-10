package com.kay.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@TableName("replies")
public class Reply implements Serializable {
    @TableId(value = "r_id", type = IdType.ASSIGN_UUID)
    private String id;
    @TableField("c_id")
    private String cId;
    @TableField("u_id")
    private String uId;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private User reUser;
    @TableField("r_uid")
    private String rUid;
    @TableField("r_content")
    private String content;
    @TableField("r_date")
    private Date date;
}
