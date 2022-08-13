package br.com.elo7.explorer.probe.controller;

import br.com.elo7.explorer.probe.dto.ActionDTO;
import br.com.elo7.explorer.probe.dto.ProbeRequestDTO;
import br.com.elo7.explorer.probe.dto.ProbeResponseDTO;
import br.com.elo7.explorer.probe.service.ProbeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/probes")
public class ProbeController {

    private final ProbeService probeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ProbeRequestDTO ProbeRequestDTO){
        probeService.create(ProbeRequestDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProbeResponseDTO>> list(){
        return ResponseEntity.ok().body(probeService.getAll());
    }

    @PatchMapping("/{probe}")
    public void moveProbe(@Valid @RequestBody ActionDTO actionDTO, @PathVariable Long probe){
        probeService.moveProbe(actionDTO, probe);
    }
}
