delete from users_roles;
delete from users;
delete from roles;

ALTER TABLE users AUTO_INCREMENT=1;
ALTER TABLE roles AUTO_INCREMENT=1;
insert into users(activate, password, username, age, first_name, last_name)
VALUES (true, '1234', 'admin', 21, 'Alex', 'Baranov'),
       (true, '0000', 'user', 21, 'Sanek', 'Salamov');

insert into roles(name)
values ('ADMIN'),
       ('USER');

insert into users_roles(user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);



