package com.kraemer.kanban.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kraemer.kanban.Entities.Etapa;

public interface EtapaRepository extends JpaRepository<Etapa, Long> {
    
}
