package Controller;

/**
 * Created by Kalaman on 16.01.18.
 */

public class NutritionFact {
    private int calories;
    private float carbohydrates;
    private float totalFat;

    public NutritionFact(int calories, float carbohydrates , float totalFat) {
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.totalFat = totalFat;
    }

    public int getCalories() {
        return calories;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public float getTotalFat() {
        return totalFat;
    }

    public String getFormatedCalories() {
        return calories + " kcal";
    }

    public String getFormatedCarbohydrates() {
        return carbohydrates + " g";
    }

    public String getFormatedTotalFat() {
        return totalFat + " g";
    }
}