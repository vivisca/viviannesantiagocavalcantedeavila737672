package com.segplan.musicapi.repository;
import com.segplan.musicapi.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findAllByOrderByNameAsc();
    List<Artist> findAllByOrderByNameDesc();
}
