package ru.klishin.springcourse.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 6, max = 150, message = "Name should be between 6 and 150 characters")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+$", message = "Your full name should be in this format: Surname Name Patronymic")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Year of birth should be greater than 1900")
    @Max(value = 2016, message = "Year of birth must be less than 2017")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "person")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;
}
