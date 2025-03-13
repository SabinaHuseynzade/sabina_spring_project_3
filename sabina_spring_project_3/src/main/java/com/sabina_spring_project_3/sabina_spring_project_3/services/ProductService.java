package com.sabina_spring_project_3.sabina_spring_project_3.services;

import com.sabina_spring_project_3.sabina_spring_project_3.models.Product;
import com.sabina_spring_project_3.sabina_spring_project_3.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private final String uploadDir = "C:\\Users\\ASUS\\Documents\\GitHub\\sabina_spring_project_3\\sabina_spring_project_3\\uploads/";

    //добавляет новый продукт
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {

        //проверяет и создает папку
        File uploadDirectory = new File(uploadDir);



        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }


        //если нет фото оставляет старое
        if (imageFile == null || imageFile.isEmpty()) {
            Product oldProduct = productRepository.findById(product.getId()).get();
            product.setImagePath(oldProduct.getImagePath());
        } else {

            String originalFilename = imageFile.getOriginalFilename();//генерирует уникальное имя файла
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

            File imageFilePath = new File(uploadDirectory, uniqueFilename);//сохраняет фото в папку


            imageFile.transferTo(imageFilePath);


            product.setImagePath(uniqueFilename);//устанавливает путь к папке

        }




        return productRepository.save(product);
    }


    // получает товары по id
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    // получает страницу продуктов и возвращает ее содержимое
    public List<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.getContent();
    }

    // получает продукт по id
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // находит общее колчисество продаж
    public int getTotalSales() {
        List<Product> products = productRepository.findAll();
        return products.stream().mapToInt(Product::getSales).sum();
    }

    //находит общее количество дохода
    public double getTotalRevenue() {
        List<Product> products = productRepository.findAll();
        return products.stream().mapToDouble(Product::getRevenue).sum();
    }

    //находит продукт по id
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    //удаляет продукт по id
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    //находит продукт по категории
    public List<Product> getProductsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategory(category);
    }


}
