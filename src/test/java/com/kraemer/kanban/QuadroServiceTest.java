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

import com.kraemer.kanban.Entities.Quadro;
import com.kraemer.kanban.Repositories.QuadroRepository;
import com.kraemer.kanban.Services.QuadroService;

@ExtendWith(MockitoExtension.class)
public class QuadroServiceTest {

    @Mock
    private QuadroRepository repo;

    @InjectMocks
    private QuadroService service;

    @Test
    void deveCriarQuadro() {
        Quadro quadro = new Quadro();
        quadro.setNome("Estudos");

        Quadro quadroSalva = new Quadro();
        quadroSalva.setId(1L);
        quadroSalva.setNome("Estudos");

        when(repo.save(quadro)).thenReturn(quadroSalva);

        Quadro resultado = service.criar(quadro);

        assertEquals(quadroSalva, resultado);
        verify(repo).save(quadro);
    }

    @Test
    void deveRetornarTodasQuadros() {
        List<Quadro> quadros = List.of(new Quadro(), new Quadro());

        when(repo.findAll()).thenReturn(quadros);

        List<Quadro> resultado = service.obterTodos();

        assertEquals(quadros, resultado);

        verify(repo).findAll();
    }

    @Test
    void deveObterQuadroPorId() {
        Long id = 1L;

        Quadro quadro = new Quadro();
        quadro.setId(id);
        quadro.setNome("nome");

        when(repo.findById(id)).thenReturn(Optional.of(quadro));

        Quadro resultado = service.obterPorId(id);

        assertEquals(quadro, resultado);
        verify(repo).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoQuadroNaoEncontrada() {
        Long id = 1L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException excecao = assertThrows(
                ResponseStatusException.class,
                () -> service.obterPorId(id));

        assertEquals(HttpStatus.NOT_FOUND, excecao.getStatusCode());
        assertEquals("Quadro não encontrado", excecao.getReason());

        verify(repo).findById(id);
    }

    @Test
    void deveAtualizarQuadro() {
        Long id = 1L;

        Quadro quadroAtual = new Quadro();
        quadroAtual.setId(id);
        quadroAtual.setNome("Quadro antiga");

        Quadro quadroNova = new Quadro();
        quadroNova.setNome("Quadro atualizada");


        when(repo.findById(id)).thenReturn(Optional.of(quadroAtual));
        when(repo.save(quadroAtual)).thenReturn(quadroAtual);

        Quadro resultado = service.atualizar(id, quadroNova);

        assertEquals("Quadro atualizada", resultado.getNome());

        verify(repo).findById(id);
        verify(repo).save(quadroAtual);
    }

    @Test
    void deveLancar404QuandoQuadroNaoExiste() {
        Long id = 999L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> service.atualizar(id, new Quadro()));

        verify(repo).findById(id);
        verify(repo, never()).save(any());
    }

    @Test
    void deveDeletarQuadroQuandoExistir() {
        Long id = 1L;
        Quadro quadro = new Quadro();
        quadro.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(quadro));

        service.deletar(id);

        verify(repo).findById(id);
        verify(repo).delete(quadro);
    }

        @Test
    void deveRetornarNotFoundQuandoQuadroNaoExistir() {
        Long id = 1L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.deletar(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(repo).findById(id);
        verify(repo, never()).delete(any());
        
    }
}
