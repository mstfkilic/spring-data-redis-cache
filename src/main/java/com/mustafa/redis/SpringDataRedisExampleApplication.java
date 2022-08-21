package com.mustafa.redis;

import com.mustafa.redis.entities.Product;
import com.mustafa.redis.repositories.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class SpringDataRedisExampleApplication {

    @Autowired
    private ProductDao productDao;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productDao.save(product);
    }

    @GetMapping
    public List<Product> findAll() {
        return productDao.findAll();

    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product")
    public Product findProduct(@PathVariable int id) {
        return productDao.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id) {
        return productDao.deleteProduct(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisExampleApplication.class, args);
    }

}
