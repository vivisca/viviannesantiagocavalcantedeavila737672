package com.segplan.musicapi.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data @Entity @Table(name = "album")
public class Album {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String title;
    @Column(name = "created_at") private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne @JoinColumn(name = "artist_id", nullable = false) private Artist artist;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL) private List<AlbumImage> images;
}
