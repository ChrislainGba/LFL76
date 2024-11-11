package com.lfl.entity;

import java.util.Date;
import java.util.List;

import com.lfl.enums.RhFileTemplateValidator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RhFileTemplate extends BaseEntity {

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "question", columnDefinition = "JSONB", nullable = false)
    private String question;

    @Column(name = "title", length = 50, nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "validator")
    private RhFileTemplateValidator validator;

    @OneToMany(mappedBy = "rhFileTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RhFile> rhFiles;
}
