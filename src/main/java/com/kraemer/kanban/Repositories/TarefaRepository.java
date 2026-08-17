package com.kraemer.kanban.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kraemer.kanban.Entities.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
}
