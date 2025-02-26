create table positions
(
    id     serial primary key,
    name   varchar(100)   not null,
    salary decimal(10, 2) not null
);

create table employees
(
    id         serial primary key,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    email      varchar(50) not null,
    phone      varchar(20) not null
);

create table departments
(
    id         serial primary key,
    name       varchar(50)                   not null,
    manager_id int references employees (id) not null
);