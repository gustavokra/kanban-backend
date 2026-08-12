package com.kraemer.tarefas.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    

}
