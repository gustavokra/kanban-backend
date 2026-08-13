package com.kraemer.kanban.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kraemer.kanban.Entities.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
}
