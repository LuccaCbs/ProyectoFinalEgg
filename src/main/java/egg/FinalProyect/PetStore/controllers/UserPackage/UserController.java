package egg.FinalProyect.PetStore.controllers.UserPackage;

import egg.FinalProyect.PetStore.entities.UserPackage.Usuario;
import egg.FinalProyect.PetStore.errors.ServiceError;
import egg.FinalProyect.PetStore.repositories.UserPackage.UsuarioRepository;
import egg.FinalProyect.PetStore.services.UsuarioService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*LOGIN*/
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model, Principal principal, RedirectAttributes attribute) {
        /*if para mostrar el error de acceso*/
        if (error != null) {
            model.addAttribute("error", "Wrong user or password!");
            return "redirect:/login.html";
        }
        /*Muestra el aviso de que ya inició sesión previamente, al buscar el link de login.*/
        if (principal != null) {
            attribute.addFlashAttribute("warning", "ATTENTION: YOU ALREADY LOG IN!");
            return "redirect:/index.html";
        }
        /*Muestra el aviso de sesión cerrada.*/
        if (logout != null) {
            model.addAttribute("success", "SUCCESSFUL LOGOUT!");
        }
        return "login.html";
    }

    //ADMIN
    /*New AdminUser*/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/newAdmin")
    public String saveAdmGet() {
        return "/newAdmin.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/saveAdmin")
    public ResponseEntity<Object> saveAdminPost(@RequestBody Usuario user, ModelMap model) {
        
        try {
            System.out.println("Trying");
                usuarioService.saveClientUser(user.getName(), user.getDni(), user.getPhone(), user.getMail(), user.getAddress(), user.getPassword(), user.getPassword());
            } catch (ServiceError ex) {
                model.put("error", ex.getMessage());
                model.put("name", user.getName());
                model.put("dni", user.getDni());
                model.put("phone", user.getPhone());
                model.put("mail", user.getMail());

                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<Object>(ex, HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<Object>(user, HttpStatus.OK) ;
    }

    @PostMapping("/saveClient")
    public ResponseEntity<Object> saveClientPost(@RequestBody Usuario user, ModelMap model) {
        
        try {
            System.out.println("Trying");
                usuarioService.saveClientUser(user.getName(), user.getDni(), user.getPhone(), user.getMail(), user.getAddress(), user.getPassword(), user.getPassword());
            } catch (ServiceError ex) {
                model.put("error", ex.getMessage());
                model.put("name", user.getName());
                model.put("dni", user.getDni());
                model.put("phone", user.getPhone());
                model.put("mail", user.getMail());
                model.put("address", user.getAddress());

                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<Object>(ex, HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<Object>(user, HttpStatus.OK) ;
    }
    /*New ClientUser*/

//        @PostMapping("/saveClient")
//        public String saveClientPost(ModelMap model, @RequestParam String name, @RequestParam String dni, @RequestParam String phone, @RequestParam String mail, @RequestParam String address, @RequestParam String password1, @RequestParam String password2){
//
//            try {
//                usuarioService.saveClientUser(name, dni, phone, mail, address, password1, password2);
//            } catch (ServiceError ex) {
//                model.put("error", ex.getMessage());
//                model.put("name", name);
//                model.put("dni", dni);
//                model.put("phone", phone);
//                model.put("mail", mail);
//                model.put("address", address);
//
//                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
//                return "/newClient"; 
//            }
//            return "redirect:" + saveClientGet();
//        }
    /*Edit Admin*/
//        @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/editUser")
    public String editUser(Model model, @RequestParam(required = false) String id) {

        Usuario user = usuarioService.findAdminById(id).get();
        model.addAttribute("user", user);

        return "editUser";
    }

    /*Only can modify phone, mail & password*/

    //         @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modifyAdmin/{id}/{name}/{dni}/{phone}/{mail}/{password1}/{password2}")
    public String modifyAdmin(ModelMap model, @RequestParam String id, @RequestParam String name, @RequestParam String dni, @RequestParam String phone, @RequestParam String mail, @RequestParam String password1, @RequestParam String password2) {

        try {

            Usuario admin = usuarioRepository.getById(id);

            usuarioService.editAdmin(id, phone, mail, password1, password2);

            model.put("name", admin.getName());
            model.put("dni", admin.getDni());
            model.put("phone", admin.getPhone());
            model.put("mail", admin.getMail());

        } catch (ServiceError ex) {

            model.put("error", ex.getMessage());
            model.put("phone", phone);
            model.put("mail", mail);

            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect: /editAdmin";
        }
        return "/index";
    }

    //Admin List/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/adminList")
    public String adminList(Model model) {
        List<Usuario> admins = usuarioRepository.findAll();
        List<Usuario> newList = new ArrayList();
        for (Usuario admin : admins) {
            if (admin.getRol().equals("ROLE_ADMIN")) {
                newList.add(admin);
            }
        }
        model.addAttribute("tittle", "ADMINISTRATOR LIST");
        model.addAttribute("admins", newList);
        return "/adminList";
    }

    //Client List/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/clientList")
    public String clientList(Model model) {
        List<Usuario> clients = usuarioRepository.findAll();
        List<Usuario> newList = new ArrayList();
        for (Usuario client : clients) {
            if (client.getRol().equals("ROLE_CLIENT")) {
                newList.add(client);
            }
        }
        model.addAttribute("tittle", "CLIENT LIST");
        model.addAttribute("clients", newList);
        return "/clientList";
    }
}
