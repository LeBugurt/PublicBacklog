package bugurt.backlog.service.impl;

import bugurt.backlog.model.OfferPhoto;
import bugurt.backlog.repository.OfferPhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class OfferPhotoServiceImplTest {

    @Mock
    private OfferPhotoRepository offerPhotoRepository;

    @InjectMocks
    private OfferPhotoServiceImpl offerPhotoService;

    private OfferPhoto offerPhoto;
    private UUID offerId;
    private UUID offerPhotoId;

    @BeforeEach
    void setUp() {
        // Создаем тестовые данные
        offerId = UUID.randomUUID();
        offerPhotoId = UUID.randomUUID();
        offerPhoto = new OfferPhoto();
        offerPhoto.setOfferId(offerId);
        offerPhoto.setId(offerPhotoId);
        offerPhoto.setPhoto("path/to/photo.jpg");
    }

    @Test
    void createOfferPhoto() {
        Mockito.when(offerPhotoRepository.save(any(OfferPhoto.class))).thenReturn(offerPhoto); // Мокаем сохранение

        UUID result = offerPhotoService.createOfferPhoto(offerPhoto.getPhoto(), offerId);

        assertNotNull(result);
        assertEquals(offerId, result);

        Mockito.verify(offerPhotoRepository, Mockito.times(1)).save(any(OfferPhoto.class)); // Проверка вызова сохранения
    }

    @Test
    void getOfferPhotos() {
        List<OfferPhoto> offerPhotos = new ArrayList<>();
        offerPhotos.add(offerPhoto);
        Mockito.when(offerPhotoRepository.findByOfferId(offerId)).thenReturn(offerPhotos);

        List<OfferPhoto> result = offerPhotoService.getOfferPhotos(offerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(offerPhoto, result.get(0));

        Mockito.verify(offerPhotoRepository, Mockito.times(1)).findByOfferId(offerId);
    }

    @Test
    void getOfferPhotos_ShouldReturnEmptyList() {
        Mockito.when(offerPhotoRepository.findByOfferId(offerId)).thenReturn(new ArrayList<>());

        List<OfferPhoto> result = offerPhotoService.getOfferPhotos(offerId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(offerPhotoRepository, Mockito.times(1)).findByOfferId(offerId);
    }

    @Test
    void deleteOfferPhoto() {
        Mockito.when(offerPhotoRepository.findById(offerPhotoId)).thenReturn(Optional.of(offerPhoto));

        offerPhotoService.deleteOfferPhoto(offerPhotoId);

        Mockito.verify(offerPhotoRepository, Mockito.times(1)).findById(offerPhotoId);
        Mockito.verify(offerPhotoRepository, Mockito.times(1)).delete(offerPhoto);
    }
}