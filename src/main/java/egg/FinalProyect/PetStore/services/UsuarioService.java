package egg.FinalProyect.PetStore.services;

import egg.FinalProyect.PetStore.entities.UserPackage.Usuario;
import egg.FinalProyect.PetStore.errors.ServiceError;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import egg.FinalProyect.PetStore.repositories.UserPackage.UsuarioRepository;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificationService notificationService;

    /*NEW ADMIN*/
    @Transactional
    public void saveAdminUser(String name, String dni, String phone, String mail, String password1, String password2) throws ServiceError {

        validation(name, dni, phone, mail, password1, password2);

        Usuario admin = new Usuario();

        admin.setName(name);
        admin.setDni(dni);
        admin.setPhone(phone);
        admin.setMail(mail);
        admin.setRol("ROLE_ADMIN");
        admin.setAddress(null);
        admin.setActive(Boolean.TRUE);
        admin.setRegistrationDate(new Date());

        /*encriptamos la clave y luego la seteamos*/
        String encryptedPassword = new BCryptPasswordEncoder().encode(password1);
        admin.setPassword(encryptedPassword);

        if (usuarioRepository.findAll().contains(usuarioRepository.findByMail(mail))) {
            throw new ServiceError("Administrator already registered.");
        } else {
            usuarioRepository.save(admin);
        }

        //notificationService.sendMail("Welcome to Pet Store.", "Pet Store notification.", admin.getMail());
    }

    /*NEW CLIENT*/
    @Transactional
    public void saveClientUser(String name, String dni, String phone, String mail, String address, String password1, String password2) throws ServiceError {

        validation(name, dni, phone, mail, password1, password2);

        Usuario client = new Usuario();

        client.setName(name);
        client.setDni(dni);
        client.setPhone(phone);
        client.setMail(mail);
        client.setRol("ROLE_CLIENT");
        client.setAddress(address);
        client.setActive(Boolean.TRUE);
        client.setRegistrationDate(new Date());

        /*encriptamos la clave y luego la seteamos*/
        String encryptedPassword = new BCryptPasswordEncoder().encode(password1);
        client.setPassword(encryptedPassword);

        if (usuarioRepository.findAll().contains(usuarioRepository.findByMail(mail))) {
            throw new ServiceError("Client already registered.");
        } else {
            usuarioRepository.save(client);
        }

        //notificationService.sendMail("Welcome to Pet Store.", "Pet Store notification.", admin.getMail());
    }

    /*Edit Admin*/
    @Transactional
    public void editAdmin(String id, String phone, String mail, String password1, String password2) throws ServiceError {

        Usuario admin = usuarioRepository.getById(id);

        validation(admin.getName(), admin.getDni(), phone, mail, password1, password2);

        admin.setPhone(phone);
        admin.setMail(mail);        

        String encryptedPassword = new BCryptPasswordEncoder().encode(password1);
        admin.setPassword(encryptedPassword);

        usuarioRepository.save(admin);

//        notificationService.sendMail("The changes were processed.", "Pet Store notification.", admin.getMail());
    }
    
    @Transactional
    public void editClient(String id, String phone, String mail, String address, String password1, String password2) throws ServiceError {

        Usuario client = usuarioRepository.getById(id);

        validation(client.getName(), client.getDni(), phone, mail, password1, password2);

        client.setPhone(phone);
        client.setMail(mail); 
        client.setAddress(address);

        String encryptedPassword = new BCryptPasswordEncoder().encode(password1);
        client.setPassword(encryptedPassword);

        usuarioRepository.save(client);

//        notificationService.sendMail("The changes were processed.", "Pet Store notification.", admin.getMail());
    }
    
    /*Find by Id*/
    @Transactional
    public Optional<Usuario> findAdminById(String id) {
        return usuarioRepository.findById(id);
    }

    /*Admin List*/
    
//    public List<Usuario> adminList() {
//        
//        List<Usuario> adminList = usuarioRepository.findAll();
//        int x = adminList.size();
//        
//        for (int i = 0; i < x; i++) {
//           Usuario usuario = usuarioRepository.findByRole("admin");
//           adminList.add(usuario); 
//        }
//
//        return adminList;
//    }

    /*Validations*/
    private void validation(String name, String dni, String phone, String mail, String password1, String password2) throws ServiceError {

        Pattern pString = Pattern.compile("^([A-Za-z]+[ ]*){1,3}$");
        Pattern pNum = Pattern.compile("^[0-9]{8}$");
        Pattern pNumPhone = Pattern.compile("^[0-9]{8,10}$");
        Pattern pMail = Pattern.compile("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");

        Matcher mName = pString.matcher(name);
        Matcher mDni = pNum.matcher(dni);
        Matcher mPhone = pNumPhone.matcher(phone);
        Matcher mMail = pMail.matcher(mail);

        if (name == null || name.isEmpty()) {
            throw new ServiceError("Name not entered");
        } else if (!mName.matches()) {
            throw new ServiceError("The name must contain only letters.");
        }

        if (dni == null || dni.isEmpty()) {
            throw new ServiceError("Dni not entered.");
        } else if (!mDni.matches()) {
            throw new ServiceError("The DNI must contain 8 digits.");
        }

        if (phone == null || phone.isEmpty()) {
            throw new ServiceError("Phone not entered.");
        } else if (!mPhone.matches()) {
            throw new ServiceError("The phone must contain between 8 and 10 digits.");
        }

        if (password1 == null || password1.isEmpty()) {
            throw new ServiceError("Passwords not entered.");
        } else if (password1.equals(password2)) {
        } else {
            throw new ServiceError("The passwords are different.");
        }

        if (mail == null || mail.isEmpty()) {
            throw new ServiceError("Mail not entered.");
        } else if (!mMail.matches()) {
            throw new ServiceError("The mail is wrong.");
        }
    }
    
    private void checkPassword(String password){
        
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByMail(mail);

        if (usuario != null && usuario.getRol().equals("ROLE_ADMIN")) {
            
            List<GrantedAuthority> authorities = new ArrayList<>();
            /*Lista de permisos*/

            GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_ADMIN");
            authorities.add(auth);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            
            User user = new User(usuario.getName(), usuario.getPassword(), authorities);
            
            session.setAttribute("usuario", usuario);
            return user;
            
        } else if(usuario != null && usuario.getRol().equals("ROLE_CLIENT")) {

            List<GrantedAuthority> authorities = new ArrayList<>();
            /*Lista de permisos*/

            GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_CLIENT");
            authorities.add(auth);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            
            User user = new User(usuario.getName(), usuario.getPassword(), authorities);

            session.setAttribute("usuario", usuario);
            return user;
            
        } else {
            return null;
        }      
    }

}
