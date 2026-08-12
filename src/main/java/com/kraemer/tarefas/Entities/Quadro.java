package com.kraemer.tarefas.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "quadro")
public class Quadro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 25, nullable = false, unique = true)
    private String nome;

    // @OneToMany(mappedBy = "quadro")
    // private List<Etapa> etapas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // public List<Etapa> getEtapas() {
    // return etapas;
    // }

    // public void setEtapas(List<Etapa> etapas) {
    // this.etapas = etapas;
    // }
}
