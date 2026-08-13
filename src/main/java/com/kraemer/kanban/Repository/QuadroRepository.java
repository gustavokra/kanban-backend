package com.kraemer.kanban.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kraemer.kanban.Entities.Quadro;

public interface QuadroRepository extends JpaRepository<Quadro, Long> {
    
}
