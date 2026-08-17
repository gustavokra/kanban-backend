package com.kraemer.kanban.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kraemer.kanban.Entities.Etapa;
import com.kraemer.kanban.Services.EtapaService;

@RestController
@RequestMapping("api/v1/etapa")
public class EtapaController {

    private EtapaService service;

    public EtapaController(EtapaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Etapa> criar(@RequestBody Etapa etapa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.criar(etapa));
    }

    @GetMapping()
    public ResponseEntity<List<Etapa>> obterTodos() {
        return ResponseEntity.ok(service.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etapa> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etapa> atualizar(
            @PathVariable Long id,
            @RequestBody Etapa etapa) {
        return ResponseEntity.ok(service.atualizar(id, etapa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Etapa> excluir(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
