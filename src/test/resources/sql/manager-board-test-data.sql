-- 테이블 생성
CREATE TABLE manager_board (
                                id BIGINT PRIMARY KEY,
                                manager VARCHAR(255),
                                title VARCHAR(255),
                                category VARCHAR(255),
                                view_count INT,
                                create_date TIMESTAMP
);

-- 더미 데이터 삽입
INSERT INTO manager_board (id, manager, title, category, view_count, create_date)
VALUES
    (1, 'Manager 1', 'Notice Title 1', 'Category A', 10, '2025-01-01 09:00:00'),
    (2, 'Manager 2', 'Notice Title 2', 'Category B', 20, '2025-01-02 10:00:00'),
    (3, 'Manager 3', 'Notice Title 3', 'Category C', 30, '2025-01-03 11:00:00'),
    (4, 'Manager 4', 'Notice Title 4', 'Category A', 40, '2025-01-04 12:00:00'),
    (5, 'Manager 5', 'Notice Title 5', 'Category B', 50, '2025-01-05 13:00:00'),
    (6, 'Manager 6', 'Notice Title 6', 'Category C', 60, '2025-01-06 14:00:00'),
    (7, 'Manager 7', 'Notice Title 7', 'Category A', 70, '2025-01-07 15:00:00'),
    (8, 'Manager 8', 'Notice Title 8', 'Category B', 80, '2025-01-08 16:00:00'),
    (9, 'Manager 9', 'Notice Title 9', 'Category C', 90, '2025-01-09 17:00:00'),
    (10, 'Manager 10', 'Notice Title 10', 'Category A', 100, '2025-01-10 18:00:00');
