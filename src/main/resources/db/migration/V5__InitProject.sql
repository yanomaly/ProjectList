insert into projects (name, head, date_finish, user_id, budget, is_delete) VALUES
                                                                                  ('Coffee shop', 'Marc', '2025-04-01', 0, 100000, false),
                                                                                  ('Fun Shop', 'Mario', '2023-01-10', 1, 5000, false),
                                                                                  ('Mars colonization', 'Elon', '2030-11-29', 1, 250000000, false);

insert into problems (description, name, is_delete, project_id) VALUES
                                                                       ('Star ship', 'Build huge space people carrier', false, 3),
                                                                       ('Falcon 9', 'Successfully land 1-st stage of rocket', false, 3),
                                                                       ('Falcon heavy', 'Launch Tesla into space', false, 3);