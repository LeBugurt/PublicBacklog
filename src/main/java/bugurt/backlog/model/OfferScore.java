package bugurt.backlog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "offer_score")
@Getter
@Setter
@IdClass(OfferScoreId.class)
public class OfferScore {

    @Enumerated(EnumType.STRING)
    private OfferScoreType type;

    @Id
    private UUID worker_id;

    @Id
    private UUID offer_id;
}