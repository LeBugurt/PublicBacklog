package bugurt.backlog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "offer")
@Getter
@Setter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String header;

    private String description;

    private String status;

    private UUID workerId;
}
