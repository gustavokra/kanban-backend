package com.kraemer.kanban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class QuadroServiceTest {

    @Mock
    private QuadroRepository repo;

    @InjectMocks
    private QuadroService service;

    @Test
    void deveObterQuadroPorId() {
        Long id = 1L;

        Quadro quadro = new Quadro();
        quadro.setId(id);
        quadro.setNome("Backlog");

        when(repo.findById(id)).thenReturn(Optional.of(quadro));

        Quadro resultado = service.obterPorId(id);

        assertEquals(quadro, resultado);

        verify(repo).findById(id);
    }

    @Test
    void deveLancar404QuandoQuadroNaoExiste() {
        Long id = 1L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.obterPorId(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Quadro não encontrado", exception.getReason());

        verify(repo).findById(id);
    }
}