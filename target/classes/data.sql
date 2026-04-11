INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$7zNfukJLBraFhx.Q.hKDk..aj3it8DMEcY4.hPTXJLNwGLD1zBH.i', 'ROLE_ADMIN');

INSERT INTO users (username, password, role)
VALUES ('teacher', '$2a$10$mYl7IEhVa3hYm660fdh4tepfNQ2hBWvV6ScBlEm2/wBFKbgEyDzre', 'ROLE_TEACHER');

INSERT INTO users (username, password, role)
VALUES ('student', '$2a$10$ZA.VYufohD38Lt/EdqSLZOOep5DIAVn7B70B4ZwgqAdxQTOJQMePa', 'ROLE_STUDENT');


INSERT INTO assignment (title, course_name, due_date, priority, status, description, owner_username, created_at)
VALUES ('Project Deliverable 1', 'CPAN 228', '2026-03-15', 'HIGH', 'COMPLETED', 'Web Front-End & Database Integration', 'teacher', CURRENT_TIMESTAMP);

INSERT INTO assignment (title, course_name, due_date, priority, status, description, owner_username, created_at)
VALUES ('Project Deliverable 2', 'CPAN 228', '2026-03-29', 'MEDIUM', 'IN_PROGRESS', 'Security and User Management', 'teacher', CURRENT_TIMESTAMP);

INSERT INTO assignment (title, course_name, due_date, priority, status, description, owner_username, created_at)
VALUES ('Project Deliverable 3: Final Project', 'CPAN 228', '2026-04-16', 'LOW', 'NOT_STARTED', 'Microservices, REST APIs & DevOps (incl. presentation)', 'admin', CURRENT_TIMESTAMP);


INSERT INTO task (title, description, due_date, owner_username, created_at)
VALUES ('Read chapter 4', 'Summarize notes', '2026-04-12', 'student', CURRENT_TIMESTAMP);

INSERT INTO task (title, description, due_date, owner_username, created_at)
VALUES ('Prepare demo', 'Test role features', '2026-04-13', 'admin', CURRENT_TIMESTAMP);