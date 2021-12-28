package egg.FinalProyect.PetStore.MercadoPago;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import egg.FinalProyect.PetStore.entities.ProductPackage.Product;
import egg.FinalProyect.PetStore.entities.UserPackage.Usuario;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MercadoPagoController {

    //Usuario comprador de prueba, contraseña: qatest5240
    //Usuario comprador de prueba email: test_user_67320610@testuser.com
    @GetMapping("/mercadoPago")
    public String createAndRedirect(Model model) throws MPException {

        MercadoPago.SDK.setAccessToken("APP_USR-8370135482658329-120722-a12a96d2297fde3e4358e40f227a6804-1034756692");

// Crea un objeto de preferencia
        Preference preference = new Preference();

// Crea un ítem en la preferencia
        Item item = new Item();
        item.setTitle("Pc gamer increible")
                .setQuantity(1)
                .setUnitPrice((float) 10258.68);
        preference.appendItem(item);


//        Payer payer = new Payer();
//        payer.setName("Alan");
//        preference.setPayer(payer);
//        
        BackUrls backUrls = new BackUrls(
                "http://localhost:8080/",
                "http://localhost:8080/",
                "http://localhost:8080/");
        preference.setAutoReturn(Preference.AutoReturn.approved);
        preference.getAutoReturn();
        preference.setBackUrls(backUrls);
        System.out.println("proceso save inicia" + new Date());
        preference.save();
        System.out.println("proceso save finaliza" + new Date());
        model.addAttribute("Preference", preference);
        return null;

    }
}
