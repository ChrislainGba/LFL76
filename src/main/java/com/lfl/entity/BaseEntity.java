package com.lfl.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
	/**
     * Identifiant unique généré de manière aléatoire pour chaque entité héritant.
     * Utilise un UUID pour garantir l'unicité globale des enregistrements.
     */
	
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
