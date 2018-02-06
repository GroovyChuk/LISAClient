package product;

public class Product {
    private String name;
    private float price;
    private String producer;
    private String productType;
    private NutritionFact nutritionFact;
    private String sGTIN;
    private int weight;

    public Product (String name, String producer, float price, String productType, NutritionFact nutritionFact, String sGTIN, int weight) {
        this.name = name;
        this.price = price;
        this.nutritionFact = nutritionFact;
        this.producer = producer;
        this.productType = productType;
        this.weight = weight;
        this.sGTIN = sGTIN;
    }

    public int getWeight() { return weight; }

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

    public String getsGTIN() { return sGTIN; }
}
