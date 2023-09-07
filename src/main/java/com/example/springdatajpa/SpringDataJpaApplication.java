package com.example.springdatajpa;

import com.example.springdatajpa.book.Book;
import com.example.springdatajpa.book.BookRepository;
import com.example.springdatajpa.course.Course;
import com.example.springdatajpa.enrolment.EnrollmentId;
import com.example.springdatajpa.enrolment.Enrolment;
import com.example.springdatajpa.idCard.StudentIdCard;
import com.example.springdatajpa.idCard.StudentIdCardRepository;
import com.example.springdatajpa.student.Student;
import com.example.springdatajpa.student.StudentRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class SpringDataJpaApplication {

    private final StudentIdCardRepository idCardRepository;
    private final StudentRepository studentRepository;

    public SpringDataJpaApplication(StudentIdCardRepository idCardRepository,
                                    StudentRepository studentRepository) {
        this.idCardRepository = idCardRepository;
        this.studentRepository = studentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
        log.info(
                "\n\n ============================ APPLICATION LAUNCHED ======================= \n\n");
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Lagos"));
        System.out.println(LocalDateTime.now());
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository idCardRepository, BookRepository bookRepository) {
        return args -> {
            generateStudentIdCard(studentRepository, idCardRepository);
           // createBooks(bookRepository);

        };
    }

    public void pagination(StudentRepository studentRepository){
        PageRequest pageRequest = PageRequest.of(
                0,
                5,
               Sort.by(Sort.Direction.ASC, "firstName"));
        Page<Student> page = studentRepository.findAll(pageRequest);
        System.out.println(page);
    }
    public void sorting(StudentRepository studentRepository){

        // Using Type-safe API
        Sort.TypedSort<Student> student = Sort.sort(Student.class);
        Sort sortp = student.by(Student::getName).ascending();
        studentRepository.findAll(sortp)
                .forEach(s -> System.out.println(s.getFirstName()+
                        " "+ s.getLastName() + " "+ s.getEmail()));

        // Using Sort API
        Sort sort1 = Sort.by(Sort.Direction.ASC, "firstName");
        studentRepository.findAll(sort1)
                .forEach(s -> System.out.println(s.getFirstName()+
                        " "+ s.getLastName() + " "+ s.getEmail()));

        log.info("\n\n =================================================== \n\n");

        Sort sort2 = Sort.by( "firstName").descending();
        studentRepository.findAll(sort2)
                .forEach(System.out::println);
    }
    public void generateStudent(StudentRepository studentRepository
                                ){
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().firstName();
            String email = String.format("%s.%s@babcock.edu", firstName, lastName);
            int age = faker.number().numberBetween(11, 18);
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            student.setAge(age);
            studentRepository.save(student);
        }
    }

    public void generateStudentIdCard(StudentRepository studentRepository, StudentIdCardRepository idCardRepository){
        Faker faker = new Faker();
        for (int i = 0; i < 5; i++) {

            String firstName = faker.name().firstName();
            String lastName = faker.name().firstName();
            String email = String.format("%s.%s@babcock.edu", firstName, lastName);
            int age = faker.number().numberBetween(11, 18);
            String cardNumber = String.valueOf(faker.number().numberBetween(10000, 35000));

            StudentIdCard studentIdCard = new StudentIdCard();
            studentIdCard.cardNumber(cardNumber);


            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            student.setAge(age);
            student.setStudentIdCard(studentIdCard);


            student.addBook(new Book("The Lion King"));
            student.addBook(new Book("The Man's Wife"));
            student.addBook(new Book("The Bible"));

            /*student.addCourse(new Course("CHM 101", "Biochemistry"));
            student.addCourse(new Course("CHM 111", "Biochemistry"));
            student.addCourse(new Course("CHM 123", "Biochemistry"));*/

            student.addEnrolment(new Enrolment( new EnrollmentId(1L, 2L),
                    student,
                    new Course("CHM 101", "Biochemistry"), LocalDateTime.now() ));

            /*student.addEnrolment(new Enrolment(new EnrollmentId(1L, 2L),
                    student,
                    new Course("CHM 112", "Biochemistry") ));*/

            studentRepository.save(student);


            studentRepository.findById(1L)
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Not Found"));

            studentRepository.findById(1L)
                    .ifPresentOrElse(s -> {
                        System.out.println("fetch book lazy");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                                System.out.println(s.getName() + " borrowed " + book.bookName());
                        });

                    }, () -> System.out.println("Not Found"));
//
//            idCardRepository.findById(2L)
//                    .ifPresentOrElse(System.out::println, () -> System.out.println("Not Found"));
        }
    }


/*
        @Bean
        CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student maria = new Student(
                    "Maria",
                    "Jones",
                    "maria@gmail.com",
                    22
            );
            Student james = new Student(
                    "James",
                    "Jones",
                    "jame.jones@gmail.com",
                    25
            );

            Student james2 = new Student(
                    "James",
                    "Jones",
                    "jame2.jones@gmail.com",
                    21
            );
            //Adding Maria and James to DB
            System.out.println("Adding maria and james to DB ");
            studentRepository.saveAll(List.of(maria, james, james2));

            //Count the number of students
            System.out.println("Number of students: ");
            System.out.print(studentRepository.count());

            studentRepository
                    .findById(1L)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("student with id 1 not found"));

            studentRepository
                    .findById(3L)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("student with id 3 not found"));

            List<Student> studentList = studentRepository.findAll();
            studentList.forEach(System.out::println);

            studentRepository
                    .findStudentByEmail("jame.jones@gmail.com")
                    .ifPresentOrElse(System.out::println,
                            () -> System.out.println("Student with email not found"));

            studentRepository
                    .findStudentByFirstNameAndAgeGreaterThan("James", 15)
                    .forEach(System.out::println);

            studentRepository
                    .findStudentByFirstNameAndAgeGreaterThanNative("James", 15)
                    .forEach(System.out::println);

            System.out.println(studentRepository.deleteStudentById(3L));

        };

        }*/

}

