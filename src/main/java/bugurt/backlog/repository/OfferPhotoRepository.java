package bugurt.backlog.repository;

import bugurt.backlog.model.OfferPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OfferPhotoRepository extends JpaRepository<OfferPhoto, UUID> {
    List<OfferPhoto> findByOfferId(UUID offerId);
}
