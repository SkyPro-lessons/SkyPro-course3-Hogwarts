package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getStudentByAgeBeetween(Integer min, Integer max) {
        if (min == null) {
            min = 0;
        } else if (max == null) {
            max = 120;
        }
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Student editStudent(Student student) {
        if (findStudent(student.getId()) != null) {
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        return studentRepository.findStudentByAge(age);
    }
}
