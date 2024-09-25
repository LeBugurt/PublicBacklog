package bugurt.backlog.service.impl;

import bugurt.backlog.exception.ResourceNotFoundException;
import bugurt.backlog.model.OfferScore;
import bugurt.backlog.model.OfferScoreId;
import bugurt.backlog.model.OfferScoreType;
import bugurt.backlog.repository.OfferScoreRepository;
import bugurt.backlog.service.OfferScoreService;
import bugurt.backlog.web.dto.OfferScoreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OfferScoreServiceImpl implements OfferScoreService {
    private final OfferScoreRepository offerScoreRepository;

    @Override
    public OfferScore createOfferScore(UUID worker_id, UUID offer_id) {
        OfferScore offerScore = new OfferScore();
        offerScore.setType(OfferScoreType.NO_VALUE);
        offerScore.setOffer_id(offer_id);
        offerScore.setWorker_id(worker_id);

        return offerScoreRepository.save(offerScore);
    }

    @Override
    public OfferScore updateOfferScore(UUID worker_id, UUID offer_id, OfferScoreDto offerScoreDto) {
        OfferScoreId id = new OfferScoreId(worker_id, offer_id);
        OfferScore existingOfferScore = offerScoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OfferScore not found"));

        existingOfferScore.setType(offerScoreDto.getType());
        return offerScoreRepository.save(existingOfferScore);
    }

    @Override
    public long offerScoreCountLikes() {
        return offerScoreRepository.countByType(OfferScoreType.LIKE);
    }

    @Override
    public long offerScoreCountDislikes() {
        return offerScoreRepository.countByType(OfferScoreType.DISLIKE);
    }
}
