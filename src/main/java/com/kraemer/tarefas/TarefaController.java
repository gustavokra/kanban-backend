package com.kraemer.tarefas;

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

@RestController
@RequestMapping(value = "/api/v1/tarefa", produces = MediaType.APPLICATION_JSON_VALUE)
public class TarefaController {

    private final TarefaRepository repo;

    public TarefaController(TarefaRepository repo) {
        this.repo = repo;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa) {

        var saved = repo.save(tarefa);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> obterTodos() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> obter(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping( 
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Tarefa> atualizar(
            @PathVariable Long id,
            @RequestBody Tarefa tarefa) {
        var existe = repo.findById(id);

        if(existe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var tarefaAtual = existe.get();
        tarefaAtual.setNome(tarefa.getNome());

        return ResponseEntity.ok(repo.save(tarefaAtual));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if(!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
