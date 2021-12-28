package egg.FinalProyect.PetStore.controllers;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import egg.FinalProyect.PetStore.entities.ProductPackage.Product;
import egg.FinalProyect.PetStore.entities.UserPackage.Usuario;
import egg.FinalProyect.PetStore.repositories.ProductPackage.ProductRepository;
import egg.FinalProyect.PetStore.repositories.UserPackage.UsuarioRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class TransactionController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/paymentMethods")
    public String createAndRedirect(@RequestParam(required = false) List<String> products, Model model) throws MPException {

        MercadoPago.SDK.setAccessToken("APP_USR-8370135482658329-120722-a12a96d2297fde3e4358e40f227a6804-1034756692");

        List<Product> itemList = new ArrayList();

        for (String product : products) {
            Product item = productRepository.findById(product).get();
            if (!itemList.contains(item)) {
                item.setProductQuantity(1);
                itemList.add(item);
            } else {
                Integer newPrice = Integer.parseInt(itemList.get(itemList.indexOf(item)).getPrice()) + Integer.parseInt(item.getPrice());

                itemList.get(itemList.indexOf(item)).setProductQuantity(itemList.get(itemList.indexOf(item)).getProductQuantity() + 1);
                itemList.get(itemList.indexOf(item)).setPrice(newPrice.toString());
            }
        }
// Crea un objeto de preferencia
        Preference preference = new Preference();

// Crea un ítem en la preferencia
        ArrayList<Item> items = new ArrayList();
        for (Product product : itemList) {
            Item item = new Item();
            item.setTitle(product.getName())
                    .setQuantity(1)
                    .setUnitPrice((float) Float.parseFloat(product.getPrice()));
            preference.setItems(items);
        }

        BackUrls backUrls = new BackUrls(
                "http://localhost:8080/",
                "http://localhost:8080/",
                "http://localhost:8080/");
//        preference.setAutoReturn(Preference.AutoReturn.approved);
//        preference.getAutoReturn();
        preference.setBackUrls(backUrls);
        System.out.println("proceso save inicia" + new Date());
        preference.save();
        System.out.println("proceso save finaliza" + new Date());
        model.addAttribute("Preference", preference);
        return "paymentMethods";

    }

    @GetMapping("/shoppingCart")
    public String shoppingCart(@RequestParam(required = false) List<String> products, Integer selector, Model model) throws MPException {

        List<Product> itemList = new ArrayList();

        for (String product : products) {
            Product item = productRepository.findById(product).get();
            if (!itemList.contains(item)) {
                item.setProductQuantity(1);
                itemList.add(item);
            } else {
                Integer newPrice = Integer.parseInt(itemList.get(itemList.indexOf(item)).getPrice()) + Integer.parseInt(item.getPrice());

                itemList.get(itemList.indexOf(item)).setProductQuantity(itemList.get(itemList.indexOf(item)).getProductQuantity() + 1);
                itemList.get(itemList.indexOf(item)).setPrice(newPrice.toString());
            }
        }

        Preference preference = new Preference();

//        if (selector == 1) {
            MercadoPago.SDK.setAccessToken("APP_USR-8370135482658329-120722-a12a96d2297fde3e4358e40f227a6804-1034756692");

// Crea un objeto de preferencia
// Crea un ítem en la preferencia
            for (Product product : itemList) {
                Item item = new Item();
                item.setTitle(product.getName())
                        .setQuantity(1)
                        .setUnitPrice((float) Float.parseFloat(product.getPrice()));
                preference.appendItem(item);
            }

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

//        }

        model.addAttribute("Preference", preference);

        model.addAttribute("products", itemList);

        return "shoppingCart";

    }

}
