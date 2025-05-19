CREATE TABLE event_board (
    event_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    app_user_id BIGINT NOT NULL,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    location VARCHAR(255),
    view_cnt INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    insert_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (app_user_id) REFERENCES admin.app_user(app_user_id)
);

-- 이미지 테이블에 event_board_id 컬럼 추가
ALTER TABLE admin.image ADD COLUMN event_board_id BIGINT;
ALTER TABLE admin.image ADD CONSTRAINT fk_image_event_board FOREIGN KEY (event_board_id) REFERENCES event_board(event_id); 