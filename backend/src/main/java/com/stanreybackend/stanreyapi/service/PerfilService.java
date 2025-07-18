package com.stanreybackend.stanreyapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stanreybackend.stanreyapi.DTO.PerfilDTO;
import com.stanreybackend.stanreyapi.entity.Perfil;
import com.stanreybackend.stanreyapi.repository.PerfilRepository;

import jakarta.transaction.Transactional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Transactional
    public String addPerfil(PerfilDTO perfilDTO) {

        Perfil perfil = new Perfil(
                perfilDTO.getIdPerfil(),
                perfilDTO.getDescripcion());

        perfilRepository.save(perfil);

        return perfil.getIdPerfil().toString();
    };

    public List<Perfil> findAll() {

        List<Perfil> perfiles = new ArrayList<Perfil>();
        perfiles = perfilRepository.findAll();

        return perfiles;
    }
}
