package com.ecostyle.EcoStyle.dto;

public class ProductoDTO {
    private Long id;
    private String name;
    private Long price;
    private String description;

    public ProductoDTO() {
        this.name = "";
        this.price = 0L;
        this.description = "";
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

