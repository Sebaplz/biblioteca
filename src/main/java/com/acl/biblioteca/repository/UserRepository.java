package com.acl.biblioteca.repository;

import com.acl.biblioteca.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query("SELECT new map(u.id as id, u.username as username, u.email as email) FROM User u WHERE u.rol.id_rol = 2")
    Page<Object[]> findAllUsersByRol(Pageable pageable);

}
