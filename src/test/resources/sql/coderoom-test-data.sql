-- 외래키 제약 조건 비활성화 후 테이블 삭제
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS user_code_room;
DROP TABLE IF EXISTS code_room;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

-- 테이블 생성
CREATE TABLE users (
                       users_id BIGINT PRIMARY KEY,
                       email VARCHAR(255),
                       image_url VARCHAR(255),
                       is_activated BOOLEAN NOT NULL,
                       is_banned BOOLEAN NOT NULL,
                       nickname VARCHAR(255),
                       password VARCHAR(255),
                       role ENUM('ADMIN', 'USER') DEFAULT 'USER',
                       social ENUM('NAVER', 'THIS_CODING') DEFAULT 'THIS_CODING'
);

CREATE TABLE code_room (
                           code_room_id BIGINT PRIMARY KEY,
                           content VARCHAR(255),
                           head_count INT,
                           language VARCHAR(255),
                           title VARCHAR(255),
                           uuid VARCHAR(255)
);

CREATE TABLE user_code_room (
                                user_code_room_id BIGINT PRIMARY KEY,
                                is_activated BOOLEAN NOT NULL,
                                users_id BIGINT,
                                code_room_id BIGINT,
                                foreign key (users_id) references users(users_id),
                                foreign key (code_room_id) references code_room(code_room_id)
);

-- 더미 데이터 삽입
INSERT INTO users (users_id, email, password, nickname, image_url, is_activated, is_banned, role, social)
VALUES
    (1, 'test01@test.com', 'pw01', 'test01', 'http://example.com/image1.png', true, false, 'USER', 'THIS_CODING'),
    (2, 'test02@test.com', 'pw02', 'test02', 'http://example.com/image2.png', true, false, 'USER', 'NAVER'),
    (3, 'test03@test.com', 'pw03', 'test03', 'http://example.com/image3.png', true, false, 'USER', 'NAVER'),
    (4, 'test04@test.com', 'pw04', 'test04', 'http://example.com/image4.png', true, false, 'USER', 'THIS_CODING'),
    (5, 'test05@test.com', 'pw05', 'test05', 'http://example.com/image5.png', true, false, 'ADMIN', 'THIS_CODING');

INSERT INTO code_room (code_room_id, uuid, title, content, language, head_count)
VALUES
    (1, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'teach me', 'how to print the hello world?', 'Java', 5);

INSERT INTO user_code_room (user_code_room_id, users_id, code_room_id, is_activated)
VALUES
    (1, 1, 1, true),
    (2, 2, 1, true),
    (3, 3, 1, true),
    (4, 4, 1, true),
    (5, 5, 1, true);