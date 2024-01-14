package com.acl.biblioteca.services;

import com.acl.biblioteca.models.Roles;
import com.acl.biblioteca.models.User;
import com.acl.biblioteca.repository.RolesRepository;
import com.acl.biblioteca.repository.UserRepository;
import com.acl.biblioteca.response.ResponseUser;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public Page<Object[]> allUsersByRol(Pageable pageable) {
        return userRepository.findAllUsersByRol(pageable);
    }

    public User findUser(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isAdmin(User user){
        return user != null && user.getRol().getId_rol() == 1L;
    }

    public ResponseUser registerUser(User user) {
        Optional<Roles> optionalRole = rolesRepository.findById(2L);
        if (optionalRole.isEmpty()) {
            return new ResponseUser(404, "Not Found", "Problema al crear el usuario: Rol no encontrado", null,null, null,"404 Not Found!");
        }
        user.setRol(optionalRole.get());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return new ResponseUser(201, "Created", "OK", user.getEmail(), user.getRol().getNombre_rol(), user.getUsername(), "");
    }

    public ResponseUser authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseUser(401, "Unauthorized", "Credenciales inválidas", null, null, null, "Unauthorized");
        } else if (BCrypt.checkpw(password, user.getPassword())) {
            return new ResponseUser(200, "OK", "OK", user.getEmail(), user.getRol().getNombre_rol(), user.getUsername(), "");
        } else {
            return new ResponseUser(401, "Unauthorized", "Credenciales inválidas", user.getEmail(), null, null,"Unauthorized");
        }
    }
}
