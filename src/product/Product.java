package product;

public class Product {
    private String name;
    private float price;
    private String producer;
    private String productType;
    private NutritionFact nutritionFact;

    public Product (String name, String producer,float price,String productType, NutritionFact nutritionFact) {
        this.name = name;
        this.price = price;
        this.nutritionFact = nutritionFact;
        this.producer = producer;
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public float getPrice() {
        return price;
    }

    public String getProductType() {
        return productType;
    }

    public NutritionFact getNutritionFact() {
        return nutritionFact;
    }

    public String getFormatedPrice() {
        return price + " €";
    }


}
