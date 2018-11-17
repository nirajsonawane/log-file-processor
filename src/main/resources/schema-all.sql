DROP TABLE LOG_MESSAGES IF EXISTS;

CREATE TABLE LOG_MESSAGES  (
    id VARCHAR(30),
    state VARCHAR(30),
    event_type VARCHAR(20),
    host VARCHAR(30),
    event_time_stamp VARCHAR(30)
);
