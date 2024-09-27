package bugurt.backlog.web.Controller;

import bugurt.backlog.web.dto.WorkerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class WorkerControllerTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCRUD() throws Exception {
        WorkerDto workerDto = new WorkerDto();
        workerDto.setName("Vladimir");
        workerDto.setSurname("Babuin");
        workerDto.setPositional("Shestyorka");
        workerDto.setPhone("1234567890");
        workerDto.setEmail("test@example.com");
        workerDto.setBirthdate(LocalDate.of(2004, 1, 9));
        workerDto.setTelegram("@johndoe");
        workerDto.setDate_start_work(LocalDate.of(2024, 9, 1));
        workerDto.set_probation_period(true);

        String workerJson = objectMapper.writeValueAsString(workerDto);
        MvcResult createResult = mockMvc.perform(multipart("/api/v1/user")
                        .file(new MockMultipartFile("worker", "", MediaType.APPLICATION_JSON_VALUE, workerJson.getBytes())))
                .andExpect(status().isOk())
                .andReturn();

        String createdWorkerId = createResult.getResponse().getContentAsString();
        createdWorkerId = createdWorkerId.replace("\"", "");

        mockMvc.perform(get("/api/v1/user/" + createdWorkerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vladimir"));


        UUID uuid = UUID.fromString(createdWorkerId);
        workerDto.setName("Vladimir2.0");
        workerDto.setId(uuid);
        String newWorker = objectMapper.writeValueAsString(workerDto);

        mockMvc.perform(put("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newWorker))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/v1/user/" + createdWorkerId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/user/" + createdWorkerId))
                .andExpect(status().isNotFound());
    }
}