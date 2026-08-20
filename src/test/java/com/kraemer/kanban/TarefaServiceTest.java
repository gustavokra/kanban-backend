package com.kraemer.kanban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kraemer.kanban.Entities.Tarefa;
import com.kraemer.kanban.Repositories.TarefaRepository;
import com.kraemer.kanban.Services.TarefaService;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository repo;

    @InjectMocks
    private TarefaService service;

    @Test
    void deveCriarTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Estudar Java");

        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(1L);
        tarefaSalva.setNome("Estudar Java");

        when(repo.save(tarefa)).thenReturn(tarefaSalva);

        Tarefa resultado = service.criar(tarefa);

        assertEquals(tarefaSalva, resultado);
        verify(repo).save(tarefa);
    }

    @Test
    void deveRetornarTodasTarefas() {
        List<Tarefa> tarefas = List.of(new Tarefa(), new Tarefa());

        when(repo.findAll()).thenReturn(tarefas);

        List<Tarefa> resultado = service.obterTodos();

        assertEquals(tarefas, resultado);

        verify(repo).findAll();
    }
}
