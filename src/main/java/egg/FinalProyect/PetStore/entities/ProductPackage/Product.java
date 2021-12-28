package egg.FinalProyect.PetStore.entities.ProductPackage;

import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

@Entity
public abstract class Product implements Serializable {

    //------------------------------------------------------------
    //Product Id: Database universal unic identificator.
    //Brand: Product brand to filter by.
    //Price: Product price to filter by.
    //Image: URL to search the image from the resources of the proyect.
    //Description: Brief description of the product.
    //Stock: Available amount of the product.
    //Pet: Pet type determinated by a radio button.
    //Pet Size: Pet syze determinated by a radio button.(small, medium, large)
    //Pet Age: Pet Age determinated by a radio button.(puppy, young, adult, Elderly).
    //------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String productType;
    private String name;
    private String brand;
    private String price;
    private String image;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description", nullable = false, columnDefinition = "Text")
    private String description;
    private String stock;
    private String pet;
    private String petSize;
    private String petAge;
    private Integer productQuality;
    private Integer productQuantity;

    public Product() {
    }

    public Product(String productType, String name, String brand, String price, String description, String stock, String pet, String petSize, String petAge, Integer productQuality, Integer productQuantity) {
        this.productType = productType;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.pet = pet;
        this.petSize = petSize;
        this.petAge = petAge;
        this.productQuality = productQuality;
        this.productQuantity = productQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getPetSize() {
        return petSize;
    }

    public void setPetSize(String petSize) {
        this.petSize = petSize;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public Integer getProductQuality() {
        return productQuality;
    }

    public void setProductQuality(Integer productQuality) {
        this.productQuality = productQuality;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
    
    
    public static Comparator<Product> orderByName = new Comparator<Product>() {
        @Override
        public int compare(Product p, Product p1) {
            return p1.getName().compareTo(p.getName());
        }
    ;
    };
    
    public static Comparator<Product> orderByBrand = new Comparator<Product>() {
        @Override
        public int compare(Product p, Product p1) {
            return p.getBrand().compareTo(p1.getBrand());
        }
    ;
    };

    public static Comparator<Product> orderByPrice = new Comparator<Product>() {
        @Override
        public int compare(Product p, Product p1) {
            return p1.getPrice().compareTo(p.getPrice());
        }
    ;
    };
            public static Comparator<Product> orderByPetType = new Comparator<Product>() {
        @Override
        public int compare(Product p, Product p1) {
            return p1.getPet().compareTo(p.getPet());
        }
    ;
};
}
