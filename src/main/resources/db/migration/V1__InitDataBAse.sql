create table problems (
    problem_id int8 generated by default as identity,
    description varchar(2048),
    is_delete boolean,
    name varchar(255),
    project_id int8,
    primary key (problem_id)
                      );

create table projects (
    project_id int8 generated by default as identity,
    budget int4,
    date_finish timestamp,
    head varchar(255),
    is_delete boolean,
    name varchar(255),
    user_id int8,
    primary key (project_id)
                      );

create table roles (
    role_id int8 generated by default as identity,
    name varchar(255),
    primary key (role_id)
                   );

create table users (
    user_id int8 generated by default as identity,
    is_delete boolean,
    password varchar(255),
    username varchar(255),
    primary key (user_id)
                   );

create table users_role (
    user_user_id int8 not null,
    role_role_id int8 not null,
    primary key (user_user_id, role_role_id)
                        );

alter table if exists users_role add constraint roles_users_fk foreign key (role_role_id) references roles;
alter table if exists users_role add constraint users_roles_fk foreign key (user_user_id) references users;