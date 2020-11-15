package com.irij.buyerdesign.model;

public class CategoryModel {

    private Integer id;
    private String name;
    private Integer parentId;
    private String createdAt;
    private String updatedAt;

    public CategoryModel(int id,
                         String name
    ) {

        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getCategoryId(){
        return id;
    }
    public String getCategoryName(){
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



}
