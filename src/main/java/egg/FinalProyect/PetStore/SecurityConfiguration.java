package egg.FinalProyect.PetStore;

import egg.FinalProyect.PetStore.services.UsuarioService;
import egg.FinalProyect.PetStore.utilities.LoginSuccessMessage;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfiguration extends WebSecurityConfigurerAdapter { /*Esta extensión nos permite configurar las peticiones http*/

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private LoginSuccessMessage successMessage;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(usuarioService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override  /*Este método se sobreescribe desde: 'boton derecho-insert code-override methods' */ 
    protected void configure(HttpSecurity http) throws Exception {
        
        /*Primero ponemos al home o index como público, es decir que cualquier tipo de usuario podría visitarlo, junto con las carpetas necesarias para las vistas, como CSS, IMAGES y JS*/
        http.headers()
        .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self' 'unsafe-inline' *", "frame-ancestors 'self' *.mercadolibre.com")).frameOptions().sameOrigin().and()
                .authorizeRequests()
                        .antMatchers("/", "/home" , "/index", "/resources/**")
                        .permitAll()  // "/resources/**" : los dos asteriscos luego de la barra indican que está permitido todo lo contenido en esa carpeta.
//                        .anyRequest().authenticated()
                .and().formLogin()
                        .successHandler(successMessage)
                        .loginPage("/")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")                    
                        .permitAll() /*Establecemos el mapa del método "login" del controller correspondiente, para que nos redireccione a una vista creada por nosotros y no la de Spring*/
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                .and().csrf().disable().cors();
                
    }

}

//http.headers()
//        .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self' 'unsafe-inline' *", "frame-ancestors 'self' *.mercadolibre.com"))
//                .frameOptions().sameOrigin().and()
//                .authorizeRequests()
//                        .antMatchers(HttpMethod.POST, "/saveClient").permitAll()
//                        .antMatchers("/", "/resources/**")
//                        .permitAll()  // "/resources/**" : los dos asteriscos luego de la barra indican que está permitido todo lo contenido en esa carpeta.
//                        .anyRequest().permitAll()
//                .and().formLogin()
//                        .successHandler(successMessage)
//                        .loginPage("/login")
//                        .loginProcessingUrl("/logincheck")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/")                    
//                        .permitAll() /*Establecemos el mapa del método "login" del controller correspondiente, para que nos redireccione a una vista creada por nosotros y no la de Spring*/
//                .and().logout()
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .permitAll()
//                .and().csrf().disable().cors();
                


//    @Autowired
//    private DataSource dataSource;
//    
//    @Autowired
//    private BCryptPasswordEncoder passEncoder;
//
//    @Autowired
//    private LoginSuccessMessage successMessage;
//    
//    @Override  /*Este método se sobreescribe desde: 'boton derecho-insert code-override methods' */ 
//    protected void configure(HttpSecurity http) throws Exception {
//        
//        /*Primero ponemos al home o index como público, es decir que cualquier tipo de usuario podría visitarlo, junto con las carpetas necesarias para las vistas, como CSS, IMAGES y JS*/
//        http.authorizeRequests()
//                .antMatchers("/", "/home" , "/index", "/resources/**").permitAll()  // "/static.images/**" : los dos asteriscos luego de la barra indican que está permitido todo lo contenido en esa carpeta.
//      
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                    .successHandler(successMessage)
//                    .loginPage("/login").permitAll() /*Establecemos el mapa del método "login" del controller correspondiente, para que nos redireccione a una vista creada por nosotros y no la de Spring*/
//                .and()
//                .logout().permitAll();
//                
//    }
//
//    @Autowired
//    public void configurerSecurityGlobal(AuthenticationManagerBuilder builder) throws Exception{
//        builder.jdbcAuthentication()
//                .dataSource(dataSource)         /*Se le coloca el nombre con el que fue inyectado*/
//                .passwordEncoder(passEncoder)   /*Se le coloca el nombre con el que fue inyectado*/
//                .usersByUsernameQuery("SELECT username, password, enable FROM users WHERE username=?")                                      /*Esta Query trae los datos del usuario en base al username del formulario de login*/
//                .authoritiesByUsernameQuery("SELECT u.username, r.rol FROM roles r INNER JOIN users u ON r.user_id=u.id WHERE username=?"); /*Esta Query trae al user con su respectivo rol(es la unión de ambas tablas.)*/
//    }