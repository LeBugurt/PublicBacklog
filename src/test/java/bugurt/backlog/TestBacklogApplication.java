package bugurt.backlog;

import org.springframework.boot.SpringApplication;

public class TestBacklogApplication {

    public static void main(String[] args) {
        SpringApplication.from(BacklogApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
