package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        if (student.getAge() < 0 || student.getAge() > 120) {
            return null;
        }
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getStudentByAgeBeetween(Integer min, Integer max) {
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Student editStudent(Student student) {
        if (findStudent(student.getId()) != null) {
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        return studentRepository.findStudentByAge(age);
    }

    public Faculty getFacultyByStudent(Long studentId) {
        Student student = studentRepository.findStudentById(studentId);
        return facultyRepository.findFacultyByStudents(student);
    }
}
