package com.example.teacher1.controller;


import com.example.teacher1.exception.DuplicateEntryException;
import com.example.teacher1.model.Teacher;
import com.example.teacher1.service.TeacherService;
import com.example.teacher1.utils.EntityResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher/")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/createUser")
    public ResponseEntity<EntityResponse<Teacher>> createTeacher(@RequestBody Teacher teacher){



        EntityResponse<Teacher> entityResponse=new EntityResponse<>();
        try {
            Teacher createdTeacher = teacherService.createTeacher(teacher);

            entityResponse.setStatus(HttpStatus.CREATED.value());
            entityResponse.setMessage("Teacher created successfully");
            entityResponse.setEntity(createdTeacher);
            return ResponseEntity.status(HttpStatus.CREATED).body(entityResponse);
        } catch (DuplicateEntryException e) {
            entityResponse.setEntity(null);
            entityResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            entityResponse.setMessage(e.getMessage());  // Specific message from the exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityResponse);
        } catch (Exception e) {
            // Handle any other exceptions
            entityResponse.setEntity(null);
            entityResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            entityResponse.setMessage("An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(entityResponse);
        }

    }
    @GetMapping("/find/{id}")
    public ResponseEntity<EntityResponse<Teacher>> findTeacherById(@PathVariable Long id){
        Optional<Teacher> foundTeacher=teacherService.findById(id);
      EntityResponse<Teacher> entityResponse=new  EntityResponse <>();
      if(foundTeacher.isPresent()){
          entityResponse.setStatus(HttpStatus.FOUND.value());
          entityResponse.setMessage("Teacher with id " + id + "has been found");
          entityResponse.setEntity(foundTeacher.get());
          return ResponseEntity.status(HttpStatus.FOUND).body(entityResponse);
    }else{
          entityResponse.setEntity(null);
          entityResponse.setStatus(HttpStatus.NOT_FOUND.value());
          entityResponse.setMessage("Teacher with id " + id + "not found");
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityResponse);
      }
    }
    @RequestMapping("/findAll")
    public ResponseEntity<EntityResponse <List<Teacher>>> findAllTeachers(){
        List<Teacher> allTeachers=teacherService.findAllTeachers();
        EntityResponse<List<Teacher>> entityResponse=new EntityResponse<>();

        if(allTeachers !=null && !allTeachers.isEmpty()){
            entityResponse.setMessage("teachers found");
            entityResponse.setStatus(HttpStatus.OK.value());
            entityResponse.setEntity(allTeachers);
            return ResponseEntity.status(HttpStatus.OK).body(entityResponse);
        }else{
            entityResponse.setEntity(Collections.emptyList());
            entityResponse.setMessage("not found");
            entityResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(entityResponse);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id){
        EntityResponse<Void> entityResponse = new EntityResponse<>();

        try {
            teacherService.deleteTeacher(id);
            entityResponse.setMessage("Teacher with id " + id + " deleted successfully");
            entityResponse.setStatus(HttpStatus.NO_CONTENT.value());
            entityResponse.setEntity(null); // No content to return

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
       }catch (EntityNotFoundException e){
           entityResponse.setMessage("Teacher with id " + id + "not found");
           entityResponse.setStatus(HttpStatus.NOT_FOUND.value());
           entityResponse.setEntity(null);
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

       }
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<EntityResponse<Teacher>> modifyTeacher(@PathVariable Long id,@RequestBody Teacher newTeacher){

        Optional<Teacher> modifiedTeacher=teacherService.modifyTeacher(id);

        EntityResponse<Teacher> entityResponse=new EntityResponse<>();
        try{
            if(modifiedTeacher.isPresent()){
                Teacher teacher=modifiedTeacher.get();
                teacher.setEmail(newTeacher.getEmail());
                teacher.setPassword(newTeacher.getPassword());
                teacher.setGender(newTeacher.getGender());
                teacher.setFirstName(newTeacher.getFirstName());
                teacher.setPhoneNo(newTeacher.getPhoneNo());
               teacher.setLastName(newTeacher.getLastName());
               entityResponse.setEntity(teacher);
               entityResponse.setStatus(HttpStatus.OK.value());
               entityResponse.setMessage("Teacher with id " + id + "modified successfully");
               return ResponseEntity.ok(entityResponse);

            }else{
                entityResponse.setMessage("Teacher not found");
                entityResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityResponse);
            }
        }catch(EntityNotFoundException e){
            entityResponse.setMessage("Error: " + e.getMessage());
            entityResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(entityResponse);
        }

    }
}
