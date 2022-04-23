/* all passwords is 111111 */

insert into users (username, password, is_delete)  VALUES
                                                                   ('Marc', '$2a$10$UmqAj0xbxmKJnzbcBhTygu9ydebkKWX9zQs.wala7zeU2brLMTNDG', false),
                                                                   ('Mario', '$2a$10$UmqAj0xbxmKJnzbcBhTygu9ydebkKWX9zQs.wala7zeU2brLMTNDG', false),
                                                                   ('Bastian', '$2a$10$UmqAj0xbxmKJnzbcBhTygu9ydebkKWX9zQs.wala7zeU2brLMTNDG', false);

insert into users_role (user_user_id, role_role_id) VALUES
                                                           (1, 1),
                                                           (2, 1),
                                                           (3, 1);

