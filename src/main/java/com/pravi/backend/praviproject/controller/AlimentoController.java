package com.pravi.backend.praviproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pravi.backend.praviproject.DTO.AlimentoRequestDTO;
import com.pravi.backend.praviproject.DTO.AlimentoResponseDTO;
import com.pravi.backend.praviproject.service.AlimentoService;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {
private final AlimentoService service;

    public AlimentoController(AlimentoService service) {
        this.service = service;
    }

    @PostMapping
    public AlimentoResponseDTO salvar(@RequestBody AlimentoRequestDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<AlimentoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AlimentoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
