package bugurt.backlog.service.impl;

import bugurt.backlog.exception.ResourceNotFoundException;
import bugurt.backlog.model.Offer;
import bugurt.backlog.repository.OfferRepository;
import bugurt.backlog.service.OfferService;
import bugurt.backlog.service.mapper.OfferMapper;
import bugurt.backlog.web.dto.OfferDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    @Override
    public UUID crateOffer(OfferDto offerDto) {
        Offer newOffer = offerMapper.toOffer(offerDto);
        return offerRepository.save(newOffer).getId();
    }

    @Override
    public Optional<Offer> getOfferById(UUID offerId) {
        return offerRepository.findById(offerId);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public UUID updateOffer(OfferDto offerDto) {
        if (offerRepository.existsById(offerDto.getId())) {
            return crateOffer(offerDto);
        } else {
            throw new ResourceNotFoundException("Offer not found with id: " + offerDto.getId());
        }
    }

    @Override
    public void deleteOffer(UUID offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id: " + offerId));

        offerRepository.delete(offer);
    }
}
