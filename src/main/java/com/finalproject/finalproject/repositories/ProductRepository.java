package com.finalproject.finalproject.repositories;

import com.finalproject.finalproject.entity.Products;
import com.finalproject.finalproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    List<Products> getProductsByUserId(Users userId);
}
