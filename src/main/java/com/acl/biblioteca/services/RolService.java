package com.acl.biblioteca.services;

import com.acl.biblioteca.models.Rol;
import com.acl.biblioteca.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Optional<Rol> existRol(Long id){
        return rolRepository.findById(id);
    }
}
