delete from roles;
delete from users;
delete from users_roles;

insert into users(activate, password, username) VALUES
(true, '1234', 'admin'),
(true, '0000', 'user');

insert into roles(name) values
('ADMIN'), ('USER');

insert into  users_roles(user_id, role_id) VALUES
(1,1),(1,2),
(2,2);



