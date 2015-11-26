package task.service.lvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import task.service.lvs.repository.BaseRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {BaseRepository.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
