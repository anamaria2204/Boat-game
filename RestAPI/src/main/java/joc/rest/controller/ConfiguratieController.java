package joc.rest.controller;

import joc.model.Configuratie;
import joc.model.Jucator;
import joc.persistence.IConfiguratieRepository;
import joc.persistence.IJucatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/configuratie")
public class ConfiguratieController {

    private final IConfiguratieRepository configuratieRepo;

    @Autowired
    public ConfiguratieController(IConfiguratieRepository configuratieRepo) {
        this.configuratieRepo = configuratieRepo;
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "msg", defaultValue = "Hello") String msg) {
        return ("Configuratie API - test: " + msg).toUpperCase();
    }


    @GetMapping
    public Iterable<Configuratie> getAll() {
        return configuratieRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Configuratie> getById(@PathVariable int id) {
        Optional<Configuratie> result = configuratieRepo.findOne(id);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> addConfiguratie(@RequestBody Configuratie configuratie) {
        int p11 = configuratie.getPozitie11();
        int p12 = configuratie.getPozitie12();
        int p21 = configuratie.getPozitie21();
        int p22 = configuratie.getPozitie22();
        int p31 = configuratie.getPozitie31();
        int p32 = configuratie.getPozitie32();

        boolean aceeasiLinie = p11 == p21 && p21 == p31;
        boolean aceeasiColoana = p12 == p22 && p22 == p32;

        if (!aceeasiLinie && !aceeasiColoana) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Configurația trebuie să fie pe aceeași linie SAU coloană.");
        }

        if (aceeasiColoana) {
            int[] linii = {p11, p21, p31};
            Arrays.sort(linii);
            if (!(linii[1] == linii[0] + 1 && linii[2] == linii[1] + 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Liniile trebuie să fie consecutive dacă toate coloanele sunt egale.");
            }
        }

        if (aceeasiLinie) {
            int[] coloane = {p12, p22, p32};
            Arrays.sort(coloane);
            if (!(coloane[1] == coloane[0] + 1 && coloane[2] == coloane[1] + 1)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Coloanele trebuie să fie consecutive dacă toate liniile sunt egale.");
            }
        }

        Optional<Configuratie> saved = configuratieRepo.save(configuratie);
        return saved.map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }



    @GetMapping("/random")
    public ResponseEntity<Configuratie> getRandomConfiguratie() {
        Optional<Configuratie> randomConfig = configuratieRepo.getRandomConfiguratie();
        return randomConfig
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
