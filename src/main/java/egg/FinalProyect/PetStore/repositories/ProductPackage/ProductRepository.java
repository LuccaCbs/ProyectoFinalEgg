
package egg.FinalProyect.PetStore.repositories.ProductPackage;

import egg.FinalProyect.PetStore.entities.ProductPackage.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
}
