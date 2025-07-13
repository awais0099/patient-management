-- V1__Create_patient_events_table.sql

CREATE TABLE patient_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_timestamp DATETIME NOT NULL
);
