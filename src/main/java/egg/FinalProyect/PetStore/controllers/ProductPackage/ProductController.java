package egg.FinalProyect.PetStore.controllers.ProductPackage;

//Entity import
import egg.FinalProyect.PetStore.entities.ProductPackage.Accessory;
import egg.FinalProyect.PetStore.entities.ProductPackage.Food;
import egg.FinalProyect.PetStore.entities.ProductPackage.Product;

//Services import
import egg.FinalProyect.PetStore.services.AccessoryService;
import egg.FinalProyect.PetStore.services.FoodService;
import egg.FinalProyect.PetStore.services.ProductService;
import egg.FinalProyect.PetStore.errors.ServiceError;

//Repositories import
import egg.FinalProyect.PetStore.repositories.ProductPackage.ProductRepository;

//Java import
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//Spring import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class ProductController {

    //---------------------------------------------------
    //Autowired classes
    //---------------------------------------------------
    @Autowired
    private ProductService productService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/allProducts")
    public String allProducts(Model model,@RequestParam(required = false) List<String> productIds){
        if (!productRepository.findAll().isEmpty()) {
            List<Product> products = productRepository.findAll();

            products.sort(Product.orderByBrand);
            List<String> brands = new ArrayList();
            List<String> petTypes = new ArrayList();
            int foods = 0;
            int accessories = 0;
            int maxPrice = 0;

            for (Product product : products) {

                if (product.getProductType().equals("food")) {
                    foods++;
                } else {
                    accessories++;
                }

                if (!brands.contains(product.getBrand())) {
                    brands.add(product.getBrand());
                }

                if (!petTypes.contains(product.getPet())) {
                    petTypes.add(product.getPet());
                }
                int productPrice = Integer.parseInt(product.getPrice());
                if (productPrice > maxPrice) {
                    maxPrice = productPrice;
                }

            }

            List<String> petSizes = new ArrayList();
            petSizes.add("small");
            petSizes.add("medium");
            petSizes.add("big");

            List<String> petAges = new ArrayList();
            petAges.add("puppy");
            petAges.add("young");
            petAges.add("adult");

            model.addAttribute("brands", brands);
            model.addAttribute("petTypes", petTypes);
            model.addAttribute("petSizes", petSizes);
            model.addAttribute("petAges", petAges);
            model.addAttribute("foods", foods);
            model.addAttribute("accessories", accessories);
            model.addAttribute("maxPrice", maxPrice);
        }

        return "allProducts";
    }


    @GetMapping("/productForm")
    public String productForm(@RequestParam(required = false) String selector, Model model) {
        
        model.addAttribute("selector", selector);

        return "productForm";
    }

    //---------------------------------------------------------------------
    //Product creation: Determinated by the var "productType" it will create a product that is either a "food" type or an accessory" using whatever is nesesary for them
    //---------------------------------------------------------------------
    @PostMapping("/createProduct")
    public String createProduct(Model model, @RequestParam(required = false) String productType, @RequestParam(required = false) String brand, @RequestParam(required = false) String price, @RequestParam(required = false) MultipartFile image, @RequestParam(required = false) String description, @RequestParam(required = false) String stock, @RequestParam(required = false) String petType, @RequestParam(required = false) String name, @RequestParam(required = false) String productWeight, @RequestParam(required = false) String petSize, @RequestParam(required = false) String petAge, @RequestParam(required = false) String foodType,  @RequestParam(required = false) String accessoryType, @RequestParam(required = false) Integer productQuality) throws ServiceError {

        try {
            Integer productQuantity = 0;
            if (productType.equals("food")) {
                try {

                    foodService.validation(productWeight, foodType, name, brand, price, description, stock, petType, petSize, petAge);

                    Food food = new Food(productWeight, foodType, productType, name, brand, price, description, stock, petType, petSize, petAge,productQuality, productQuantity);

                    if (!image.isEmpty()) {

                        String absolutePath = "C://PetStore//resources//products//food";

                        try {
                            byte[] byteImg = image.getBytes();
                            Path fullPath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
                            Files.write(fullPath, byteImg);

                            food.setImage(image.getOriginalFilename());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        food.setImage("No image available");
                    }

                    productService.save(food);

                } catch (ServiceError e) {
                    model.addAttribute("error", e.getMessage());
                    model.addAttribute("productType", productType);
                    model.addAttribute("productWeight", productWeight);
                    model.addAttribute("foodType", foodType);
                    model.addAttribute("accessoryType", accessoryType);
                    model.addAttribute("name", name);
                    model.addAttribute("brand", brand);
                    model.addAttribute("price", price);
                    model.addAttribute("description", description);
                    model.addAttribute("stock", stock);
                    model.addAttribute("petType", petType);
                    model.addAttribute("petSize", petSize);
                    model.addAttribute("petAge", petAge);
                    model.addAttribute("productQuality", productQuality);

                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, e);
                    return "productForm";
                } catch (Exception e) {

                    model.addAttribute("error", e.getMessage());
                    model.addAttribute("productType", productType);
                    model.addAttribute("productWeight", productWeight);
                    model.addAttribute("foodType", foodType);
                    model.addAttribute("accessoryType", accessoryType);
                    model.addAttribute("name", name);
                    model.addAttribute("brand", brand);
                    model.addAttribute("price", price);
                    model.addAttribute("description", description);
                    model.addAttribute("stock", stock);
                    model.addAttribute("petType", petType);
                    model.addAttribute("petSize", petSize);
                    model.addAttribute("petAge", petAge);
                    model.addAttribute("productQuality", productQuality);
                    return "productForm";
                }
            } else if (productType.equals("accessory")) {

                try {
                    accessoryService.validation(accessoryType, petSize, name, brand, price, description, stock, petType, petSize, petAge);

                    Accessory accessory = new Accessory(accessoryType, petSize, productType, name, brand, price, description, stock, petType, petSize, petAge, productQuality, productQuantity);

                    if (!image.isEmpty()) {

                        String absolutePath = "C://PetStore//resources//products//accessory";

                        try {
                            byte[] byteImg = image.getBytes();
                            Path fullPath = Paths.get(absolutePath + "//" + image.getOriginalFilename());
                            Files.write(fullPath, byteImg);

                            accessory.setImage(image.getOriginalFilename());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        accessory.setImage("No image available");
                    }

                    productService.save(accessory);
                } catch (ServiceError e) {

                    model.addAttribute("error", e.getMessage());
                    model.addAttribute("productType", productType);
                    model.addAttribute("productWeight", productWeight);
                    model.addAttribute("foodType", foodType);
                    model.addAttribute("accessoryType", accessoryType);
                    model.addAttribute("name", name);
                    model.addAttribute("brand", brand);
                    model.addAttribute("price", price);
                    model.addAttribute("description", description);
                    model.addAttribute("stock", stock);
                    model.addAttribute("petType", petType);
                    model.addAttribute("petSize", petSize);
                    model.addAttribute("petAge", petAge);
                    model.addAttribute("productQuality", productQuality);

                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, e);
                    return "productForm";
                } catch (Exception e) {

                    model.addAttribute("error", e.getMessage());
                    model.addAttribute("productType", productType);
                    model.addAttribute("productWeight", productWeight);
                    model.addAttribute("foodType", foodType);
                    model.addAttribute("accessoryType", accessoryType);
                    model.addAttribute("name", name);
                    model.addAttribute("brand", brand);
                    model.addAttribute("price", price);
                    model.addAttribute("description", description);
                    model.addAttribute("stock", stock);
                    model.addAttribute("petType", petType);
                    model.addAttribute("petSize", petSize);
                    model.addAttribute("petAge", petAge);
                    model.addAttribute("productQuality", productQuality);
                    return "productForm";
                }

            }
        } catch (Exception e) {
            model.addAttribute("error", "A product type must be selected");
            model.addAttribute("productType", productType);
            model.addAttribute("productWeight", productWeight);
            model.addAttribute("foodType", foodType);
            model.addAttribute("accessoryType", accessoryType);
            model.addAttribute("name", name);
            model.addAttribute("brand", brand);
            model.addAttribute("price", price);
            model.addAttribute("description", description);
            model.addAttribute("stock", stock);
            model.addAttribute("petType", petType);
            model.addAttribute("petSize", petSize);
            model.addAttribute("petAge", petAge);
            model.addAttribute("productQuality", productQuality);
            return "productForm";
        }

        return "productForm";
    }
    
    @GetMapping("/modalProduct")
    public String modalProduct(String productId, Model model) {

        System.out.println(productId);
        Product product = productRepository.findById(productId).get();

        model.addAttribute("brand", product.getBrand());
        model.addAttribute("name", product.getName());
        model.addAttribute("price", product.getPrice());
        model.addAttribute("product.productQuality", product.getProductQuality());
        model.addAttribute("productType", product.getProductType());
        model.addAttribute("image", product.getImage());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("petType", product.getPet());
        model.addAttribute("petAge", product.getPetAge());
        model.addAttribute("petSize", product.getPetSize());

        return "modalProduct";

    }

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productRepository.findAll();

        products.sort(Product.orderByBrand);

        model.addAttribute("products", products);

        return "products";

    }
    
    @GetMapping("/listFilter")
    public String listFilter(@RequestParam(required = false) List<String> brands, @RequestParam(required = false) List<String> petTypes, @RequestParam(required = false) List<String> petSizes, @RequestParam(required = false) List<String> petAges, @RequestParam(required = false) String all, @RequestParam(required = false) Integer price, @RequestParam(required = false) String productType, Model model) {

        List<Product> finalList = productRepository.findAll();

        finalList.sort(Product.orderByBrand);

        if (all.isEmpty()) {
            if (!brands.isEmpty()) {

                finalList = brands(brands, finalList);

            }

            if (!petTypes.isEmpty()) {

                finalList = petTypes(petTypes, finalList);
            }

            if (!petSizes.isEmpty()) {

                finalList = petSizes(petSizes, finalList);
            }

            if (!petAges.isEmpty()) {

                finalList = petAges(petAges, finalList);
            }

            if (price > 0) {
                
                finalList = prices(price, finalList);
            }
            
            if (!productType.isEmpty()) {
                
                finalList = productTypes(productType, finalList);
            }
        }

        model.addAttribute("products", finalList);

        return "products";
    }

    public List<Product> brands(List<String> brands, List<Product> finalList) {

        List<Product> newList = new ArrayList();

        for (String brand : brands) {
            for (Product product : finalList) {
                if (product.getBrand().equals(brand)) {
                    newList.add(product);
                }
            }
        }

        return newList;
    }

    public List<Product> petTypes(List<String> petTypes, List<Product> finalList) {
        List<Product> newList = new ArrayList();

        for (String petType : petTypes) {
            for (Product product : finalList) {
                if (product.getPet().equals(petType)) {
                    newList.add(product);
                }
            }
        }

        return newList;
    }

    public List<Product> petSizes(List<String> petSizes, List<Product> finalList) {
        List<Product> newList = new ArrayList();

        for (String petSize : petSizes) {
            for (Product product : finalList) {
                if (product.getPetSize().equals(petSize)) {
                    newList.add(product);
                }
            }
        }

        return newList;
    }

    public List<Product> petAges(List<String> petAges, List<Product> finalList) {
        List<Product> newList = new ArrayList();

        for (String petAge : petAges) {
            for (Product product : finalList) {
                if (product.getPetAge().equals(petAge)) {
                    newList.add(product);
                }
            }
        }

        return newList;
    }

    public List<Product> prices(Integer price, List<Product> finalList) {
        List<Product> newList = new ArrayList();

        for (Product product : finalList) {
            int productPrice = Integer.parseInt(product.getPrice());
            if (productPrice <= price) {
                newList.add(product);
            }
        }

        return newList;
    }
    
    public List<Product> productTypes(String productType, List<Product> finalList) {
        List<Product> newList = new ArrayList();

        for (Product product : finalList) {
            if (product.getProductType().equals(productType)) {
                newList.add(product);
            }
        }

        return newList;
    }
    
    @GetMapping("/carousel")
    public String carousel(Model model, String petType) {

        List<Product> list = productRepository.findAll();
        list.sort(Product.orderByBrand);

        List<Product> newList = new ArrayList();

        for (Product product : list) {
            if (product.getPet().equals(petType)) {
                newList.add(product);
            }
        }

        model.addAttribute("elements", newList);

        return "carouselProduct";
    }

}
