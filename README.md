# 博客开发文档

## 二、数据库

### 1、t_user表

| 字段       | 类型         | 约束        | 描述             |
| ---------- | ------------ | ----------- | ---------------- |
| u_id       | int          | primary key | 用户标识         |
| u_name     | varchar(4)   | not null    | 用户名           |
| u_pwd      | char(32)     | not null    | 用户密码         |
| u_img      | varchar(255) | not null    | 用户头像         |
| u_salt     | char(4)      | not null    | 密码加密盐       |
| has_Delete | bit(1)       | not null    | 标识用户是否注销 |

### 2、t_blog

| 字段       | 类型   | 约束        | 描述             |
| ---------- | ------ | ----------- | ---------------- |
| b_id       | int    | primary key | 博客标识         |
| b_content  | text   | not null    | 博客内容         |
| b_date     | date   | not null    | 博客发布时间     |
| u_id       | int    | foreign key | 发布者用户标识   |
| has_Delete | bit(1) | not null    | 标识博客是否删除 |

### 3、t_comment

| 字段       | 类型         | 约束        | 描述               |
| ---------- | ------------ | ----------- | ------------------ |
| c_id       | int          | primary key | 评论标识           |
| c_content  | varchar(100) | not null    | 评论内容           |
| c_date     | date         | not null    | 评论发布时间       |
| u_id       | int          | foreign key | 评论者用户标识     |
| b_id       | int          | foreign key | 被评论博客标识     |
| has_Delete | bit(1)       | not null    | 标识评论是否被删除 |

### 4、t_reply

| 字段       | 类型         | 约束        | 描述               |
| ---------- | ------------ | ----------- | ------------------ |
| r_id       | int          | primary key | 回复标识           |
| r_content  | varchar(100) | not null    | 回复内容           |
| r_date     | date         | not null    | 回复发布时间       |
| u_id       | int          | foreign key | 回复者用户标识     |
| u_rid      | int          | foriegn key | 被回复者用户标识   |
| c_id       | int          | foreign key | 评论标识           |
| has_Delete | bit(1)       | not null    | 标识回复是否被删除 |

