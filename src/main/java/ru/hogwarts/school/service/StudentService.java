package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        if (student.getAge() < 0 || student.getAge() > 120) {
            return null;
        }
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student by id");
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getStudentByAgeBeetween(Integer min, Integer max) {
        logger.info("Was invoked method for find students by age beetween two digits");
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        if (findStudent(student.getId()) != null) {
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student by id");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        logger.info("Was invoked method for find students by age");
        return studentRepository.findStudentByAge(age);
    }

    public Faculty getFacultyByStudent(Long studentId) {
        logger.info("Was invoked method for find faculty by student id");
        logger.debug("Requesting find faculty by student id: {}", studentId);
        Student student = studentRepository.findStudentById(studentId);
        Faculty result = facultyRepository.findFacultyByStudents(student);
        logger.debug("{} faculty found by student id: {}", result, studentId);
        return result;
    }

    public int getCountAllStudents() {
        logger.info("Was invoked method for get count students");
        return studentRepository.getCountAllStudents();
    }

    public double getAverageAgeAllStudents() {
        logger.info("Was invoked method for get average age from all students");
        return studentRepository.getAverageAgeAllStudents();
    }

    public Collection<Student> getFiveLastStudents() {
        logger.info("Was invoked method for get five last students");
        return studentRepository.getFiveLastStudents();
    }

    public Collection<String> getAllStudentsStartingLetterA() {
        logger.info("Was invoked method for get all student names, starting letter A");
        Collection<Student> students = this.getAllStudents();
        return students.stream()
                .map(e -> e.getName().toUpperCase(Locale.ROOT))
                .filter(e -> e.startsWith("A"))
                .sorted()
                .toList();
    }

    public Double getAverageAge() {
        logger.info("Was invoked method for get average age for all students");
        Collection<Student> students = this.getAllStudents();
        return students.stream()
                .mapToInt(e -> e.getAge())
                .average()
                .orElse(0);
    }

    public void printFewStudentsThreads() {
        logger.info("Was invoked method for print few students in different threads");
        List<Student> students = new ArrayList<>(this.getAllStudents());

        new Thread(() -> {
            printStudent(students, "Поток 1", 2);
            printStudent(students, "Поток 1", 3);
        }).start();

        new Thread(() -> {
            printStudent(students, "Поток 2", 4);
            printStudent(students, "Поток 2", 5);
        }).start();

        printStudent(students, "Основной поток", 0);
        printStudent(students, "Основной поток", 1);
    }

    private void printStudent(List<Student> students, String message, int number) {
        System.out.println(message + ": #" + number + ": " + students.get(number).getName());

        String s = "";
        for (int i = 0; i < 1000; i++) {
            s += i;
        }
    }
}
