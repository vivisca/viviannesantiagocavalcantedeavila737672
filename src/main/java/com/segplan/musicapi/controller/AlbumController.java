package com.segplan.musicapi.controller;
import com.segplan.musicapi.entity.Album;
import com.segplan.musicapi.repository.AlbumRepository;
import com.segplan.musicapi.service.MinioStorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/v1/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumRepository albumRepository;
    private final MinioStorageService storageService;
    @Operation(summary = "Listar Ã¡lbuns com paginaÃ§Ã£o")
    @GetMapping
    public Page<Album> listAlbums(@RequestParam(required = false) String artistType, Pageable pageable) {
        if (artistType != null) return albumRepository.findByArtistType(artistType, pageable);
        return albumRepository.findAll(pageable);
    }
    @Operation(summary = "Upload de capa do Ã¡lbum")
    @PostMapping("/{id}/cover")
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String path = storageService.uploadImage(file, id);
        return ResponseEntity.ok("Imagem salva em: " + path);
    }
}
