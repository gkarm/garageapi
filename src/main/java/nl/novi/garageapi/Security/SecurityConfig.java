package nl.novi.garageapi.Security;

import nl.novi.garageapi.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admedewerkers").authenticated()
                        .requestMatchers(HttpMethod.GET, "/admedewerkers/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/keuringen/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/keuringen").authenticated()
                        .requestMatchers(HttpMethod.GET, "/keuringen/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/keuringen/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/monteurs").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/monteurs/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/onderdelen").authenticated()
                        .requestMatchers(HttpMethod.GET, "/onderdelen/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/onderdelen/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/autos/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/autos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/bomedwerkers/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/bomedewerkers").authenticated()
                        .requestMatchers(HttpMethod.GET, "/kassamedewerkers/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/kassamedewerkers").authenticated()
                        .requestMatchers(HttpMethod.GET, "/klanten/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/klanten").authenticated()
                        .requestMatchers(HttpMethod.POST,"/upload").permitAll()
                        .requestMatchers(HttpMethod.GET,"/files").authenticated()
                        .requestMatchers(HttpMethod.POST, "/tekortkomingen").authenticated()
                        .requestMatchers(HttpMethod.GET, "/tekortkomingen/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/tekortkomingen/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/tekortkomingen/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/reparaties").authenticated()
                        .requestMatchers(HttpMethod.GET, "/reparaties/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/bons").authenticated()
                        .requestMatchers(HttpMethod.GET, "/bons/{id}").authenticated()

                        .requestMatchers(HttpMethod.POST, "/gebruikte-handelingen").authenticated()
                        .requestMatchers(HttpMethod.POST, "/gebruiktonderdelen").authenticated()

                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}