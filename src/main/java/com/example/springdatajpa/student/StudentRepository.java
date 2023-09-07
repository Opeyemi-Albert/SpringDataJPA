package com.example.springdatajpa.student;

import com.example.springdatajpa.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE  s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findStudentByFirstNameAndAgeGreaterThan (String firstName, Integer age);

    @Query(value = "SELECT * FROM student s WHERE s.first_name = :firstName AND s.age >= :age",
            nativeQuery = true)
    List<Student> findStudentByFirstNameAndAgeGreaterThanNative (
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    int deleteStudentById(@Param("id") Long id);

}
