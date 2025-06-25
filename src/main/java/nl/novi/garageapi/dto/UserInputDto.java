package nl.novi.garageapi.dto;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInputDto {

    @NotNull
    @Size(min=1, max=60)
    private String username;
    @NotNull
    @Size(min=1, max=60)
    private String password;


    @Getter

    private List<String> role;

    public UserInputDto(String username, String password, List<String> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public UserInputDto(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public UserInputDto() {
    }


}
