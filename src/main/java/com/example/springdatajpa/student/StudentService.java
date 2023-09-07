package com.example.springdatajpa.student;

import com.example.springdatajpa.student.Student;
import com.example.springdatajpa.student.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }
}
