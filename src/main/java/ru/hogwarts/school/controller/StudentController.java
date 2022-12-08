package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        Collection<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }


    @GetMapping("age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer age) {
        Collection<Student> foundStudents = studentService.getStudentsByAge(age);
        return ResponseEntity.ok(foundStudents);
    }

    @GetMapping("ages")
    public ResponseEntity<Collection<Student>> getStudentsByMinMaxAge(@RequestParam Integer min, @RequestParam Integer max) {
        Collection<Student> foundStudents = studentService.getStudentByAgeBeetween(min, max);
        return ResponseEntity.ok(foundStudents);
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long id) {
        Faculty foundFaculty = studentService.getFacultyByStudent(id);
        return ResponseEntity.ok(foundFaculty);
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.ok(newStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }


    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

}
