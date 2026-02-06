package com.segplan.musicapi.repository;
import com.segplan.musicapi.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findAll(Pageable pageable);
    @Query("SELECT a FROM Album a WHERE a.artist.type = :type")
    Page<Album> findByArtistType(@Param("type") String type, Pageable pageable);
}
