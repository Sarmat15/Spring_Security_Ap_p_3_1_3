insert into roles (authority) values ('ROLE_ADMIN'),('ROLE_USER');
insert into users (age,email,password,name,second_name) values (25,'admin@email.ru','$2a$12$0eve3CIl0ZPuhBWE9mkgluAWGjkp0D48gEzlaD2OzB0tYxSCXyBi2','admin','admin');
insert into users (age,email,password,name,second_name) values (28,'admin1@email.ru','$2a$12$2.Jila4g4ZWrwBgMN0.a8uhzJybWzyhiNalXZEK.BFd8.XyW5qdKq','admin2','admin2');
INSERT INTO users_role (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO users_role (`user_id`, `role_id`) VALUES ('2', '2');
INSERT INTO users_role (`user_id`, `role_id`) VALUES ('3', '1');
INSERT INTO users_role (`user_id`, `role_id`) VALUES ('4', '1');
INSERT INTO users_role (`user_id`, `role_id`) VALUES ('5', '2');