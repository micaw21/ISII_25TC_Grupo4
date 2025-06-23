package com.stanreybackend.stanreyapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stanreybackend.stanreyapi.DTO.PersonaDTO;
import com.stanreybackend.stanreyapi.entity.Persona;
import com.stanreybackend.stanreyapi.repository.PersonaRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public String addPersona(PersonaDTO personaDTO) {
        Persona persona = new Persona(
                personaDTO.getDni(),
                personaDTO.getNombre(),
                personaDTO.getApellido(),
                personaDTO.getEmail());
        personaRepository.save(persona);

        return persona.getDni().toString();
    };

    public List<Persona> findAll() {

        List<Persona> personas = new ArrayList<Persona>();
        personas = personaRepository.findAll();

        return personas;
    }
}
