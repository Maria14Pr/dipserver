package com.irij.buyerdesign.model;

public class GoodModel {

    private Integer id;
    private String name;
    private Integer min_count;
    private Integer countProduct;
    private Integer priceProduct;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private Float calories;
    private Integer farmerId;
    //private String createdAt;
    //private String updatedAt;
    private UnitForGoodModel unit;
    private MediaModel [] media;
    private CategoryModel [] category;
    private FarmerNameForGoodModel farmer;

    public GoodModel (int id, String name, int min_count, int countProduct, int priceProduct,
                      float proteins, float fats, float carbohydrates, float calories,
                      int farmerId,
                      //String createdAt, String updatedAt,
                      UnitForGoodModel unit,
                      MediaModel [] media, CategoryModel [] category, FarmerNameForGoodModel farmer
                      ) {
        this.id = id;
        this.name = name;
        this.min_count = min_count;
        this.countProduct = countProduct;
        this.priceProduct = priceProduct;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.farmerId = farmerId;
        //this.createdAt = createdAt;
        //this.updatedAt = updatedAt;
        this.unit = unit;
        this.media = media;
        this.category = category;
        this.farmer = farmer;

    }

    public int getGoodId () {
        return id;
    }

    public String getGoodName () {
        return name;
    }

    public int getGoodMinCount () {
        return min_count;
    }

    public int getGoodPrice () {
        return priceProduct;
    }

    public String getUnitName (){
        return unit.getUnitNameForGood();
    }

    public String getGoodPhoto () {
        return media[0].getMediaUrl();
    }

    public String getGoodFarmerName () {
        return farmer.getFarmerNameForGood();
    }

    public int getCountOfGood () {
        return countProduct;
    }

    public float getProteins () {
        return proteins;
    }

    public float getFats () {
        return fats;
    }

    public float getCarbohydrates () {
        return carbohydrates;
    }

    public float getCalories(){
        return calories;
    }

    public CategoryModel getCategory(){
        return category[0];
    }

    public int getFarmerOfGoodId (){
        return farmerId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMin_count(Integer min_count) {
        this.min_count = min_count;
    }

    public void setCountProduct(Integer countProduct) {
        this.countProduct = countProduct;
    }

    public void setPriceProduct(Integer priceProduct) {
        this.priceProduct = priceProduct;
    }

    public void setProteins(Float proteins) {
        this.proteins = proteins;
    }

    public void setFats(Float fats) {
        this.fats = fats;
    }

    public void setCarbohydrates(Float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public void setUnit(UnitForGoodModel unit) {
        this.unit = unit;
    }

    public void setMedia(MediaModel[] media) {
        this.media = media;
    }

    public void setCategory(CategoryModel[] category) {
        this.category = category;
    }

    public void setFarmer(FarmerNameForGoodModel farmer) {
        this.farmer = farmer;
    }
}
