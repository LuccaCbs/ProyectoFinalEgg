package egg.FinalProyect.PetStore.controllers;

import egg.FinalProyect.PetStore.entities.Transactions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class AjaxLoginController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String performLogin(@RequestBody Transactions tr,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            request.login(tr.getUsername(), tr.getPassword());
            return "{\"status\": true}";
        } catch (Exception e) {
            return "{\"status\": false, \"error\": \"Incorrect E-mail or Password\"}";
        }
    }
}
