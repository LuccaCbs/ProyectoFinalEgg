package egg.FinalProyect.PetStore.services;

//Entity import
import egg.FinalProyect.PetStore.entities.ProductPackage.Accessory;

//Services import
import egg.FinalProyect.PetStore.errors.ServiceError;

//Repositories import
import egg.FinalProyect.PetStore.repositories.ProductPackage.AccessoryRepository;
import egg.FinalProyect.PetStore.repositories.ProductPackage.ProductRepository;

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
public class AccessoryService {

    //---------------------------------------------------
    //Autowired classes
    //---------------------------------------------------
    @Autowired
    private AccessoryRepository accessoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

    //---------------------------------------------------
    public void AccessoryService() {

    }

    //---------------------------------------------------
    //Transactional methods-----DESABILITADOS
    //---------------------------------------------------
    @Transactional
    public void save(Accessory accessory) throws ServiceError {
//        validation(accessory);
        
        productRepository.save(accessory);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Accessory> answer = accessoryRepository.findById(id);
        if (answer.isPresent()) {
            accessoryRepository.delete(answer.get());
        }
    }

    //---------------------------------------------------
    //Search methods
    //---------------------------------------------------
    public Accessory showOne(String id) {
        return accessoryRepository.findById(id).get();
    }

    public List<Accessory> listAll() {

        return accessoryRepository.findAll();
    }

    public List<Accessory> listByName(String name) {
        return accessoryRepository.findAllByName(name);
    }

    
    public List<Accessory> listByPet(String pet) {
        return accessoryRepository.findAllByPet(pet);
    }

    public List<Accessory> listByBrand(String brand) {
        return accessoryRepository.findAllByBrand(brand);
    }

    //---------------------------------------------------
    //Validation method
    //---------------------------------------------------
    
    public void validation(String accessoryType, String size, String name, String brand, String price, String description, String stock, String pet, String petSize, String petAge) throws ServiceError {
        Pattern pString = Pattern.compile("^([A-Za-z]+[ ]*){1,3}$");
        Pattern pNum = Pattern.compile("[0-9]{1,5}(\\.[0-9]*)?");

        Matcher mName = pString.matcher(name);
        Matcher mAccessoryType = pString.matcher(accessoryType);
        Matcher mBrand = pString.matcher(brand);
        Matcher mStock = pNum.matcher(stock);
        Matcher mPrice = pNum.matcher(price);
        //--------------------------------------------------------------------------
        //Matcher validations
        //--------------------------------------------------------------------------
        if (name == null || name.isEmpty()) {
            throw new ServiceError("Name not entered");
        } else if (!mName.matches()) {
            throw new ServiceError("The name must contain only letters.");
        }
        
        if (accessoryType == null || accessoryType.isEmpty()) {
            throw new ServiceError("Food type not entered");
        } else if (!mAccessoryType.matches()) {
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

        //--------------------------------------------------------------------------
        //Radio button validations
        //--------------------------------------------------------------------------
        if (size == null || size.isEmpty() ) {
            throw new ServiceError("You must select an accessory size.");
        }

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
            description = "No description available";
        }
        //--------------------------------------------------------------------------
    }

}
