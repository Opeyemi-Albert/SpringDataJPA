package com.example.springdatajpa.idCard;

import com.example.springdatajpa.Model;
import com.example.springdatajpa.student.Student;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter @Accessors(chain = true, fluent = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "StudentIdCard")
@Table(name = "student_id_card", uniqueConstraints = {
        @UniqueConstraint(name = "student_id_card_number_unique", columnNames = "card_number")})
public class StudentIdCard extends Model {

    @OneToOne(mappedBy = "studentIdCard")
    private Student student;

    @Column(name = "card_number", nullable = false, length = 15)
    private String cardNumber;

    @Override
    public String toString() {
        return "{cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
