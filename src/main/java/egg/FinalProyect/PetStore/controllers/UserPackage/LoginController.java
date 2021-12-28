package egg.FinalProyect.PetStore.controllers.UserPackage;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class LoginController {
    
    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
                        @RequestParam(value="logout", required=false)String logout,
                        Model model, Principal principal, RedirectAttributes attribute){
    /*if para mostrar el error de acceso*/    
        if(error!=null){
            model.addAttribute("error", "Wrong user or password!");
            return "redirect:/login";
        }
    /*Muestra el aviso de que ya inició sesión previamente, al buscar el link de login.*/    
        if(principal!=null){
            attribute.addFlashAttribute("warning", "ATTENTION: YOU ALREADY LOG IN!");
            return "redirect:/index";
        }
    /*Muestra el aviso de sesión cerrada.*/    
        if(logout!=null){
            model.addAttribute("success", "SUCCESSFUL LOGOUT!");
        }
        
        return "/newAdmin";
    }
}

