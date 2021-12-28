package egg.FinalProyect.PetStore.services;

//Entity import
import egg.FinalProyect.PetStore.entities.ProductPackage.Food;

//Services import
import egg.FinalProyect.PetStore.errors.ServiceError;

//Repositories import
import egg.FinalProyect.PetStore.repositories.ProductPackage.FoodRepository;

//Java import
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;

//Spring import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    //---------------------------------------------------
    //Autowired classes
    //---------------------------------------------------
    @Autowired
    private FoodRepository foodRepository;

    //---------------------------------------------------
    public void FoodService() {

    }

    //---------------------------------------------------
    //Transactional methods
    //---------------------------------------------------

    @Transactional
    public void deleteById(String id) {
        Optional<Food> answer = foodRepository.findById(id);
        if (answer.isPresent()) {
            foodRepository.delete(answer.get());
        }
    }
    //---------------------------------------------------
    //Search methods
    //---------------------------------------------------

    public Food showOne(String id) {
        return foodRepository.findById(id).get();
    }

    public List<Food> listAll() {

        return foodRepository.findAll();
    }

    public List<Food> listByName(String foodType) {
        return foodRepository.findAllByFoodType(foodType);
    }

    public List<Food> listByPet(String pet) {
        return foodRepository.findAllByPet(pet);
    }

    public List<Food> listByBrand(String brand) {
        return foodRepository.findAllByBrand(brand);
    }

    //---------------------------------------------------
    //Validation method
    //---------------------------------------------------
    public void validation(String productWeight, String foodType, String name, String brand, String price, String description, String stock, String pet, String petSize, String petAge) throws ServiceError {
        System.out.println("error por validar");
        
        Pattern pString = Pattern.compile("^([A-Za-z]+[ ]*){1,3}$");
        Pattern pNum = Pattern.compile("[0-9]{1,5}(\\.[0-9]*)?");

        Matcher mName = pString.matcher(name);
        Matcher mFoodType = pString.matcher(foodType);
        Matcher mBrand = pString.matcher(brand);
        Matcher mStock = pNum.matcher(stock);
        Matcher mPrice = pNum.matcher(price);
        Matcher mProductWeight = pNum.matcher(productWeight);
        

        //--------------------------------------------------------------------------
        //Matcher validations
        //--------------------------------------------------------------------------
        if (name == null || name.isEmpty()) {
            throw new ServiceError("Name not entered");
        } else if (!mName.matches()) {
            throw new ServiceError("The name must contain only letters.");
        }
        
        
        if (foodType == null || foodType.isEmpty()) {
            throw new ServiceError("Food type not entered");
        } else if (!mFoodType.matches()) {
            throw new ServiceError("The food type must contain only letters.");
        }

        if (brand == null || brand.isEmpty()) {
            throw new ServiceError("Brand not entered");
        } else if (!mBrand.matches()) {
            throw new ServiceError("The brand must contain only letters.");
        }

        if (price == null || price.isEmpty()) {
            throw new ServiceError("Price not entered");
        } else if (price.equals("0")) {
            throw new ServiceError("The price cannot be zero");
        } else if (!mPrice.matches()) {
            throw new ServiceError("The price must contain only numbers.");
        }

        if (stock == null || stock.isEmpty()) {
            throw new ServiceError("The stock must be filled.");
        } else if (!mStock.matches()) {
            throw new ServiceError("The stock must contain only numbers.");
        }

        if (productWeight == null || productWeight.isEmpty()) {
            throw new ServiceError("Product weight not entered");
//        } else if (Double.parseDouble(productWeight) <= 0) {
//            throw new ServiceError("The product weight cannot be lower than 0.");
        } else if ( !mProductWeight.matches()) {
            throw new ServiceError("The product weight must contain only numbers.");
        }

        //--------------------------------------------------------------------------
        //Radio button validations
        //--------------------------------------------------------------------------
        if (pet == null || pet.isEmpty()) {
            throw new ServiceError("You must select a pet type.");
        }

        if (petSize == null || petSize.isEmpty()) {
            throw new ServiceError("You must select a pet size.");
        }

        if (petAge == null || petAge.isEmpty()) {
            throw new ServiceError("You must select a pet age.");
        }

        //--------------------------------------------------------------------------
        //Auto-completed validations
        //--------------------------------------------------------------------------
        if (description.isEmpty()) {
            description ="No description available";
        }
        //--------------------------------------------------------------------------
    }
    
}
