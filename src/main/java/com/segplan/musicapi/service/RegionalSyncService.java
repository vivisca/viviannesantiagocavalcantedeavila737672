package com.segplan.musicapi.service;
import com.segplan.musicapi.dto.RegionalDto;
import com.segplan.musicapi.entity.Regional;
import com.segplan.musicapi.repository.RegionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionalSyncService {
    private final RegionalRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public void syncRegionais() {
        try {
            RegionalDto[] externalList = restTemplate.getForObject("https://integrador-argus-api.geia.vip/v1/regionais", RegionalDto[].class);
            if (externalList == null) return;
            
            Map<Integer, RegionalDto> externalMap = Arrays.stream(externalList)
                .collect(Collectors.toMap(RegionalDto::id, Function.identity()));
            List<Regional> internalActive = repository.findByAtivoTrue();
            
            for (Regional internal : internalActive) {
                if (!externalMap.containsKey(internal.getExternalId())) {
                    internal.setAtivo(false);
                    repository.save(internal);
                } else {
                    RegionalDto external = externalMap.get(internal.getExternalId());
                    if (!internal.getNome().equals(external.nome())) {
                        internal.setAtivo(false);
                        repository.save(internal);
                        Regional newVersion = new Regional();
                        newVersion.setExternalId(external.id());
                        newVersion.setNome(external.nome());
                        newVersion.setAtivo(true);
                        repository.save(newVersion);
                    }
                    externalMap.remove(internal.getExternalId());
                }
            }
            externalMap.values().forEach(dto -> {
                Regional newReg = new Regional();
                newReg.setExternalId(dto.id());
                newReg.setNome(dto.nome());
                newReg.setAtivo(true);
                repository.save(newReg);
            });
        } catch (Exception e) {
            System.err.println("Erro ao sincronizar: " + e.getMessage());
        }
    }
}
