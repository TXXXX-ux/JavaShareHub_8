-- Пароли для этих двух тестовых аккаунтов: 123456
INSERT INTO users (email, password, role, enabled)
VALUES ('admin@gmail.com', '$2a$10$4h8MvwLR3AgYyZ8UrAUObei0SjMtraI2q4MdbrmobgQBtUKj2Qm9e', 'ROLE_ADMIN', true),
       ('test@gmail.com', '$2a$10$4h8MvwLR3AgYyZ8UrAUObei0SjMtraI2q4MdbrmobgQBtUKj2Qm9e', 'ROLE_USER', true);

INSERT INTO files (name, storage_path, is_public, unique_key, download_count, category, uploader_id)
VALUES ('message_icon.jpg', 'data/message_icon.jpg', true, null, 15, 'Разное', 1),
       ('global.jpg', 'data/global.jpg', true, null, 5, 'Разное', 2),
       ('package.jpg', 'data/package.jpg', true, null, 0, 'Разное', 1);