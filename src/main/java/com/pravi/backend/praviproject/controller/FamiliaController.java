package com.pravi.backend.praviproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pravi.backend.praviproject.DTO.FamiliaRequestDTO;
import com.pravi.backend.praviproject.DTO.FamiliaResponseDTO;
import com.pravi.backend.praviproject.service.FamiliaService;

@RestController
@RequestMapping("/familias")
public class FamiliaController {
private final FamiliaService service;

    public FamiliaController(FamiliaService service) {
        this.service = service;
    }

    @PostMapping
    public FamiliaResponseDTO salvar(@RequestBody FamiliaRequestDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<FamiliaResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public FamiliaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
