package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.dto.BookDTO;
import com.example.bookProjectTekSystem.model.Book;
import com.example.bookProjectTekSystem.service.CategoryService;
import com.example.bookProjectTekSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class BookController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/bookImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    BookService bookService;

    //Control product routes
    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAllBook());
        return "books";
    }

    @GetMapping("/books/add")
    public String getBooks(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "booksAdd";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute("bookDTO") BookDTO bookDTO,
                          @RequestParam("bookImage") MultipartFile file,
                          @RequestParam("imgName") String imgName) throws IOException {

        Book book = new Book();
        Double price = Double.parseDouble(String.valueOf(bookDTO.getPrice()));
        book.setId(bookDTO.getId());
//        System.out.println(productDTO.getName());
        book.setName(bookDTO.getName());
        book.setCategory(categoryService.getCategoryById(bookDTO.getCategoryId()).get());
        book.setPrice(price);
        book.setDescription(bookDTO.getDescription());
        String imageView;
//        if its false
        if (!file.isEmpty()) {
//            getOriginalFileName return the original file name and store it in imageUUID
            imageView = file.getOriginalFilename();
//            using Paths method take two parameters . one where you want to save(which path) the file and
//            which file you want to store.
            Path fileNameAndPath = Paths.get(uploadDirectory, imageView);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageView = imgName;
        }
        book.setImageName(imageView);
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.removeByBookId(id);
        return "redirect:/books";
    }

    @GetMapping("/book/update/{id}")
    public String updateBook(@PathVariable long id, Model model) {
        Book product = bookService.getBookById(id).get();
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(product.getId());
        bookDTO.setName(product.getName());
        bookDTO.setCategoryId(product.getCategory().getId());
        bookDTO.setPrice((product.getPrice()));
        bookDTO.setDescription(product.getDescription());
        bookDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("bookDTO", bookDTO);
        return "booksAdd";

    }
}