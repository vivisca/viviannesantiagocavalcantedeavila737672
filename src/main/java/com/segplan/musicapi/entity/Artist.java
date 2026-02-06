package com.segplan.musicapi.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Data @Entity @Table(name = "artist")
public class Artist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String type;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL) private List<Album> albums;
}
