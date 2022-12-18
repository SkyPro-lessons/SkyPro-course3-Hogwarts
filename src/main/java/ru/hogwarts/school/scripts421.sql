
/* Имена студентов должны быть уникальными и не равны нулю.*/
ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age >= 16);

/* Имена студентов должны быть уникальными и не равны нулю.*/
ALTER TABLE student
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT name_unique UNIQUE (name);

/* Пара “значение названия” - “цвет факультета” должна быть уникальной.*/
ALTER TABLE student
    ADD CONSTRAINT name_faculty_unique UNIQUE (name, faculty_id);

/* При создании студента без возраста ему автоматически должно присваиваться 20 лет.*/
ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;
