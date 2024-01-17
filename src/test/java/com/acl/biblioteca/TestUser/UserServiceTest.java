package com.acl.biblioteca.TestUser;

import com.acl.biblioteca.controllers.UserController;
import com.acl.biblioteca.models.Rol;
import com.acl.biblioteca.models.User;
import com.acl.biblioteca.response.ResponseUser;
import com.acl.biblioteca.services.RolService;
import com.acl.biblioteca.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UserController userController;

      @Test
      void testRegisterUser() {
          Optional<Rol> optionalRole = rolService.existRol(2L);

          User user = new User();
          user.setEmail("test@example.com");
          user.setUsername("testUser");
          user.setPassword("password123");
          optionalRole.ifPresent(user::setRol);

          ResponseUser responseUser = userService.registerUser(user);

          assertEquals(201, responseUser.getStatus());
          assertEquals("Created", responseUser.getStatusText());
      }

      @Test
      void testRegisterEndpoint() {
          User user = new User();
          user.setEmail("test1@example.com");
          user.setUsername("testUser");
          user.setPassword("password123");

          ResponseEntity<?> responseEntity = userController.register(user);

          assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
      }

}