package bugurt.backlog.service;

import bugurt.backlog.model.Offer;
import bugurt.backlog.model.OfferPhoto;

import java.util.List;
import java.util.UUID;

public interface OfferPhotoService {

    UUID createOfferPhoto(String PathPhoto, UUID offerId);

    List<OfferPhoto> getOfferPhotos(UUID offerId);

    void deleteOfferPhoto(UUID offerPhotoId);
}
