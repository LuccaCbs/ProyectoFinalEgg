
package egg.FinalProyect.PetStore.repositories.ProductPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jdbc.repository.query.Query;

import egg.FinalProyect.PetStore.entities.ProductPackage.Accessory;
import java.util.List;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, String>{

    //--------------------------------
    //Single-Target Query
    //--------------------------------
    
    
    //--------------------------------
    //List-Target Query
    //--------------------------------
    @Query("SELECT a FROM Accessory a WHERE a.name like :name")
    public List<Accessory> findAllByName(@Param("name") String name);
    
    @Query("SELECT a FROM Accessory a WHERE a.brand like :brand")
    public List<Accessory> findAllByBrand(@Param("brand")String brand);
    
    @Query("SELECT a FROM Accessory a WHERE a.pet like :pet")
    public List<Accessory> findAllByPet(@Param("pet")String pet);

}
