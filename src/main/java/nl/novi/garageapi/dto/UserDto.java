package nl.novi.garageapi.dto;



import lombok.Getter;
import lombok.Setter;
import nl.novi.garageapi.enumeration.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter

public class UserDto {

    public String username;

    public String password;

    private List<UserRole> userRole;





}