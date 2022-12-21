-- liquibase formatted sql



-- 1) Индекс для поиска по имени студента.
-- changeset starasov:1
CREATE INDEX students_name_index ON student (name);




-- 2) Индекс для поиска по названию и цвету факультета.
-- changeset starasov:2
CREATE INDEX faculties_name_and_color_index ON faculty (name, color);