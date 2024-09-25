package bugurt.backlog.service;

import bugurt.backlog.model.Worker;
import bugurt.backlog.web.dto.WorkerDto;

import java.util.Optional;
import java.util.UUID;

public interface WorkerService {

    UUID createWorker(WorkerDto workerDto);

    Optional<Worker> getWorkerById(UUID workerId);

    UUID updateWorker(WorkerDto newWorkerDto);

    void deleteWorker(UUID workerId);
}
