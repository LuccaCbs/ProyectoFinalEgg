
package egg.FinalProyect.PetStore.repositories.ProductPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jdbc.repository.query.Query;

import egg.FinalProyect.PetStore.entities.ProductPackage.Food;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, String>{

    
    //--------------------------------
    //Single-Target Query
    //--------------------------------
    
    
    //--------------------------------
    //List-Target Query
    //--------------------------------
    @Query("SELECT f FROM Food f WHERE f.foodType like :foodType")
    public List<Food> findAllByFoodType(@Param("foodType") String foodType);
    
    @Query("SELECT f FROM Food f WHERE f.brand like :brand")
    public List<Food> findAllByBrand(@Param("brand")String brand);
    
    @Query("SELECT f FROM Food f WHERE f.pet like :pet")
    public List<Food> findAllByPet(@Param("pet")String pet);
}
