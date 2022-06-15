package com.example.bookProjectTekSystem.serviceTest;

import com.example.bookProjectTekSystem.model.Category;
import com.example.bookProjectTekSystem.model.Product;
import com.example.bookProjectTekSystem.repository.ProductRepository;
import com.example.bookProjectTekSystem.service.ProductService;
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
public class ProductServiceTest {


    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;



    /* To test getallproduct  */
    @Test
    public void getAllProductTest() {
        Category category= new Category();
        category.setId(1);
        when (productRepository.findAll()).thenReturn(Stream.of(new Product("Brainquest", category,3.25f,"good to read","brainquest.png")).collect(Collectors.toList()));
        assertEquals(1,productService.getAllProduct().size());
        assertEquals(category.getId(),productService.getAllProduct().get(0).getCategory().getId());
        assertEquals("Brainquest",productService.getAllProduct().get(0).getName());
        assertEquals(3.25f,productService.getAllProduct().get(0).getPrice());
        assertEquals("good to read",productService.getAllProduct().get(0).getDescription());
        assertEquals("brainquest.png",productService.getAllProduct().get(0).getImageName());
    }
    @Test
    public void getProductByIdTest(){
        Category category = new Category();
        Optional<Product> product = Optional.ofNullable(new Product( "A book",category,4.25f,"nice to read","book.jpg"));
        when(productRepository.findById(3l)).thenReturn(product);
        assertEquals("A book", productService.getProductById(3).get().getName());
    }
    @Test
    public void getAllProductByCategoryId(){
        Category category= new Category();
        category.setId(1);
        when (productRepository.findAll()).thenReturn(Stream.of(new Product("Brainquest", category,3.25f,"good to read","brainquest.png")).collect(Collectors.toList()));
        assertEquals(1,productService.getAllProduct().get(0).getCategory().getId());

    }

    @Test
    public void removeProductByIdTest(){
        willDoNothing().given(productRepository).deleteById(4l);

        productService.removeByProductId(4);
        verify(productRepository,times(1)).deleteById(4l);

    }
}
