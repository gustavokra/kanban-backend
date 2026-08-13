package com.kraemer.kanban.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kraemer.kanban.Entities.Quadro;
import com.kraemer.kanban.Service.QuadroService;

@RestController
@RequestMapping(value = "/api/v1/quadro", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuadroController {

    private QuadroService service;

    public QuadroController(QuadroService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quadro> criar(@RequestBody Quadro quadro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(quadro));
    }

    @GetMapping
    public ResponseEntity<List<Quadro>> obterTodos() {
        return ResponseEntity.ok(service.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quadro> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quadro> atualizar(
            @PathVariable Long id,
            @RequestBody Quadro quadro) {
        return ResponseEntity.ok(service.atualizar(id, quadro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Quadro> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
