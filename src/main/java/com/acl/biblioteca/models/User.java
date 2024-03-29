package com.acl.biblioteca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username no puede estar vacio!")
    private String username;
    @Column(unique = true)
    @NotBlank(message = "Email no puede estar vacio!")
    @Email(message = "El email no es valido!")
    private String email;
    @NotBlank(message = "Por favor, ingresa un password")
    @Size(min = 8, max = 64, message = "El password debe contener entre 8 y 20 caracteres")
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    private Rol rol;
}
