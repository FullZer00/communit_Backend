create schema if not exists communit;

create table if not exists companies
(
    id bigserial primary key,
    name varchar(255) not null unique,
    address varchar(255) not null
    );

create table if not exists roles(
                                    id bigserial primary key,
                                    name varchar(255) not null,
    ru_name varchar(100)
    );

create table if not exists user_data (
                                         id bigserial primary key,
                                         first_name varchar(100) not null,
    surname varchar(100) not null,
    patronymic varchar(100),
    email varchar(100) unique,
    phone varchar(20),
    password varchar(255),
    salt_password varchar(10)
    );

create table if not exists clients (
                                       id bigserial primary key,
                                       user_data_id bigint not null,
                                       company_id bigint not null,
                                       constraint fk_clients_user_data foreign key (user_data_id) references user_data(id) on update cascade,
    constraint fk_clients_company foreign key (company_id) references companies(id) on update cascade
    );

create table if not exists workers (
                                       id bigserial primary key,
                                       user_data_id bigint not null,
                                       passport_seria varchar(10) default(null),
    passport_number varchar(12) default(null),
    rate decimal(10, 2) default(0) not null
    );

create table if not exists users_roles (
                                           user_id bigint not null,
                                           role_id bigint not null,
                                           primary key (user_id, role_id),
    constraint users_roles_user foreign key (user_id) references user_data(id) on delete cascade on update cascade,
    constraint users_roles_role foreign key (role_id) references roles(id) on delete cascade on update cascade
    );

create table if not exists projects (
                                        id bigserial primary key,
                                        name varchar(255) not null,
    description text,
    location varchar(255) not null,
    company_id bigint not null,
    client_id bigint not null,
    constraint fk_projects_company foreign key(company_id) references companies(id) on update cascade,
    constraint fk_projects_client foreign key(client_id) references clients(id) on update cascade
    );

create table if not exists projects_workers (
                                                project_id bigint not null,
                                                worker_id bigint not null,
                                                primary key (project_id, worker_id),
    constraint projects_workers_worker foreign key(worker_id) references workers(id) on delete cascade on update cascade,
    constraint projects_workers_project foreign key(project_id) references projects(id) on delete cascade on update cascade
    );