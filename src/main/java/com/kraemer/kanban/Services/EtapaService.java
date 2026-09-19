package com.kraemer.kanban.Services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kraemer.kanban.Entities.Etapa;
import com.kraemer.kanban.Repositories.EtapaRepository;

@Service
public class EtapaService {

    private final EtapaRepository repo;

    public EtapaService(EtapaRepository repo) {
        this.repo = repo;
    }

    public Etapa criar(Etapa etapa) {
        return repo.save(etapa);
    }

    public List<Etapa> obterTodos() {
        return repo.findAll();
    }

    public Etapa obterPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrado"));
    }

    public Etapa atualizar(Long id, Etapa etapa) {
        var etapaAtual = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrado"));

        etapaAtual.setNome(etapa.getNome());
        etapaAtual.setQuadro(etapa.getQuadro());
        return repo.save(etapaAtual);
    }

    public void deletar(Long id) {
        var etapaRemover = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrado"));
        repo.delete(etapaRemover);
    }

}
