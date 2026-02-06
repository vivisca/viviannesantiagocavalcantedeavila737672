package com.segplan.musicapi.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data @Entity @Table(name = "album_image")
public class AlbumImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "object_name", nullable = false) private String objectName;
    @ManyToOne @JoinColumn(name = "album_id") private Album album;
}
