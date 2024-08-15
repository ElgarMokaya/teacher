package com.example.teacher1.service;


import com.example.teacher1.exception.DuplicateEntryException;
import com.example.teacher1.model.Teacher;
import com.example.teacher1.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    public Teacher createTeacher(Teacher teacher){

        //Teacher teacher=new Teacher();
        if(teacherRepository.findByEmail(teacher.getEmail()).isPresent()){
            throw new DuplicateEntryException("A teacher with the email exists");
        }
        if(teacherRepository.findByPhoneNo(teacher.getPhoneNo()).isPresent()){
            throw new DuplicateEntryException("The phone number has already been used");

        }

        return  teacherRepository.save(teacher);


    }
    public List<Teacher> findAllTeachers(){

        return teacherRepository.findAll();
    }
    public Optional<Teacher> findByEmail(String email){

        return teacherRepository.findByEmail(email);

    }
    public Optional<Teacher>findById(Long id){
        return  teacherRepository.findById(id);
    }
    public Optional<Teacher> modifyTeacher(Long id){
        Optional<Teacher> teachermodified=  teacherRepository.findById(id);

        if(teachermodified.isPresent()){
            return teacherRepository.findById(id);
        }else {
            throw new EntityNotFoundException("Teacher with ID " + id + "not found");
        }

    }
    public void deleteTeacher(Long id){
        Optional<Teacher> foundTeacher=teacherRepository.findById(id);

        if(foundTeacher.isPresent()){
            teacherRepository.delete(foundTeacher.get());
        }else {
            throw new EntityNotFoundException("Teacher with id " + id + "not found");
        }


    }
}
