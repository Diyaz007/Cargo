package com.finalproject.finalproject.controllers;

import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.dto.ProductRequest;
import com.finalproject.finalproject.entity.Flights;
import com.finalproject.finalproject.entity.Products;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.services.FlightsService;
import com.finalproject.finalproject.services.ProductService;
import com.finalproject.finalproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/api/v1/product")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            Map<String,Integer> errors = new HashMap<>();
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users == null) {
                errors.put("not found",404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
            }
            if(users.getRole() != Roles.USER && users.getRole() != Roles.ADMIN) {
                errors.put("access denied",403);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
            }
            Products products = new Products();
            products.setUserId(users);
            products.setWeight(productRequest.getWeight());
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(products));
        }catch (SignUpException e){
            HashMap<String,Integer> errors = (HashMap<String, Integer>) e.getErrors();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        Optional<Products> products = productService.findProductById(id);
        if (products.isPresent()) {
            return ResponseEntity.ok(products.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
