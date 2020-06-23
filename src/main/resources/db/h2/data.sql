insert into `user` values (1, 'admin1@aispeech.com', '超级管理员1','MTIzNDU2', 'admin1@aispeech.com', '研发部', '架构师1', '2020-06-16 14:37:20', 0, '2020-06-16 14:37:20', '2020-06-16 14:37:20');

insert into role values (1, '管理员', 0, 1, '2020-06-16 14:37:20', '2020-06-16 14:37:20');
insert into role values (2, '标注员', 0, 1, '2020-06-16 14:37:20', '2020-06-16 14:37:20');
insert into role values (3, '审核员', 0, 1, '2020-06-16 14:37:20', '2020-06-16 14:37:20');
insert into role values (4, '训练员', 0, 1, '2020-06-16 14:37:20', '2020-06-16 14:37:20');

insert into permission values (1, '语言模型训练', 1, 0);
insert into permission values (2, '声学模型训练', 1, 0);
insert into permission values (3, '模型测试', 1, 0);
insert into permission values (4, '发布标注任务', 1, 0);
insert into permission values (5, '人工标注', 1, 0);
insert into permission values (6, '标注任务审核', 1, 0);
insert into permission values (7, '团队成员管理', 0, 0);
insert into permission values (8, '团队权限管理', 0, 0);

insert into role_permission values ('8a0430ff-0c6d-460d-96df-f23f1ec1a55c', 1, 1);
insert into role_permission values ('9c8a5885-9cec-46b0-8353-821fbbe28692', 1, 2);
insert into role_permission values ('b9ba7139-4cc1-4897-833a-86015d821590', 1, 3);
insert into role_permission values ('0ec7a0c6-7a97-4cfd-b304-7f16d92a211c', 1, 4);
insert into role_permission values ('82c2abf7-a90e-4a11-9dab-781b97de9a2b', 1, 5);
insert into role_permission values ('2fba52de-1bca-4300-b801-a575e7ab8a1b', 1, 6);
insert into role_permission values ('dfa6eee4-7805-4457-8846-99efc0087476', 1, 7);
insert into role_permission values ('8e84def2-02eb-407c-85ce-b1f2887761c6', 1, 8);
insert into role_permission values ('992dade0-b430-4c83-8a58-344fd411a4d3', 2, 5);
insert into role_permission values ('a2d97c45-251b-47ca-8a30-ad6214b938a3', 3, 6);
insert into role_permission values ('243f5fe2-cfeb-4dc5-8c8c-dd9dd61577dc', 4, 1);
insert into role_permission values ('92f4fd2e-6000-4578-8626-9a79a2e80645', 4, 3);

insert into user_role values ('b90e5cc8-a18d-4abe-9511-8d6de4def4d0', 1, 1);