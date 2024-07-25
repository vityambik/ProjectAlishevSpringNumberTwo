package ru.klishin.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klishin.springcourse.models.Book;
import ru.klishin.springcourse.models.Person;
import ru.klishin.springcourse.repositories.LibraryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LibraryService {

    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, String sortByYear) {
        if (page != null && booksPerPage != null && "true".equals(sortByYear)) {

            return libraryRepository.findAll(PageRequest.of(page, booksPerPage,
                    Sort.by("yearOfPublishing"))).getContent();

        }
        if (page == null && booksPerPage == null && "true".equals(sortByYear)) {

            return libraryRepository.findAll(Sort.by("yearOfPublishing"));

        }
        if (page != null && booksPerPage != null && !"true".equals(sortByYear)) {

            return libraryRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();

        }

        return libraryRepository.findAll();
    }

    public Book findById(int id) {
        Optional<Book> foundBook = libraryRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        libraryRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        libraryRepository.save(updatedBook);
    }

    @Transactional
    public void appointBook(Person person, Book updatedBook) {
        updatedBook.setPerson(person);
        libraryRepository.save(updatedBook);
    }

    @Transactional
    public void freeingBook(Book updatedBook) {
        updatedBook.setPerson(null);
        libraryRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        libraryRepository.deleteById(id);
    }
}
