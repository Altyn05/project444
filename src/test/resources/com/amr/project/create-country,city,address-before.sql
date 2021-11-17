delete from addresses;
delete from cities;
delete from countries;

ALTER TABLE countries AUTO_INCREMENT=1;
ALTER TABLE cities AUTO_INCREMENT=1;
ALTER TABLE addresses AUTO_INCREMENT=1;

insert into countries(name)
values ('Ukraine'),
       ('Russia');

insert into cities(name, country_id)
values ('Mirgorod', 1),
       ('Kiev', 1),
       ('Tambov', 2),
       ('Moscow', 2);

insert into addresses(city_index, house, street, city_id, country_id)
VALUES ('123456', '123', 'banderi', 1, 1),
       ('111111', '111', 'kievskaya', 2, 1),
       ('222222', '222', 'moskovskaya', 3, 2),
       ('333333', '333', 'тамбовская', 4, 2);