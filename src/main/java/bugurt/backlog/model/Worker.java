package bugurt.backlog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "worker")
@Getter
@Setter
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String surname;

    private String name;

    private String patronymic;

    private String positional;

    private UUID id_department;

    private String phone;

    private LocalDate birthdate;

    private String email;

    private String telegram;

    private LocalDate date_start_work;

    private boolean probation_period;

    private String photo;

    private UUID id_director;

    private UUID id_office;
}
