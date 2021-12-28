
package egg.FinalProyect.PetStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jdbc.repository.query.Query;

import egg.FinalProyect.PetStore.entities.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String> {

}
