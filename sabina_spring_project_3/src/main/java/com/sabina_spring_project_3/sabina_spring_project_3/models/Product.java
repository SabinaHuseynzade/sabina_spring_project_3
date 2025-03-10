package com.sabina_spring_project_3.sabina_spring_project_3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String imagePath;

    @Column(name = "sales_per_day", nullable = false)
    private int salesPerDay;

    @Column(name = "sales_per_month", nullable = false)
    private int salesPerMonth;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private int ratedCount;

    @Column(nullable = false)
    private int sales;

    @Column(nullable = false)
    private double revenue;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double sale;

    @Column(nullable = false)
    private boolean isNew;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String speed;

    @Column(name = "ram", nullable = false)
    private int RAM;

    @Column(nullable = false)
    private String processor;

    @Column(name = "rom", nullable = false)
    private int ROM;

    @Column(name = "last_update")
    private LocalDate lastUpdate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Product() {
    }

    public Product(Long id, String name, String category, String imagePath, int salesPerDay, int salesPerMonth, double rating, int ratedCount, int sales, double revenue, double price, double sale, boolean isNew, String model, String speed, int RAM, String processor, int ROM, LocalDate lastUpdate, User user) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imagePath = imagePath;
        this.salesPerDay = salesPerDay;
        this.salesPerMonth = salesPerMonth;
        this.rating = rating;
        this.ratedCount = ratedCount;
        this.sales = sales;
        this.revenue = revenue;
        this.price = price;
        this.sale = sale;
        this.isNew = isNew;
        this.model = model;
        this.speed = speed;
        this.RAM = RAM;
        this.processor = processor;
        this.ROM = ROM;
        this.lastUpdate = lastUpdate;
        this.user = user;
    }

    public Product(String name, String category, double price, User user) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getSalesPerDay() {
        return salesPerDay;
    }

    public void setSalesPerDay(int salesPerDay) {
        this.salesPerDay = salesPerDay;
    }

    public int getSalesPerMonth() {
        return salesPerMonth;
    }

    public void setSalesPerMonth(int salesPerMonth) {
        this.salesPerMonth = salesPerMonth;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatedCount() {
        return ratedCount;
    }

    public void setRatedCount(int ratedCount) {
        this.ratedCount = ratedCount;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getRAM() {
        return RAM;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getROM() {
        return ROM;
    }

    public void setROM(int ROM) {
        this.ROM = ROM;
    }
}