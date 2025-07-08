package joc.service;

import jakarta.transaction.Transactional;
import joc.model.*;
import joc.persistence.IConfiguratieRepository;
import joc.persistence.IJocRepository;
import joc.persistence.IJucatorRepository;
import joc.persistence.IMutareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JocService {

    private final IJocRepository jocRepo;
    private final IJucatorRepository jucatorRepo;
    private final IConfiguratieRepository configuratieRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final IMutareRepository mutareRepo;

    @Autowired
    public JocService(IJocRepository jocRepo, IJucatorRepository jucatorRepo,
                      IConfiguratieRepository configuratieRepo, SimpMessagingTemplate messagingTemplate, IMutareRepository mutareRepo) {
        this.jocRepo = jocRepo;
        this.jucatorRepo = jucatorRepo;
        this.configuratieRepo = configuratieRepo;
        this.messagingTemplate = messagingTemplate;
        this.mutareRepo = mutareRepo;

    }

    @Transactional
    public Optional<Joc> startJoc(String numeJucator) {
        Optional<Jucator> jucatorOpt = jucatorRepo.findByName(numeJucator);
        Optional<Configuratie> configuratieOpt = configuratieRepo.getRandomConfiguratie();

        if (jucatorOpt.isPresent() && configuratieOpt.isPresent()) {
            Joc joc = new Joc(jucatorOpt.get(), configuratieOpt.get());
            return jocRepo.save(joc);
        }
        return Optional.empty();
    }

    public Iterable<Joc> findAll() {
        return jocRepo.findAll();
    }

    @Transactional
    public void adaugaScor(Integer jocId, int scorDelta) {
    jocRepo.findById(jocId).ifPresent(joc -> {
        joc.setScor(joc.getScor() + scorDelta);
            jocRepo.save(joc);
        trimiteClasamentLive();
        });
    }

    public Optional<Joc> findById(Integer id) {
        return jocRepo.findById(id);
    }

    @Transactional
    public void adaugaPozitii(Integer jocID, boolean ghicit){
        jocRepo.findById(jocID).ifPresent(joc -> {
            if (ghicit) {
                joc.setPozitiiGhicite(joc.getPozitiiGhicite() + 1);
            }
            jocRepo.save(joc);
            trimiteClasamentLive();
        });
    }

    public void trimiteClasamentLive() {
        List<ClasamentDTO> clasament = StreamSupport
            .stream(jocRepo.findAll().spliterator(), false)
            .sorted((j1, j2) -> Integer.compare(j2.getScor(), j1.getScor()))
            .map(joc -> new ClasamentDTO(
                joc.getJucator().getNume(),
                joc.getStartTime(),
                joc.getScor(),
                joc.getPozitiiGhicite()
            ))
            .collect(Collectors.toList());

        messagingTemplate.convertAndSend("/topic/clasament", clasament);
    }

    public List<JocCuMutariDTO> getJocuriCuGhiciteByJucator(String numeJucator) {
    Optional<Jucator> jucatorOpt = jucatorRepo.findByName(numeJucator);
    if (jucatorOpt.isEmpty()) return List.of();

    Jucator jucator = jucatorOpt.get();

    Iterable<Mutare> iterableMutari = mutareRepo.findAllByJucatorId(jucator.getId());
    List<Mutare> toateMutarile = StreamSupport
        .stream(iterableMutari.spliterator(), false)
        .collect(Collectors.toList());


    Map<Integer, List<Mutare>> mutariPeJoc = toateMutarile.stream()
        .collect(Collectors.groupingBy(Mutare::getJocId));

    return mutariPeJoc.entrySet().stream()
        .filter(e -> e.getValue().stream().anyMatch(Mutare::isGhicit))
        .map(e -> {
            int jocId = e.getKey();
            List<Mutare> mutari = e.getValue();

            Joc joc = jocRepo.findById(jocId).orElse(null);
            if (joc == null) return null;

            JocCuMutariDTO dto = new JocCuMutariDTO();
            dto.setJocId(jocId);
            dto.setStartTime(joc.getStartTime());
            dto.setScor(joc.getScor());
            dto.setPozitiiGhicite(joc.getPozitiiGhicite());
            dto.setConfiguratie(joc.getConfiguratie());
            dto.setMutari(mutari);
            return dto;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }
}
