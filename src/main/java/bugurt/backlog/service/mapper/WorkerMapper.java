package bugurt.backlog.service.mapper;

import bugurt.backlog.model.Worker;
import bugurt.backlog.web.dto.WorkerDto;
import org.springframework.stereotype.Component;

@Component
public class WorkerMapper {
    public Worker toWorker(WorkerDto workerDto) {
        if (workerDto == null) {
            return null;
        }

        Worker worker = new Worker();

        worker.setId(workerDto.getId());
        worker.setName(workerDto.getName());
        worker.setSurname(workerDto.getSurname());
        worker.setPatronymic(workerDto.getPatronymic());
        worker.setPositional(workerDto.getPositional());
        worker.setId_department(workerDto.getId_department());
        worker.setPhone(workerDto.getPhone());
        worker.setBirthdate(workerDto.getBirthdate());
        worker.setEmail(workerDto.getEmail());
        worker.setTelegram(workerDto.getTelegram());
        worker.setPhoto(workerDto.getPhoto());
        worker.setDate_start_work(workerDto.getDate_start_work());
        worker.setProbation_period(workerDto.is_probation_period());
        worker.setId_director(workerDto.getId_director());
        worker.setId_office(workerDto.getId_office());

        return worker;
    }
}
