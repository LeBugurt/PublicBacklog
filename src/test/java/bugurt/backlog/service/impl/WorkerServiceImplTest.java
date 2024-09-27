package bugurt.backlog.service.impl;

import bugurt.backlog.exception.ResourceNotFoundException;
import bugurt.backlog.model.Worker;
import bugurt.backlog.repository.WorkerRepository;
import bugurt.backlog.service.mapper.WorkerMapper;
import bugurt.backlog.web.dto.WorkerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WorkerServiceImplTest {
    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private WorkerMapper workerMapper;  // Мокаем WorkerMapper

    @InjectMocks
    private WorkerServiceImpl workerService;

    private WorkerDto workerDto;
    private Worker worker;

    @BeforeEach
    void setUp() {
        workerDto = new WorkerDto();
        workerDto.setId(UUID.randomUUID());
        worker = new Worker();
        worker.setId(workerDto.getId());
    }

    @Test
    void createWorker() {
        Mockito.when(workerMapper.toWorker(workerDto)).thenReturn(worker);
        Mockito.when(workerRepository.save(any(Worker.class))).thenReturn(worker);

        UUID result = workerService.createWorker(workerDto);

        assertNotNull(result);
        assertEquals(workerDto.getId(), result);

        Mockito.verify(workerMapper, Mockito.times(1)).toWorker(workerDto);
        Mockito.verify(workerRepository, Mockito.times(1)).save(any(Worker.class));
    }

    @Test
    void getWorkerById() {
        UUID workerId = UUID.randomUUID();
        Mockito.when(workerRepository.findById(workerId)).thenReturn(Optional.of(worker));

        Optional<Worker> result = workerService.getWorkerById(workerId);

        assertTrue(result.isPresent());
        assertEquals(worker, result.get());
        Mockito.verify(workerRepository, Mockito.times(1)).findById(workerId);
    }

    @Test
    void getWorkerById_ShouldReturnEmptyOptional() {
        UUID workerId = UUID.randomUUID();
        Mockito.when(workerRepository.findById(workerId)).thenReturn(Optional.empty());

        Optional<Worker> result = workerService.getWorkerById(workerId);

        assertFalse(result.isPresent());
        Mockito.verify(workerRepository, Mockito.times(1)).findById(workerId);
    }

    @Test
    void updateWorker() {
        Mockito.when(workerRepository.existsById(workerDto.getId())).thenReturn(true);
        Mockito.when(workerMapper.toWorker(workerDto)).thenReturn(worker);
        Mockito.when(workerRepository.save(any(Worker.class))).thenReturn(worker);

        UUID result = workerService.updateWorker(workerDto);

        assertEquals(workerDto.getId(), result);
        Mockito.verify(workerRepository, Mockito.times(1)).existsById(workerDto.getId());
        Mockito.verify(workerRepository, Mockito.times(1)).save(any(Worker.class));
    }

    @Test
    void updateWorker_ShouldThrowException() {

        Mockito.when(workerRepository.existsById(workerDto.getId())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            workerService.updateWorker(workerDto);
        });

        assertEquals("Event not found with id " + workerDto.getId(), exception.getMessage());
        Mockito.verify(workerRepository, Mockito.times(1)).existsById(workerDto.getId());
    }

    @Test
    void deleteWorker() {
        UUID workerId = UUID.randomUUID();
        Mockito.when(workerRepository.findById(workerId)).thenReturn(Optional.of(worker));

        workerService.deleteWorker(workerId);

        Mockito.verify(workerRepository, Mockito.times(1)).findById(workerId);
        Mockito.verify(workerRepository, Mockito.times(1)).delete(worker);
    }

    @Test
    void deleteWorker_ShouldThrowException() {
        UUID workerId = UUID.randomUUID();
        Mockito.when(workerRepository.findById(workerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            workerService.deleteWorker(workerId);
        });

        assertEquals("Worker not found with id " + workerId, exception.getMessage());
        Mockito.verify(workerRepository, Mockito.times(1)).findById(workerId);
    }
}