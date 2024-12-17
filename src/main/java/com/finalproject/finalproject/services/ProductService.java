package com.finalproject.finalproject.services;

import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.dto.ProductRequest;
import com.finalproject.finalproject.entity.Flights;
import com.finalproject.finalproject.entity.Products;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.FlightStatus;
import com.finalproject.finalproject.repositories.FlightsRepository;
import com.finalproject.finalproject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FlightsRepository flightsRepository;

    public Products addProduct(ProductRequest product,Users user) {
        Products products = new Products();
        products.setUserId(user);
        products.setLength(product.getLength());
        products.setWidth(product.getWidth());
        products.setHeight(product.getHeight());
        ArrayList<Flights> flights = flightsRepository.getFlightsByFlightStatus(FlightStatus.NEW);
        for (Flights flight : flights) {
            Integer productVolume = products.getVolume();
            if(flight.getFreeVolume() + productVolume <= flight.getMaxVolume() &&
            products.getLength() <= flight.getLength() &&
            products.getWidth() <= flight.getWidth() &&
            products.getHeight() <= flight.getHeight()) {
                products.setFlights(flight);
                flight.setFreeVolume(flight.getFreeVolume() + products.getVolume());
                flightsRepository.save(flight);
                break;
            }
        }
        if(products.getFlights() == null){
            HashMap<String,Integer> errors = new HashMap<>();
            errors.put("Нет свободных рейсов!",403);
            throw new SignUpException("Нет свободных рейсов!",errors);
        }
        return productRepository.save(products);
    }
    public List<Products> getAllUserProducts(Users users) {
        List<Products> products = productRepository.getProductsByUserId(users);
        return products;
    }
    public Optional<Products> findProductById(Long id) {
        return productRepository.findById(id);
    }
}
