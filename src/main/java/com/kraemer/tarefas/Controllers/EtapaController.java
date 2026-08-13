package com.kraemer.tarefas.Controllers;

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

import com.kraemer.tarefas.Entities.Etapa;
import com.kraemer.tarefas.Repository.EtapaRepository;

@RestController
@RequestMapping("api/v1/etapa")
public class EtapaController {

    private EtapaRepository repo;

    public EtapaController(EtapaRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Etapa> criar(@RequestBody Etapa etapa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                repo.save(etapa));
    }

    @GetMapping()
    public ResponseEntity<List<Etapa>> obterTodos() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etapa> obterPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etapa> atualizar(
            @PathVariable Long id,
            @RequestBody Etapa etapa) {

        var etapaAtualizar = repo.findById(id);

        if (etapaAtualizar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var etapaAtualizada = etapaAtualizar.get();

        etapaAtualizada.setNome(etapa.getNome());

        return ResponseEntity.ok(repo.save(etapaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Etapa> excluir(@PathVariable Long id) {
        var etapaExcluir = repo.findById(id);

        if(etapaExcluir.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repo.delete(etapaExcluir.get());

        return ResponseEntity.noContent().build();
    }
}
