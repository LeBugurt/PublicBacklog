package bugurt.backlog.service.impl;

import bugurt.backlog.exception.ResourceNotFoundException;
import bugurt.backlog.model.Worker;
import bugurt.backlog.repository.WorkerRepository;
import bugurt.backlog.service.WorkerService;
import bugurt.backlog.service.mapper.WorkerMapper;
import bugurt.backlog.web.dto.WorkerDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;
    private final WorkerMapper workerMapper;

    @Override
    public UUID createWorker(WorkerDto workerDto) {
        Worker worker = workerMapper.toWorker(workerDto);
        return workerRepository.save(worker).getId();
    }

    @Override
    public Optional<Worker> getWorkerById(UUID workerId) {
        return workerRepository.findById(workerId);
    }

    @Override
    public UUID updateWorker(WorkerDto newWorkerDto) {
        if(workerRepository.existsById(newWorkerDto.getId())) {
            return createWorker(newWorkerDto);
        } else {
            throw new ResourceNotFoundException("Event not found with id " + newWorkerDto.getId());
        }
    }

    @Override
    public void deleteWorker(UUID workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id " + workerId));

        workerRepository.delete(worker);
    }
}
