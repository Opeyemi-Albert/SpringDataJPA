package com.example.springdatajpa.book;

import com.example.springdatajpa.Model;
import com.example.springdatajpa.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter @Accessors(chain = true, fluent = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@Entity(name = "Book")
public class Book extends Model {

    @Column(name = "book_name", nullable = false, columnDefinition = "TEXT")
    private String bookName;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_book_fk"))
    private Student student;

    public Book(String bookName) {
        this.bookName = bookName;
    }
}
