package br.com.elo7.explorer.probe.controller;

import br.com.elo7.explorer.probe.dto.ProbeDTO;
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
    public void create(@Valid @RequestBody ProbeDTO probeDTO){
        probeService.create(probeDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProbeDTO>> list(){
        return ResponseEntity.ok().body(probeService.getAll());
    }

}
