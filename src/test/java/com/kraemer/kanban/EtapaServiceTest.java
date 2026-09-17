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

import com.kraemer.kanban.Entities.Etapa;
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
    
}
