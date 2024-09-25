package bugurt.backlog.web.Controller;

import bugurt.backlog.exception.ResourceNotFoundException;
import bugurt.backlog.model.Worker;
import bugurt.backlog.service.WorkerService;
import bugurt.backlog.web.dto.WorkerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class WorkerController {
    private final WorkerService workerService;

    private final String uploadDir = "src/main/resources/img";

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping
    public UUID createWorker(@RequestPart("worker") WorkerDto workerDto,
                             @RequestPart("photo") MultipartFile photo) {

        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileName = photo.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, photo.getBytes());

            workerDto.setPhoto(filePath.toString());

            return workerService.createWorker(workerDto);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create worker: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable UUID id) {
        Worker worker = workerService.getWorkerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        return ResponseEntity.ok(worker);
    }

    @PutMapping
    public UUID updateWorker(@RequestBody WorkerDto workerDto) {
        return workerService.updateWorker(workerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable UUID id) {
        workerService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }
}
