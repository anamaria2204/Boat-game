package joc.rest.controller;

import joc.model.Mutare;
import joc.persistence.IMutareRepository;
import joc.persistence.hibernate.MutareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/mutare")
public class MutareController {

    private final IMutareRepository mutareRepository;
    private final MutareRepo mutareRepo;

    @Autowired
    public MutareController(IMutareRepository mutareRepository, MutareRepo mutareRepo) {
        this.mutareRepository = mutareRepository;
        this.mutareRepo = mutareRepo;
    }

    @PostMapping
    public ResponseEntity<Mutare> saveMutare(@RequestBody Mutare mutare){
        Optional<Mutare> saved = mutareRepo.save(mutare);
        return saved.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Mutare>> getMutariByJoc(@RequestParam Integer jocId) {
        Iterable<Mutare> mutari = mutareRepo.findAllByJocId(jocId);
        return ResponseEntity.ok(mutari);
    }

}
