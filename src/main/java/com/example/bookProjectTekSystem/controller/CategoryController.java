package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    // get the /admin/categories route and add attribute using "categories key in thymeleaf template category.html and get the
// all category and return category.html page
    @GetMapping("/categories")
    public String getCategory(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    // When user click on add category button then get the admin/categories/add route. passed the empty object new Category()
//    return to the categoriesAdd.html page.
    @GetMapping("/categories/add")
    public String getCategoryAdd(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

//post the rout (adding data) /admin/categories/add  and adding the data in category object
//    call the addCategory method which is in categorySerice to add the data and return the page admin/categories which
//    will show all categories

    @PostMapping("/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

//    get the url admin/categories/delete/id(for example 1) and when user hit the button delete , remove that data by
//    calling the method removeCategoryById from category service method and redirect the page url to the /admin/categories.

    @GetMapping("/categories/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/categories";
    }


    //    get the url admin/categories/update/id(for example 1) and when user hit the button update,  update that data by
//    calling the method updateCategoryById from category service method and redirect the page  to the categoriesAdd.hrml.
    @GetMapping("/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";

        } else {
            return "404";
        }

    }
}
