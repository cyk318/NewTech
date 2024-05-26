drop database if exists duitang;
create database duitang character set utf8mb4;

use duitang;

# 用户信息表
drop table if exists user_ident;
create table user_ident(
    id bigint primary key auto_increment comment '用户 id',
    username varchar(32) not null comment '用户名(昵称)',
    password varchar(65) not null comment '密码: 加盐加密',
    state tinyint default 0 comment '状态: 0正常(默认) 1封号 2管理员',
    c_time datetime default now() comment '创建时间: 默认精确到秒',
    u_time datetime default now() comment '修改时间: 默认精确到秒',
    unique uniq_username (username)
) auto_increment=10000, character set utf8mb4;

# 专辑标签
drop table if exists article_tag;
create table article_tag(
    id bigint primary key comment 'tag id',
    aid bigint comment '文章 id',
    uid bigint comment '用户 id',
    name varchar(32) not null comment '标签名',
    c_time datetime default now() comment '创建时间: 默认精确到秒',
    index idx_aid(aid)
) character set utf8mb4;

# 专辑信息表
drop table if exists article_info;
create table article_info(
    id bigint primary key auto_increment comment '文章 id',
    uid bigint not null comment '用户 id',
    content varchar(1024) default '' comment '描述',
    state tinyint default 1 comment '状态: 1正常 2私密 3草稿 4封禁 ',
    c_time datetime default now() comment '创建时间: 默认精确到秒',
    u_time datetime default now() comment '修改时间: 默认精确到秒',
    index idx_uid_state(uid, state)
) character set utf8mb4;


# 专辑照片表
drop table if exists article_photo;
create table article_photo(
    id bigint primary key auto_increment comment 'id: 自增主键',
    aid bigint not null comment '专辑id',
    photo varchar(255) not null comment '照片url',
    sort tinyint not null comment '照片展示顺序，1 默认是封面',
    unique uniq_aid_sort (aid, sort)
) character set utf8mb4;

# 初始化用户信息
insert into user_ident(username, password, state) values('cyk', '3d4f453719d6428f80a0ac22ebcb9939$4c8e41f6921edfd74b1c14e17f1f3e75', 2);
insert into user_ident(username, password, state) values('lyj', '3d4f453719d6428f80a0ac22ebcb9939$4c8e41f6921edfd74b1c14e17f1f3e75', 2);
insert into user_ident(username, password, state) values('test', '3d4f453720d6428f80a0ac22ebcb9939$4c8e41f6921edfd74b1c14e17f1f3e75', 0);

# 初始化文章信息
insert into article_info(uid, content, state) values(1, 'test', 4);

# 初始化板块信息
insert into article_tag(id, aid, uid,  name) values(1,1,1,'生活');
insert into article_tag(id, aid, uid,  name) values(2,1,1,'美妆');
insert into article_tag(id, aid, uid,  name) values(3,1,1,'美食');
insert into article_tag(id, aid, uid,  name) values(4,1,1,'摄影');
insert into article_tag(id, aid, uid,  name) values(5,1,1,'影视');
insert into article_tag(id, aid, uid,  name) values(6,1,1,'职场');
insert into article_tag(id, aid, uid,  name) values(7,1,1,'情感');
insert into article_tag(id, aid, uid,  name) values(8,1,1,'家居');
insert into article_tag(id, aid, uid,  name) values(9,1,1,'游戏');
insert into article_tag(id, aid, uid,  name) values(10,1,1,'旅行');
insert into article_tag(id, aid, uid,  name) values(11,1,1,'健身');

