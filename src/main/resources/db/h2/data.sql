insert into `user` values (1, 'admin1@aispeech.com', '超级管理员1','MTIzNDU2', 'admin1@aispeech.com', '研发部', '架构师1', '2020-06-16 14:37:20', 0);

insert into role values (1, '管理员', 0);
insert into role values (2, '测试员', 0);

insert into permission values (1, '标记模块');
insert into permission values (2, '评测模块');

insert into role_permission values ('0f642b2a-2eed-4023-9e01-a5e4afcce593', 1, 1);
insert into role_permission values ('e3c55e29-ec9f-4a4d-bca1-7d5a36865f10', 1, 2);
insert into role_permission values ('32d05b5a-ef68-4f46-b2ce-536ba3df3398', 2, 2);

insert into user_role values ('b90e5cc8-a18d-4abe-9511-8d6de4def4d0', 1, 1);