package bugurt.backlog.service.mapper;

import bugurt.backlog.model.Offer;
import bugurt.backlog.web.dto.OfferDto;
import org.springframework.stereotype.Component;

@Component
public class OfferMapper {

    public Offer toOffer(OfferDto offerDto) {
        if (offerDto == null) {
            return null;
        }

        Offer offer = new Offer();

        offer.setId(offerDto.getId());
        offer.setHeader(offerDto.getHeader());
        offer.setDescription(offerDto.getDescription());
        offer.setStatus(offerDto.getStatus());
        offer.setWorkerId(offerDto.getWorker_id());

        return offer;
    }
}
