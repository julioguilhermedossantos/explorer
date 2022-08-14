package br.com.elo7.explorer.probe.controller;

import br.com.elo7.explorer.probe.dto.ActionDTO;
import br.com.elo7.explorer.probe.dto.ProbeRequestDTO;
import br.com.elo7.explorer.probe.dto.ProbeResponseDTO;
import br.com.elo7.explorer.probe.service.ProbeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/probes")
public class ProbeController {

    private final ProbeService probeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ProbeRequestDTO ProbeRequestDTO) {
        log.info("[PlanetController] : Criando sonda");
        probeService.create(ProbeRequestDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProbeResponseDTO>> list() {
        return ResponseEntity.ok().body(probeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProbeResponseDTO> getProbe(@PathVariable Long id) {
        log.info("[PlanetController] : Buscando sonda por id {}", id);
        return ResponseEntity.ok().body(probeService.find(id));
    }

    @PatchMapping("/{id}")
    public void moveProbe(@Valid @RequestBody ActionDTO actionDTO, @PathVariable Long id) {
        log.info("[PlanetController] : movendo sonda: instrução {}", actionDTO.getAction());
        probeService.moveProbe(actionDTO, id);
    }

}
