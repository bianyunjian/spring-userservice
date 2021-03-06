
create table `user` (
    `id` int(11) unsigned not null auto_increment comment '用户ID',
    `login_name` varchar(225) not null comment '登录名称，默认使用邮箱登录',
    `user_name` varchar(225) not null comment '用户名称',
    `password` varchar(500) not null comment '用户base64密码',
    `email` varchar(225) not null comment '用户登录邮箱',
    `department` varchar(225) not null comment '用户所属部门',
    `position` varchar(225) default '' comment '用户工作职位',
    `last_login_time` datetime comment '最近一次登录时间',
    `status` int(2) unsigned not null default 0 comment '用户状态：0-正常，1-禁用',
    `gmt_create` datetime not null comment '创建时间',
    `gmt_update` datetime not null comment '更新时间',
    primary key (`id`)
) engine = innodb default charset = utf8mb4;

create table `role` (
    `id`          int(11) unsigned    not null auto_increment comment '角色ID',
    `role_name` varchar(225) not null comment '角色名称',
    `status`      int(2) unsigned not null default 0 comment '角色状态：0-正常，1-禁用',
    `is_default` tinyint(1) unsigned not null default 0 comment '是否为默认角色：0-否，1-是',
    `gmt_create` datetime not null comment '创建时间',
    `gmt_update` datetime not null comment '更新时间',
    primary key (`id`)
) engine = innodb default charset = utf8mb4;

create table `permission` (
    `id` int(11) unsigned not null auto_increment comment '权限ID',
    `permission_name` varchar(225) not null comment '权限名称',
    `is_assigned` tinyint(1) unsigned not null default 0 comment '是否可配置：0-不可配置，1-可配置',
    `type` int(2) unsigned not null default 0 comment '权限类型:1-页面,2-接口',
    primary key (`id`)
) engine = innodb default charset = utf8mb4;


create table `user_role` (
    `id` char(36) not null comment '用户角色ID，使用UUID标识',
    `user_id` int(11) unsigned not null comment '用户表ID',
    `role_id` int(11) unsigned not null comment '角色表ID',
    primary key (`id`)
) engine = innodb default charset = utf8mb4;

create table `role_permission` (
    `id` char(36) not null comment '权限角色ID，使用UUID标识',
    `role_id` int(11) unsigned not null comment '角色表ID',
    `permission_id` int(11) unsigned not null comment '权限表ID',
    primary key (`id`)
) engine = innodb default charset = utf8mb4;

