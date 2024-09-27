package bugurt.backlog.service.impl;

import bugurt.backlog.model.OfferScore;
import bugurt.backlog.model.OfferScoreType;
import bugurt.backlog.repository.OfferScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OfferScoreServiceImplTest {

    @Mock
    private OfferScoreRepository offerScoreRepository;

    @InjectMocks
    private OfferScoreServiceImpl offerScoreService;

    private UUID workerId;
    private UUID offerId;
    private OfferScore offerScore;

    @BeforeEach
    void setUp() {
        workerId = UUID.randomUUID();
        offerId = UUID.randomUUID();
        offerScore = new OfferScore();
        offerScore.setWorker_id(workerId);
        offerScore.setOffer_id(offerId);
        offerScore.setType(OfferScoreType.NO_VALUE);
    }

    @Test
    void createOfferScore() {
        Mockito.when(offerScoreRepository.save(any(OfferScore.class))).thenReturn(offerScore);

        OfferScore result = offerScoreService.createOfferScore(workerId, offerId);

        assertNotNull(result);
        assertEquals(offerScore.getWorker_id(), result.getWorker_id());
        assertEquals(offerScore.getOffer_id(), result.getOffer_id());
        assertEquals(OfferScoreType.NO_VALUE, result.getType());

        Mockito.verify(offerScoreRepository, Mockito.times(1)).save(any(OfferScore.class));
    }

    @Test
    void offerScoreCountLikes() {
        Mockito.when(offerScoreRepository.countByType(OfferScoreType.LIKE)).thenReturn(5L);
        long result = offerScoreService.offerScoreCountLikes();
        assertEquals(5L, result);
        Mockito.verify(offerScoreRepository, Mockito.times(1)).countByType(OfferScoreType.LIKE);
    }

    @Test
    void offerScoreCountDislikes() {
        Mockito.when(offerScoreRepository.countByType(OfferScoreType.DISLIKE)).thenReturn(3L);
        long result = offerScoreService.offerScoreCountDislikes();
        assertEquals(3L, result);
        Mockito.verify(offerScoreRepository, Mockito.times(1)).countByType(OfferScoreType.DISLIKE);
    }
}