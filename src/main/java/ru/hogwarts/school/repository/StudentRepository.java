package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentByAge(int age);

    Collection<Student> findStudentByAgeBetween(int min, int max);

    Collection<Student> findStudentByFacultyName(String facultyName);

}
