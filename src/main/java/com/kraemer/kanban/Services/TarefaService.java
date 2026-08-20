package com.kraemer.kanban.Services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kraemer.kanban.Entities.Tarefa;
import com.kraemer.kanban.Repositories.TarefaRepository;

@Service
public class TarefaService {

    private final TarefaRepository repo;

    public TarefaService(TarefaRepository repo) {
        this.repo = repo;
    }

    public Tarefa criar(Tarefa tarefa) {
        return repo.save(tarefa);
    }

    public List<Tarefa> obterTodos() {
        return repo.findAll();
    }

    public Tarefa obterPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrado"));
    }

    public Tarefa atualizar(Long id, Tarefa tarefa) {
        var tarefaAtual = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrado"));

        tarefaAtual.setNome(tarefa.getNome());
        tarefaAtual.setEtapa(tarefa.getEtapa());
        return repo.save(tarefaAtual);
    }

    public void deletar(Long id) {
        var tarefaRemover = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrado"));
        repo.delete(tarefaRemover);
    }

}
