package bugurt.backlog.service;

import bugurt.backlog.model.Offer;
import bugurt.backlog.web.dto.OfferDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferService {

    UUID crateOffer(OfferDto offerDto);

    Optional<Offer> getOfferById(UUID offerId);

    List<Offer> getAllOffers();

    UUID updateOffer(OfferDto offerDto);

    void deleteOffer(UUID offerId);
}
