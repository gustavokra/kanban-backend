package com.kraemer.kanban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.kraemer.kanban.Entities.Etapa;
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

    @Test
    void deveObterTarefaPorId() {
        Long id = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);
        tarefa.setNome("Estudar Java");

        when(repo.findById(id)).thenReturn(Optional.of(tarefa));

        Tarefa resultado = service.obterPorId(id);

        assertEquals(tarefa, resultado);
        verify(repo).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontrada() {
        Long id = 1L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException excecao = assertThrows(
                ResponseStatusException.class,
                () -> service.obterPorId(id));

        assertEquals(HttpStatus.NOT_FOUND, excecao.getStatusCode());
        assertEquals("Tarefa não encontrado", excecao.getReason());

        verify(repo).findById(id);
    }

    @Test
    void deveAtualizarTarefa() {
        Long id = 1L;

        Tarefa tarefaAtual = new Tarefa();
        tarefaAtual.setId(id);
        tarefaAtual.setNome("Tarefa antiga");

        Tarefa tarefaNova = new Tarefa();
        tarefaNova.setNome("Tarefa atualizada");

        Etapa etapa = new Etapa();
        tarefaNova.setEtapa(etapa);

        when(repo.findById(id)).thenReturn(Optional.of(tarefaAtual));
        when(repo.save(tarefaAtual)).thenReturn(tarefaAtual);

        Tarefa resultado = service.atualizar(id, tarefaNova);

        assertEquals("Tarefa atualizada", resultado.getNome());
        assertEquals(etapa, resultado.getEtapa());

        verify(repo).findById(id);
        verify(repo).save(tarefaAtual);
    }

    @Test
    void deveLancar404QuandoTarefaNaoExiste() {
        Long id = 999L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> service.atualizar(id, new Tarefa()));

        verify(repo).findById(id);
        verify(repo, never()).save(any());
    }

}
