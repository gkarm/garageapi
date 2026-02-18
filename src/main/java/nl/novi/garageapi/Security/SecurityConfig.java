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
                        .requestMatchers("/users").permitAll()

                        .requestMatchers("/users/{id}").authenticated()

                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers("/admedewerkers/**").authenticated()

                        .requestMatchers("/keuringen/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/monteurs").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/monteurs/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/monteurs/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/monteurs/{id}").hasAuthority("ADMIN")
                        .requestMatchers("/onderdelen/**").authenticated()
                        .requestMatchers("/autos/**").authenticated()
                        .requestMatchers("/bomedewerkers/**").authenticated()
                        .requestMatchers("/kassamedewerkers/**").authenticated()
                        .requestMatchers("/klanten/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/upload").permitAll()
                        .requestMatchers(HttpMethod.GET,"/files").authenticated()
                        .requestMatchers("/tekortkomingen/**").authenticated()
                        .requestMatchers("/reparaties/**").authenticated()
                        .requestMatchers("/bons/**").authenticated()
                        .requestMatchers("handelingen/**").authenticated()
                        .requestMatchers("/gebruikte-handelingen").authenticated()
                        .requestMatchers("/gebruiktonderdelen").authenticated()

                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}