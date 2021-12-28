package egg.FinalProyect.PetStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Async
    /*Asincrono: hace que el método funcione sin cortar la ejecución del programa. 
    Es decir que no debe esperar a que se envíe el mail para continuar.*/
    public void sendMail (String body, String title, String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("petstoret1@gmail.com");
        message.setSubject(title);
        message.setText(body); 
        
        
        mailSender.send(message);
                
    }
    
}
