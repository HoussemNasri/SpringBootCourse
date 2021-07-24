package tech.houssemnasri.clubservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.houssemnasri.clubservice.entities.Club;
import tech.houssemnasri.clubservice.repositories.ClubRepository;

@RestController
@SpringBootApplication
public class ClubServiceApplication implements CommandLineRunner {
    @Autowired private ClubRepository clubRepository;

    public static void main(String[] args) {
        SpringApplication.run(ClubServiceApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name", defaultValue = "World") String name) {
        return String.format("<h1> Hello %s </h1>", name);
    }

    @Override
    public void run(String... args) {
        // Adding dummy data to the database for testing
        clubRepository.save(new Club(null, "Fsb", "Houssem"));
        clubRepository.save(new Club(null, "Art of code", "Karim"));
        clubRepository.save(new Club(null, "Microsoft", "Mostafa"));
    }
}
