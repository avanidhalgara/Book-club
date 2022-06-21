package com.example.bookProjectTekSystem.controller;


import com.example.bookProjectTekSystem.dto.ProductDTO;
import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.model.Product;
import com.example.bookProjectTekSystem.service.CategoryService;
import com.example.bookProjectTekSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


//  control all the admin related routes
@Controller
public class adminController {

   public static String uploadDir = System.getProperty("user.dir")+ "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

// control admin routes

//    get the admin routes and return adminHome.html  page
    @GetMapping("/admin")
    public String adminHome(){

        return "adminHome";
    }

// get the /admin/categories route and add attribute using "categories key in thymeleaf template category.html and get the
// all category and return category.html page
    @GetMapping("/admin/categories")
    public String getCategory(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }

//    when user click on add category button then get the admin/categories/add route. passed the empty object new Category()
//    return to the categoriesAdd.html page.
    @GetMapping("/admin/categories/add")
    public String getCategoryAdd(Model model){
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }

//post the rout (adding data) /admin/categories/add  and adding the data in category object
//    call the addCategory method which is in categorySerice to add the data and return the page admin/categories which
//    will show all categories

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

//    get the url admin/categories/delete/id(for example 1) and when user hit the button delete , remove that data by
//    calling the method removeCategoryById from category service method and redirect the page url to the /admin/categories.

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }


    //    get the url admin/categories/update/id(for example 1) and when user hit the button update,  update that data by
//    calling the method updateCategoryById from category service method and redirect the page  to the categoriesAdd.hrml.
    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "categoriesAdd";

        }
        else{
            return "404";
        }

    }
//
//       control product routes
    @GetMapping("/admin/products")
    public String products(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "products";
  }


    @GetMapping("admin/products/add")
    public String productAddGet(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "productsAdd";
    }
    @PostMapping("admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException {

        Product product = new Product();
        Double price=Double.parseDouble(String.valueOf(productDTO.getPrice()));
        product.setId(productDTO.getId());
        System.out.println(productDTO.getName());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(price);
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else{
            imageUUID =imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);


        return "redirect:/admin/products";
    }
    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productService.removeByProductId(id);
        return "redirect:/admin/products";
    }


    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id, Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice((product.getPrice()));
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("productDTO",productDTO );
        return "productsAdd";

    }
}
