package com.kraemer.tarefas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kraemer.tarefas.Entities.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
}
