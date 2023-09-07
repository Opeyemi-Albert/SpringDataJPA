package com.example.springdatajpa.student;


import com.example.springdatajpa.Model;
import com.example.springdatajpa.book.Book;
import com.example.springdatajpa.course.Course;
import com.example.springdatajpa.enrolment.Enrolment;
import com.example.springdatajpa.idCard.StudentIdCard;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Student")
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(name = "student_email_unique", columnNames = "email"),
        @UniqueConstraint(name = "student_id_unique", columnNames = "student_id_card_id")})
public class Student extends Model {

    private String studentId;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name="email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "student_id_card_id", referencedColumnName = "id", nullable = false,
                    foreignKey = @ForeignKey(name = "student_id_card_fk"))
    private StudentIdCard studentIdCard;

/*    @ManyToMany (cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "enrolment",
            joinColumns = @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "enrolment_student_fk")),
            inverseJoinColumns = @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "enrolment_course_fk"))
    )
    private List<Course> courses = new ArrayList<>();*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<Enrolment> enrolments = new ArrayList<>();
    @OneToMany (mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

  /*  public void addCourse(Course course) {
        if(!this.courses.contains(course)) {
            this.courses.add(course);
            course.getStudent().add(this);
        }
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudent().remove(this);
    }*/

    public void addEnrolment(Enrolment enrolment){
        if(!this.enrolments.contains(enrolment)) {
            this.enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment){
        this.enrolments.remove(enrolment);
    }
    public void addBook(Book book) {
        if(!this.books.contains(book)){
            this.books.add(book);
            book.student(this);
        }
    }

    public void removeBook(Book book){
        if(this.books.contains(book)){
            books.remove(book);
            book.student(null);
        }
    }

    public Student(String firstName,
                   String lastName,
                   String email,
                   int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    @Transient
    public String getName(){
        return String.format("%s %s", firstName, lastName);
    }

    @PrePersist
    public void onCreate(){
        studentId = String.valueOf(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
