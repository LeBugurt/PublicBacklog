package bugurt.backlog.service.impl;

import bugurt.backlog.model.Offer;
import bugurt.backlog.repository.OfferRepository;
import bugurt.backlog.service.mapper.OfferMapper;
import bugurt.backlog.web.dto.OfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferMapper offerMapper;

    @InjectMocks
    private OfferServiceImpl offerService;

    private OfferDto offerDto;
    private Offer offer;

    @BeforeEach
    void setUp() {
        offerDto = new OfferDto();
        offerDto.setId(UUID.randomUUID());
        offer = new Offer();
        offer.setId(offerDto.getId());
    }

    @Test
    void crateOffer() {
        Mockito.when(offerMapper.toOffer(offerDto)).thenReturn(offer);
        Mockito.when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        UUID result = offerService.crateOffer(offerDto);

        assertNotNull(result);
        assertEquals(offerDto.getId(), result);
        Mockito.verify(offerRepository, Mockito.times(1)).save(any(Offer.class));
    }

    @Test
    void getOfferById() {
        UUID offerId = UUID.randomUUID();
        Mockito.when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        Optional<Offer> result = offerService.getOfferById(offerId);

        assertTrue(result.isPresent());
        assertEquals(offer, result.get());
        Mockito.verify(offerRepository, Mockito.times(1)).findById(offerId);
    }

    @Test
    void getOfferById_ShouldThrowException() {
        UUID offerId = UUID.randomUUID();
        Mockito.when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        Optional<Offer> result = offerService.getOfferById(offerId);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllOffers() {
        List<Offer> offers = List.of(new Offer(), new Offer());
        Mockito.when(offerRepository.findAll()).thenReturn(offers);

        List<Offer> result = offerService.getAllOffers();

        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(offerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void updateOffer() {
        // Мокаем проверку наличия и обновление предложения
        Mockito.when(offerRepository.existsById(offerDto.getId())).thenReturn(true);
        Mockito.when(offerMapper.toOffer(offerDto)).thenReturn(offer);
        Mockito.when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        UUID result = offerService.updateOffer(offerDto);

        assertEquals(offerDto.getId(), result);
        Mockito.verify(offerRepository, Mockito.times(1)).existsById(offerDto.getId());
        Mockito.verify(offerRepository, Mockito.times(1)).save(any(Offer.class));
    }

    @Test
    void deleteOffer() {
        UUID offerId = UUID.randomUUID();
        Mockito.when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        offerService.deleteOffer(offerId);

        Mockito.verify(offerRepository, Mockito.times(1)).findById(offerId);
        Mockito.verify(offerRepository, Mockito.times(1)).delete(offer);
    }
}