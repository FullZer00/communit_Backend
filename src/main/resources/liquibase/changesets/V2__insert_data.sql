insert into roles (name, ru_name)
values ('MANAGER', 'Менеджер'),
       ('INSTALLER', 'Монтажник'),
       ('ARCHITECT', 'Архитектор'),
       ('ACCOUNTANT', 'Бухгалтер'),
       ('CLIENT', 'Клиент'),
       ('DIRECTOR', 'Директор');

insert into companies (name, address)
values ('ООО "Криогенмаш"', 'г. Балашиха, ул. Красная поляна, д.15ст3.');

insert into user_data (first_name, surname, email, phone, password, salt_password)
values ('Владислав', 'Гнусаров', 'gnusarovvladislav@gmail.com', '89686298965', '$2a$04$4p.Mc2R7f37s7yZVDnO4ju.SwQiG.QloPO70unZdOnQSEDear1RVC', 'FadP'),
       ('Александр', 'Иванов',  'aliv@gmail.com', '89686567895', '$2a$04$4p.Mc2R7f37s7yZVDnO4ju.SwQiG.QloPO70unZdOnQSEDear1RVC', 'FadP'),
       ('Степан', 'Седирюк',  'sediruk@gmail.com', '89145667895', null, null);

insert into clients (user_data_id, company_id)
values (3, 1);

insert into workers (user_data_id, passport_seria, passport_number, rate)
values (1, '4528', '123456', 60000),
       (2, '1234', '567890', 100000);

insert into users_roles(user_id, role_id)
values (1, 2), (2, 3), (3, 5);

insert into projects (name, description, location, company_id, client_id)
values ('Монтаж камер Криогенмаш', '', 'г. Балашиха...', 1, 1);

insert into projects_workers (project_id, worker_id)
values (1, 1), (1, 2);


