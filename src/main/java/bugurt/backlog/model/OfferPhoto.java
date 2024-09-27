package bugurt.backlog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "offer_photo")
@Getter
@Setter
public class OfferPhoto extends BaseEntity{

    private String photo;

    private UUID offerId;
}