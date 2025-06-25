package nl.novi.garageapi.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.novi.garageapi.enumeration.UserRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;


    @Column(name = "user_role")
    private List<UserRole> UserRole;


    public User(String username, String password, List<UserRole> userRole) {
        this.username = username;
        this.password = password;

        this.UserRole = userRole;

    }

    public User() {
    }

}
