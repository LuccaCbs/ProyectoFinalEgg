
package egg.FinalProyect.PetStore.entities.ProductPackage;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Food extends Product implements Serializable{
    
    //------------------------------------------------------------
    //Food type: soft food grain, hard food grain, not grain, etc.
    //Product weight: weight in kg.
    //------------------------------------------------------------
    private String productWeight;
    private String foodType;

    public Food() {
    }

    public Food(String productWeight, String foodType, String productType, String name, String brand, String price, String description, String stock, String pet, String petSize, String petAge, Integer productQuality, Integer productQuantity) {
        super(productType, name, brand, price, description, stock, pet, petSize, petAge, productQuality, productQuantity);
        this.productWeight = productWeight;
        this.foodType = foodType;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
    
    
    
}
