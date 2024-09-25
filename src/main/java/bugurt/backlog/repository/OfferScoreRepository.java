package bugurt.backlog.repository;

import bugurt.backlog.model.OfferPhoto;
import bugurt.backlog.model.OfferScore;
import bugurt.backlog.model.OfferScoreId;
import bugurt.backlog.model.OfferScoreType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OfferScoreRepository extends JpaRepository<OfferScore, OfferScoreId> {
    long countByType(OfferScoreType type);
}
