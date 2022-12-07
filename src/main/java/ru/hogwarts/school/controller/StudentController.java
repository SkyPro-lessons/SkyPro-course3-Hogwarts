package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (null == student) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        Collection<Student> students = studentService.getAllStudents();

        if (null == students) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(students);
    }


    @GetMapping("age")

    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer age) {
        Collection<Student> foundStudents = studentService.getStudentsByAge(age);

        if (null == foundStudents) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundStudents);
    }

    @GetMapping("ages")
    public ResponseEntity<Collection<Student>> getStudentsByMinMaxAge(@RequestParam Integer min, @RequestParam Integer max) {
        Collection<Student> foundStudents = studentService.getStudentByAgeBeetween(min, max);

        if (null == foundStudents) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundStudents);
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long id) {
        Faculty foundFaculty = studentService.getFacultyByStudent(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);

        if (null == newStudent) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(newStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (null == foundStudent) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
