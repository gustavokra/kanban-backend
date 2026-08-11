package com.kraemer.tarefas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kraemer.tarefas.Entities.Quadro;

public interface QuadroRepository extends JpaRepository<Quadro, Long> {
    
}
