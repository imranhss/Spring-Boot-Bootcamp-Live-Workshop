package com.emranhss.sptingbootbotcamp.service;


import com.emranhss.sptingbootbotcamp.entity.Student;
import com.emranhss.sptingbootbotcamp.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {


    @Autowired
    private  StudentRepository studentRepository;

    @Value("src/main/resources/static/images")
    private  String uploadDir;

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    public void saveStudent(Student s, MultipartFile imageFile) throws IOException {

        if(imageFile != null && !imageFile.isEmpty()){
            String imageFileName=saveImage(imageFile, s);
            s.setImage(imageFileName);
        }


        studentRepository.save(s);

    }

    public Student getStudentById(int id){
        return studentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with Id: "+id));
    }

    public void deleteStudent(int id){
        if(!studentRepository.existsById(id)){
            throw  new EntityNotFoundException("Student not found with Id: "+id);
        }
        else{
            studentRepository.deleteById(id);
        }

    }

    public  Student updateStudent(int id, Student updateStudent){

        Student existingStudent=studentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with Id: "+id));

        if(updateStudent.getName() != null){

            existingStudent.setName(updateStudent.getName());
        }
        if(updateStudent.getEmail() != null){

            existingStudent.setEmail(updateStudent.getEmail());
        }

         if(updateStudent.getCellNo() != null){

            existingStudent.setCellNo(updateStudent.getCellNo());
        }


         return  studentRepository.save(existingStudent);


    }

    public void updateStudent(Student updateStudent){

        studentRepository.save(updateStudent);
    }


    public Student findStudentByEmail(String email){

        return   studentRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with Email : "+email));
    }


    private  String saveImage(MultipartFile file, Student s) throws IOException {
        Path uploadPath= Paths.get(uploadDir+"/students");
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileName=s.getName()+"_"+ UUID.randomUUID().toString();
        // sazid mahmus_gsdfhgsdjgfsdgfjfhj
        Path filePath=uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }






}
