
package egg.FinalProyect.PetStore.repositories.UserPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import egg.FinalProyect.PetStore.entities.UserPackage.Usuario;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
    
    @Query("SELECT c FROM AppUser c WHERE c.dni = :dni")
    public Usuario findByDni(@Param("dni")String dni);
    
    @Query("SELECT c FROM AppUser c WHERE c.mail = :mail")
    public Usuario findByMail(@Param("mail")String mail);
    
    @Query("SELECT c FROM AppUser c WHERE c.name = :name")
    public Usuario findByName(@Param("name")String name);
//    @Query("SELECT c FROM AppUser c WHERE c.rol = :rol")
//    public Usuario findByRole(@Param("rol")String rol);
}

