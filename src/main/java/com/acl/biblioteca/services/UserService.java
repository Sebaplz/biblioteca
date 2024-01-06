package com.acl.biblioteca.services;

import com.acl.biblioteca.Response;
import com.acl.biblioteca.models.Roles;
import com.acl.biblioteca.models.User;
import com.acl.biblioteca.repository.RolesRepository;
import com.acl.biblioteca.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public Response registerUser(User user) {
        //id: 1 ADMIN   2 USER
        Optional<Roles> optionalRole = rolesRepository.findById(2L);
        if (optionalRole.isEmpty()) {
            return new Response(404, "Not Found", "Problema al crear el usuario: Rol no encontrado");
        }
        user.setRol(optionalRole.get());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return new Response(201, "Created", user.getRol().getNombre_rol());
    }

    public Response authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new Response(401, "Unauthorized", "Credenciales inválidas");
        } else if (BCrypt.checkpw(password, user.getPassword())) {
            return new Response(200, "OK", user.getRol().getNombre_rol());
        } else {
            return new Response(401, "Unauthorized", "Credenciales inválidas");
        }
    }
}
