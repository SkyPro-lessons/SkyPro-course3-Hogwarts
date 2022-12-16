package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentByAge(int age);

    Student findStudentById(Long id);

    Collection<Student> findStudentByAgeBetween(int min, int max);

    Collection<Student> findStudentByFacultyId(long id);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    int getAllStudentsCount();
}
