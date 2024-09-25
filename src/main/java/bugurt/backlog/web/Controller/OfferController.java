package bugurt.backlog.web.Controller;

import bugurt.backlog.exception.FileLoadException;
import bugurt.backlog.exception.ResourceNotFoundException;
import bugurt.backlog.model.Offer;
import bugurt.backlog.model.OfferPhoto;
import bugurt.backlog.model.OfferScore;
import bugurt.backlog.service.OfferPhotoService;
import bugurt.backlog.service.OfferScoreService;
import bugurt.backlog.service.OfferService;
import bugurt.backlog.web.dto.OfferDto;
import bugurt.backlog.web.dto.OfferScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/offer")
public class OfferController {
    private final OfferService offerService;
    private final OfferPhotoService offerPhotoService;
    private final OfferScoreService offerScoreService;
    private final String uploadDir = "src/main/resources/img";

    @Autowired
    public OfferController(OfferService offerService, OfferPhotoService offerPhotoService, OfferScoreService offerScoreService) {
        this.offerService = offerService;
        this.offerPhotoService = offerPhotoService;
        this.offerScoreService = offerScoreService;
    }

    @PostMapping
    public UUID createOffer(@RequestPart("offer") OfferDto offerDto,
                            @RequestPart("files") MultipartFile[] files) {
        if (files.length > 10) {
            throw new FileLoadException("You cannot upload more than 10 photos");
        }
        offerDto.setStatus("Accepted");
        UUID offerID = offerService.crateOffer(offerDto);

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    Path path = Paths.get(uploadDir);
                    if (!Files.exists(path)) {
                        Files.createDirectories(path);
                    }

                    String fileName = file.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir, fileName);
                    Files.write(filePath, file.getBytes());
                    offerPhotoService.createOfferPhoto(filePath.toString(), offerID);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        offerScoreService.createOfferScore(offerDto.getWorker_id(), offerID);

        return offerID;
    }

    @PutMapping
    public UUID updateOffer(@RequestBody OfferDto offerDto) {
        return offerService.updateOffer(offerDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable UUID id) {
        Offer offer = offerService.getOfferById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id: " + id));
        return ResponseEntity.ok(offer);
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<List<OfferPhoto>> getOfferPhotos(@PathVariable UUID id) {
        return ResponseEntity.ok(offerPhotoService.getOfferPhotos(id));
    }

    @PutMapping("/score/{worker_id}/{offer_id}")
    public ResponseEntity<OfferScore> updateOfferScore(@PathVariable UUID worker_id, @PathVariable UUID offer_id, @RequestBody OfferScoreDto offerScoreDto) {
        return ResponseEntity.ok(offerScoreService.updateOfferScore(worker_id, offer_id, offerScoreDto));
    }

    @PutMapping("/status")
    public UUID updateOfferStatus(@RequestBody OfferDto offerDto) {
        return offerService.updateOffer(offerDto);
    }

    @GetMapping("/score/likes")
    public long getOfferScoreLikes() {
        return offerScoreService.offerScoreCountLikes();
    }

    @GetMapping("/score/dislikes")
    public long getOfferScoreDislikes() {
        return offerScoreService.offerScoreCountDislikes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable UUID id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/photo/{id}")
    public ResponseEntity<Void> deleteOfferPhoto(@PathVariable UUID id) {
        offerPhotoService.deleteOfferPhoto(id);
        return ResponseEntity.noContent().build();
    }
}
