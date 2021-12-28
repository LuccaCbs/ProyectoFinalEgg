package egg.FinalProyect.PetStore.services;

import egg.FinalProyect.PetStore.Enums.Pets;
import egg.FinalProyect.PetStore.entities.ProductPackage.Accessory;
import egg.FinalProyect.PetStore.entities.ProductPackage.Product;
import egg.FinalProyect.PetStore.repositories.ProductPackage.ProductRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void save(Product product) {
        
        productRepository.save(product);
    }

}
