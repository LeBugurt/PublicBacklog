package bugurt.backlog.service.impl;

import bugurt.backlog.exception.ResourceNotFoundException;
import bugurt.backlog.model.OfferPhoto;
import bugurt.backlog.repository.OfferPhotoRepository;
import bugurt.backlog.service.OfferPhotoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OfferPhotoServiceImpl implements OfferPhotoService {

    private final OfferPhotoRepository offerPhotoRepository;

    @Override
    public UUID createOfferPhoto(String PathPhoto, UUID offerId) {
        OfferPhoto offerPhoto = new OfferPhoto();
        offerPhoto.setOfferId(offerId);
        offerPhoto.setPhoto(PathPhoto);
        return offerPhotoRepository.save(offerPhoto).getOfferId();
    }

    @Override
    public List<OfferPhoto> getOfferPhotos(UUID offerId) {
        return offerPhotoRepository.findByOfferId(offerId);
    }

    @Override
    public void deleteOfferPhoto(UUID offerPhotoId) {
        OfferPhoto offerPhoto = offerPhotoRepository.findById(offerPhotoId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Photo id" + offerPhotoId));

        offerPhotoRepository.delete(offerPhoto);
    }
}
