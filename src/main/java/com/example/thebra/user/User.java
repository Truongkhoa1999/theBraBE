package com.example.thebra.user;

import com.example.thebra.order.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;


import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity(name = "user")
@Table(name = "customer")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    /*login credentials*/

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String username;

    /*user info*/
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String firstName;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String lastName;

    @Column(nullable = true, columnDefinition = "varchar(10)")
    private String phone;

    @Column(nullable = false, columnDefinition = "text")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@gmail\\.com$", message = "Email address must be a valid Gmail address")
    private String gmail;
    private Role role;



    public enum Role {
        ADMIN, USER
    }

    public User(String password, String username, String firstName, String lastName, String phone, String gmail, Role role) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gmail = gmail;
        this.role = role;
    }

}
