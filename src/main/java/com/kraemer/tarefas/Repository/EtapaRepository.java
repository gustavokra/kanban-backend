package com.kraemer.tarefas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kraemer.tarefas.Entities.Etapa;

public interface EtapaRepository extends JpaRepository<Etapa, Long> {
    
}
