package joc.rest.controller;

import joc.model.ClasamentDTO;
import joc.model.Joc;
import joc.model.JocCuMutariDTO;
import joc.model.Jucator;
import joc.persistence.IJocRepository;
import joc.persistence.IJucatorRepository;
import joc.service.JocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@CrossOrigin
@RestController
@RequestMapping("/api/joc")
public class JocController {

    private final JocService jocService;

    @Autowired
    public JocController(JocService jocService) {
        this.jocService = jocService;
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "msg", defaultValue = "Hello") String msg) {
        return ("Joc API - test: " + msg).toUpperCase();
    }

    @GetMapping
    public Iterable<Joc> getAll() {
        return jocService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joc> getById(@PathVariable int id) {
        Optional<Joc> result = jocService.findById(id);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/start")
    public ResponseEntity<Joc> startJoc(@RequestParam String numeJucator) {
        Optional<Joc> jocOpt = jocService.startJoc(numeJucator);
        return jocOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}/scor")
    public ResponseEntity<?> updateScor(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        int scor = body.get("scor");
        jocService.adaugaScor(id, scor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/ghicit")
    public ResponseEntity<?> updatePozitiiGhicite(@PathVariable Integer id, @RequestBody Map<String, Boolean> body) {
        boolean ghicit = body.getOrDefault("ghicit", false);
        jocService.adaugaPozitii(id, ghicit);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/clasament")
    public ResponseEntity<List<ClasamentDTO>> getClasament() {
        List<ClasamentDTO> clasament = StreamSupport
            .stream(jocService.findAll().spliterator(), false)
            .sorted((j1, j2) -> Integer.compare(j2.getScor(), j1.getScor()))
            .map(joc -> new ClasamentDTO(
                joc.getJucator().getNume(),
                joc.getStartTime(),
                joc.getScor(),
                joc.getPozitiiGhicite()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(clasament);
    }


    @GetMapping("/istoric-ghicite/{numeJucator}")
    public ResponseEntity<List<JocCuMutariDTO>> getIstoricCuGhicite(@PathVariable String numeJucator) {
        return ResponseEntity.ok(jocService.getJocuriCuGhiciteByJucator(numeJucator));
    }



}
