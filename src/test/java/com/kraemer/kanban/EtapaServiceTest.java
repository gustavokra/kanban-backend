package com.kraemer.kanban;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kraemer.kanban.Repositories.EtapaRepository;
import com.kraemer.kanban.Services.EtapaService;

@ExtendWith(MockitoExtension.class)
public class EtapaServiceTest {
    
    @Mock 
    private EtapaRepository repo;

    @InjectMocks 
    private EtapaService service;


    
}
