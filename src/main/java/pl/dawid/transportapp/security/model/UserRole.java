package pl.dawid.transportapp.security.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    private String description;

}
