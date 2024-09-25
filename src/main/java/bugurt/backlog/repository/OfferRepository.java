package bugurt.backlog.repository;

import bugurt.backlog.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OfferRepository extends JpaRepository<Offer, UUID> {

}
