package com.kay.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @TableId(value = "u_id", type = IdType.ASSIGN_UUID)
    private String id;
    @TableField("u_name")
    private String name;
    @TableField("u_pwd")
    private String pwd;

    @TableField("u_img")
    private String img;
    private String salt;
}
