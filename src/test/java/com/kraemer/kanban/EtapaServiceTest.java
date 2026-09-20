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
import com.kraemer.kanban.Entities.Quadro;
import com.kraemer.kanban.Entities.Tarefa;
import com.kraemer.kanban.Repositories.EtapaRepository;
import com.kraemer.kanban.Services.EtapaService;

@ExtendWith(MockitoExtension.class)
public class EtapaServiceTest {

    @Mock
    private EtapaRepository repo;

    @InjectMocks
    private EtapaService service;

    @Test
    void deveCriarEtapa() {
        Etapa etapa = new Etapa();
        etapa.setNome("Estudos");

        Etapa etapaSalva = new Etapa();
        etapaSalva.setId(1L);
        etapaSalva.setNome("Estudos");

        when(repo.save(etapa)).thenReturn(etapaSalva);

        Etapa resultado = service.criar(etapa);

        assertEquals(etapaSalva, resultado);
        verify(repo).save(etapa);
    }

    @Test
    void deveRetornarTodasEtapas() {
        List<Etapa> etapas = List.of(new Etapa(), new Etapa());

        when(repo.findAll()).thenReturn(etapas);

        List<Etapa> resultado = service.obterTodos();

        assertEquals(etapas, resultado);

        verify(repo).findAll();
    }

    @Test
    void deveObterEtapaPorId() {
        Long id = 1L;

        Etapa etapa = new Etapa();
        etapa.setId(id);
        etapa.setNome("nome");

        when(repo.findById(id)).thenReturn(Optional.of(etapa));

        Etapa resultado = service.obterPorId(id);

        assertEquals(etapa, resultado);
        verify(repo).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoEtapaNaoEncontrada() {
        Long id = 1L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException excecao = assertThrows(
                ResponseStatusException.class,
                () -> service.obterPorId(id));

        assertEquals(HttpStatus.NOT_FOUND, excecao.getStatusCode());
        assertEquals("Etapa não encontrado", excecao.getReason());

        verify(repo).findById(id);
    }

    @Test
    void deveAtualizarEtapa() {
        Long id = 1L;

        Etapa etapaAtual = new Etapa();
        etapaAtual.setId(id);
        etapaAtual.setNome("Etapa antiga");

        Etapa etapaNova = new Etapa();
        etapaNova.setNome("Etapa atualizada");

        Quadro quadro = new Quadro();
        etapaNova.setQuadro(quadro);

        when(repo.findById(id)).thenReturn(Optional.of(etapaAtual));
        when(repo.save(etapaAtual)).thenReturn(etapaAtual);

        Etapa resultado = service.atualizar(id, etapaNova);

        assertEquals("Etapa atualizada", resultado.getNome());
        assertEquals(quadro, resultado.getQuadro());

        verify(repo).findById(id);
        verify(repo).save(etapaAtual);
    }

    @Test
    void deveLancar404QuandoEtapaNaoExiste() {
        Long id = 999L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> service.atualizar(id, new Etapa()));

        verify(repo).findById(id);
        verify(repo, never()).save(any());
    }

}
