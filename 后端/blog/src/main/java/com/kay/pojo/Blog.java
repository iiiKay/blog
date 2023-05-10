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

@Data
@TableName("blogs")
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {
    @TableId(value = "b_id",type = IdType.ASSIGN_UUID)
    private String id;
    @TableField("u_id")
    private String uId;
    @TableField("u_name")
    private String name;
    @TableField("b_title")
    private String title;
    @TableField("b_content")
    private String content;
    @TableField("b_date")
    private Date date;
    @TableField("u_img")
    private String img;
}
