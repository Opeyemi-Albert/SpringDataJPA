package com.example.springdatajpa.course;

import com.example.springdatajpa.Model;
import com.example.springdatajpa.enrolment.Enrolment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
@Entity(name = "Course")
public class Course extends Model {

    @Column(name = "course_name", nullable = false, columnDefinition = "TEXT")
    private String courseName;

    @Column(name = "department_name", nullable = false, columnDefinition = "TEXT")
    private String department;
/*
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> student;*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Enrolment> enrolments = new ArrayList<>();

    public Course(String courseName, String department) {
        this.courseName = courseName;
        this.department = department;
    }

    public void addEnrolment(Enrolment enrolment){
        if(!this.enrolments.contains(enrolment)) {
            this.enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment){
        this.enrolments.remove(enrolment);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
