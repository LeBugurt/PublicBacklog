package bugurt.backlog.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class WorkerDto {

    private UUID id;

    private String name;

    private String surname;

    private String patronymic;

    private String positional;

    private UUID id_department;

    private String phone;

    private LocalDate birthdate;

    private String email;

    private String telegram;

    private String photo;

    private LocalDate date_start_work;

    private boolean is_probation_period;

    private UUID id_director;

    private UUID id_office;
}