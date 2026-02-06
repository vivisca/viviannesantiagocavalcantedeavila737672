package com.segplan.musicapi.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data @Entity @Table(name = "regional")
public class Regional {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "internal_id") private Long internalId;
    @Column(name = "id") private Integer externalId;
    private String nome;
    private Boolean ativo;
    @Column(name = "created_at") private LocalDateTime createdAt = LocalDateTime.now();
}
