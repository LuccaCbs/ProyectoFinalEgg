package egg.FinalProyect.PetStore.entities.UserPackage;

import egg.FinalProyect.PetStore.entities.Transactions;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity 
public class Usuario implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String dni;
    private String phone;
    private String address;
    private String mail;
    private String password;
    private String rol;
    private Boolean active;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;  

    @ManyToOne
    private Transactions transaction;
    
    
    //Constructors

    public Usuario() {
    }

    public Usuario(String id, String name, String dni, String phone, String mail, String password, String rol, String adress, Boolean active, Date registrationDate, Transactions transaction) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.rol = rol;
        this.address = adress;
        this.active = active;
        this.registrationDate = registrationDate;
        this.transaction = transaction;
    }

    //Getter & Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }
    
    
}
