package com.example.teacher1.service;

import com.example.teacher1.model.Teacher;
import com.example.teacher1.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailSService implements UserDetailsService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

     Optional<Teacher> optionalTeacher=teacherRepository.findByEmail(email);

     Teacher teacher=optionalTeacher.orElseThrow(()-> new UsernameNotFoundException("user with email " + email + "not found"));
     return  teacher;

//        return optionalTeacher.orElse(()-> new UsernameNotFoundException("User with email " + email + " does not exist"));
    }
}
