package com.emranhss.sptingbootbotcamp.restcontroller;


import com.emranhss.sptingbootbotcamp.entity.Student;
import com.emranhss.sptingbootbotcamp.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    @Autowired
    private  StudentService studentService;


    @GetMapping("/")
    public ResponseEntity<List<Student>>  getAllStudents(){

        List<Student> studentsList=studentService.getAllStudents();

        return ResponseEntity.ok(studentsList);

    }


    @PostMapping("/save")
    public ResponseEntity<String> saveStudent(
            @RequestPart Student s,
            @RequestParam(value = "image", required = true)MultipartFile file
    ) throws IOException {
        studentService.saveStudent(s, file);

        return  new ResponseEntity<>("Student saved Successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {

        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student with this ID " + id + " has been Deleted");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student s){

        Student updateStudent=studentService.updateStudent(id, s);

        return ResponseEntity.ok(updateStudent);

    }


    @GetMapping("/{email}")
    public ResponseEntity<Student >  findStudentByEmail(@PathVariable String email){

        Student updateStudent=studentService.findStudentByEmail(email);

        return ResponseEntity.ok(updateStudent);
    }


















}
