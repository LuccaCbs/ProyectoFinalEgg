package egg.FinalProyect.PetStore.controllers;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import egg.FinalProyect.PetStore.controllers.UserPackage.UserController;
import egg.FinalProyect.PetStore.entities.UserPackage.Usuario;
import egg.FinalProyect.PetStore.entities.ProductPackage.Accessory;
import egg.FinalProyect.PetStore.entities.ProductPackage.Food;
import egg.FinalProyect.PetStore.entities.ProductPackage.Product;
import egg.FinalProyect.PetStore.entities.Transactions;
import egg.FinalProyect.PetStore.errors.ServiceError;
import egg.FinalProyect.PetStore.repositories.ProductPackage.ProductRepository;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import egg.FinalProyect.PetStore.repositories.UserPackage.UsuarioRepository;
import egg.FinalProyect.PetStore.services.UsuarioService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String index(Authentication auth, HttpSession session, Model model, @RequestParam(required = false) List<String> productIds) throws MPConfException/*, MPException*/ {

        if (auth != null) {
            String name = auth.getName();

            if (session.getAttribute("usuario") == null) {
                Usuario usuario = usuarioRepository.findByName(name);

                session.setAttribute("usuario", usuario);
            }
        }

        return "index.html";
    }

    @GetMapping("/contactPage")
    public String contactPage(@RequestParam(required = false) List<String> productIds) {
        return "contactPage";
    }
    
    @GetMapping("/staff")
    public String staff (){
        return "staff";
    }

    @GetMapping("/about")
    public String about (){
        return "about";
    }

    
//    @GetMapping("/prueba")
//    public String prueba() {
//        return "prueba";
//    }
//    
//    @PostMapping("/status")
//    public ResponseEntity<Transactions> status(@RequestBody Transactions tr, ModelMap model){
//        
//        if (tr.getUsername().isEmpty()) {
//            return new ResponseEntity<Transactions>(tr, HttpStatus.BAD_REQUEST) ;
//        }else if(tr.getPassword().isEmpty()){
//            return new ResponseEntity<Transactions>(tr, HttpStatus.BAD_REQUEST) ;
//        }else{
//            return new ResponseEntity<Transactions>(tr, HttpStatus.OK) ;
//        }
//    }
}
