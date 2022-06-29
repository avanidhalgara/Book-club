package com.example.bookProjectTekSystem.serviceTest;

import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.model.Book;
import com.example.bookProjectTekSystem.repository.BookRepository;
import com.example.bookProjectTekSystem.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {


    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;


    /* To test getallproduct  */
    @Test
    public void getAllBookTest() {
        Category category = new Category();
        category.setId(1);
        when(bookRepository.findAll()).thenReturn(Stream.of(new Book("Brainquest", category, 3.25, "good to read", "brainquest.png")).collect(Collectors.toList()));
        assertEquals(1, bookService.getAllBook().size());
        assertEquals(category.getId(), bookService.getAllBook().get(0).getCategory().getId());
        assertEquals("Brainquest", bookService.getAllBook().get(0).getName());
        assertEquals(3.25f, bookService.getAllBook().get(0).getPrice());
        assertEquals("good to read", bookService.getAllBook().get(0).getDescription());
        assertEquals("brainquest.png", bookService.getAllBook().get(0).getImageName());
    }

    @Test
    public void getBookByIdTest() {
        Category category = new Category();
        Optional<Book> product = Optional.ofNullable(new Book("A book", category, 4.25, "nice to read", "book.jpg"));
        when(bookRepository.findById(3l)).thenReturn(product);
        assertEquals("A book", bookService.getBookById(3).get().getName());
    }

    @Test
    public void getAllBooksByCategoryIdTest() {
        Category category = new Category();
        category.setId(1);
        when(bookRepository.findAll()).thenReturn(Stream.of(new Book("Brainquest", category, 3.25, "good to read", "brainquest.png")).collect(Collectors.toList()));
        assertEquals(1, bookService.getAllBook().get(0).getCategory().getId());

    }

    @Test
    public void removeBookByIdTest() {
        willDoNothing().given(bookRepository).deleteById(4l);

        bookService.removeByBookId(4);
        verify(bookRepository, times(1)).deleteById(4l);

    }
}
