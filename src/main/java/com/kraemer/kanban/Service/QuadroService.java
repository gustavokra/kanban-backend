package com.kraemer.kanban.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kraemer.kanban.Entities.Quadro;
import com.kraemer.kanban.Repository.QuadroRepository;

@Service
public class QuadroService {

    private final QuadroRepository repo;

    public QuadroService(QuadroRepository repo) {
        this.repo = repo;
    }

    public Quadro criar(Quadro quadro) {
        return repo.save(quadro);
    }

    public List<Quadro> obterTodos() {
        return repo.findAll();
    }

    public Quadro obterPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quadro não encontrado"));
    }

    public Quadro atualizar(Long id, Quadro quadro) {
        var quadroAtual = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quadro não encontrado"));

        quadroAtual.setNome(quadro.getNome());
        return repo.save(quadroAtual);
    }

    public void deletar(Long id) {
        var quadroRemover = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quadro não encontrado"));
        repo.delete(quadroRemover);
    }

}
