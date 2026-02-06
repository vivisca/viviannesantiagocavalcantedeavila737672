package com.segplan.musicapi.service;
import com.segplan.musicapi.dto.RegionalDto;
import com.segplan.musicapi.entity.Regional;
import com.segplan.musicapi.repository.RegionalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionalSyncServiceTest {
    @Mock private RegionalRepository repository;
    @Mock private RestTemplate restTemplate;
    @InjectMocks private RegionalSyncService service;
    @Test
    @DisplayName("Sincroniza corretamente")
    void shouldSyncRegionaisCorrectly() {
        RegionalDto[] apiResponse = { new RegionalDto(1, "Norte"), new RegionalDto(2, "Sul Novo"), new RegionalDto(4, "Leste") };
        Regional reg1 = createRegional(1, "Norte");
        Regional reg2 = createRegional(2, "Sul Antigo");
        Regional reg3 = createRegional(3, "Oeste");
        when(restTemplate.getForObject(anyString(), eq(RegionalDto[].class))).thenReturn(apiResponse);
        when(repository.findByAtivoTrue()).thenReturn(new ArrayList<>(List.of(reg1, reg2, reg3)));
        
        service.syncRegionais();
        
        ArgumentCaptor<Regional> captor = ArgumentCaptor.forClass(Regional.class);
        verify(repository, times(4)).save(captor.capture());
    }
    private Regional createRegional(Integer extId, String nome) {
        Regional r = new Regional(); r.setExternalId(extId); r.setNome(nome); r.setAtivo(true); return r;
    }
}
