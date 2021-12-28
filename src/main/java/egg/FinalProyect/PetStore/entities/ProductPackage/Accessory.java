
package egg.FinalProyect.PetStore.entities.ProductPackage;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Accessory extends Product implements Serializable{
    
    //---------------------------------------------------
    //Accessory accessoryType: Toy, necklace, strap, etc.
    //Accessory size: s, m, l, xl, xxl.
    //---------------------------------------------------
    private String accessoryType;
    private String size;

    public Accessory() {
    }

    public Accessory(String accessoryType, String size, String productType, String name, String brand, String price, String description, String stock, String pet, String petSize, String petAge, Integer productQuality, Integer productQuantity) {
        super(productType, name, brand, price, description, stock, pet, petSize, petAge, productQuality, productQuantity);
        this.accessoryType = accessoryType;
        this.size = size;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    
}
