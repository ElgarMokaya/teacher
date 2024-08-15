package com.example.teacher1.repository;

import com.example.teacher1.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {


    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByPhoneNo(Long phoneNo);
}
