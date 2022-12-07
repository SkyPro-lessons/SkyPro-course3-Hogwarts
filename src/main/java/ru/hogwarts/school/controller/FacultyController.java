package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);

        if (null == faculty) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(faculty);
    }

    @GetMapping("color")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@RequestParam String color) {
        Collection<Faculty> faculties = facultyService.getFacultiesByColor(color);

        if (faculties.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(faculties);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = facultyService.getAllFaculties();
        return (faculties == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(faculties);
    }

    @GetMapping("name-or-color")
    public ResponseEntity<Faculty> getFacultiesByNameOrColor(@RequestParam String nameOrColor) {
        Faculty faculty = facultyService.getFacultyByNameOrColor(nameOrColor);

        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsByFacultyId(@PathVariable Long id) {
        Collection<Student> foundStudents = facultyService.getStudentsByFaculty(id);

        if (foundStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundStudents);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaclulty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);

        if (null == foundFaculty) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
}


