package tech.houssemnasri.clubservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.houssemnasri.clubservice.entities.Club;

public interface ClubRepository extends JpaRepository< Club, Long> {
}
