package ru.klishin.springcourse.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person person;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 100, message = "Title should be between 1 and 100 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, max = 100, message = "Author should be between 1 and 100 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 1457, message = "Year of publishing should be greater than 1457")
    @Max(value = 2024, message = "Year of publishing must be less than 2025")
    @Column(name = "year_of_publishing")
    private int yearOfPublishing;

    @Column(name = "appoint_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointDate;

    public boolean checkForOverdue() {

        int check = (int) ((new Date().getTime() - appointDate.getTime()) / (24 * 60 * 60 * 1000));

        return check < 10;
    }
}
