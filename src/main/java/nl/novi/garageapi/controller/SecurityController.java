package nl.novi.garageapi.controller;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @GetMapping("/hello")
    public String sayHeilo(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return "Hello " + userDetails.getUsername();
        } else {
            return "Hello stranger";
        }

    }

    @GetMapping("/secret")
    public String tellSecret(){
        return "Secret.....";
    }

}