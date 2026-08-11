package com.kraemer.tarefas.Controllers;

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

import com.kraemer.tarefas.Entities.Quadro;
import com.kraemer.tarefas.Repository.QuadroRepository;

@RestController
@RequestMapping(value = "/api/v1/quadro", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuadroController {

    private QuadroRepository repo;

    public QuadroController(QuadroRepository repo) {
        this.repo = repo;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quadro> criar(@RequestBody Quadro quadro) {
        var salvado = repo.save(quadro);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvado);
    }

    @GetMapping
    public ResponseEntity<List<Quadro>> obterTodos() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quadro> obterPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quadro> atualizar(
            @PathVariable Long id,
            @RequestBody Quadro quadro) {
        var quadroAtualizar = repo.findById(id);

        if (quadroAtualizar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var quadroAtual = quadroAtualizar.get();
        quadroAtual.setNome(quadro.getNome());

        return ResponseEntity.ok(repo.save(quadroAtual));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Quadro> deletar(@PathVariable Long id) {
        var quadroRemover = repo.findById(id);
        if (quadroRemover.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repo.delete(quadroRemover.get());
        return ResponseEntity.noContent().build();
    }
}
