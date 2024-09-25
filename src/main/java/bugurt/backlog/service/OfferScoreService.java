package bugurt.backlog.service;

import bugurt.backlog.model.OfferScore;
import bugurt.backlog.web.dto.OfferScoreDto;

import java.util.UUID;

public interface OfferScoreService {
    OfferScore createOfferScore(UUID worker_id, UUID offer_id);

    OfferScore updateOfferScore(UUID worker_id, UUID offer_id, OfferScoreDto offerScoreDto);

    long offerScoreCountLikes();

    long offerScoreCountDislikes();
}
