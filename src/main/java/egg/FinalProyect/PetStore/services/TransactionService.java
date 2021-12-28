
package egg.FinalProyect.PetStore.services;

import egg.FinalProyect.PetStore.repositories.ProductPackage.AccessoryRepository;
import egg.FinalProyect.PetStore.repositories.ProductPackage.FoodRepository;
import egg.FinalProyect.PetStore.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FoodRepository foodRepository;
    
    @Autowired
    private AccessoryRepository accessoryRepository;
}
