package com.segplan.musicapi.repository;
import com.segplan.musicapi.entity.Regional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RegionalRepository extends JpaRepository<Regional, Long> {
    List<Regional> findByAtivoTrue();
}
