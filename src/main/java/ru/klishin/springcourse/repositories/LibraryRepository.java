package ru.klishin.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klishin.springcourse.models.Book;

@Repository
public interface LibraryRepository extends JpaRepository<Book, Integer> {
}
