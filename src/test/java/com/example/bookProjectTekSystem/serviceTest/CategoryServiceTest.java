package com.example.bookProjectTekSystem.serviceTest;

import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.model.Book;
import com.example.bookProjectTekSystem.repository.CategoryRepository;
import com.example.bookProjectTekSystem.repository.BookRepository;
import com.example.bookProjectTekSystem.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookRepository bookRepository;


    @Test
    public void getAllCategoryTest() {
        when(categoryRepository.findAll()).thenReturn(Stream.of(new Category("new Book"), new Category("Old book")).collect(Collectors.toList()));
        assertEquals(2, categoryService.getAllCategory().size());
    }

    @Test
    public void getCategoryByIdTest() {
        Optional<Category> category = Optional.ofNullable(new Category("A book"));
        when(categoryRepository.findById(30)).thenReturn(category);
        assertEquals("A book", categoryService.getCategoryById(30).get().getName());
    }
//    @Test
//    public void createCategoryTest()
//    {
//        Category category = new Category(31, "fun book");
//
//
//        categoryService.addCategory(category);
//
//        verify(categoryRepository, times(1)).save(category);
//    }

    @Test
    public void deleteBycategoryTest() {
        Category category = new Category();
        Optional<Book> product = Optional.ofNullable(new Book("A book", category, 4.25, "nice to read", "book.jpg"));
        List<Book> products = new ArrayList<>();
        products.add(product.get());
        willDoNothing().given(categoryRepository).deleteById(1);
        when(bookRepository.findAllByCategory_Id(1)).thenReturn(products);
        willDoNothing().given(bookRepository).deleteAllById(Mockito.anyList());
        categoryService.removeCategoryById(1);
        verify(categoryRepository, times(1)).deleteById(1);
    }
}
