package bugurt.backlog.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OfferDto {
    private UUID id;

    private String header;

    private String description;

    private String status;

    private UUID worker_id;
}