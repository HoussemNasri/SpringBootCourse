package tech.houssemnasri.clubservice.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.houssemnasri.clubservice.entities.Club;
import tech.houssemnasri.clubservice.repositories.ClubRepository;

@RestController
public class ClubController {

  private final ClubRepository clubRepository;

  @Autowired
  public ClubController(final ClubRepository clubRepository) {
    this.clubRepository = clubRepository;
  }

  @GetMapping("/clubs")
  public ResponseEntity<List<Club>> getAllClubs() {
    try {
      List<Club> allClubs = clubRepository.findAll();
      if (allClubs.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(allClubs);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/clubs")
  public ResponseEntity<Club> addClub(@RequestBody Club toCreateClub) {
    try {
      Club createdClub = clubRepository.save(toCreateClub);
      return new ResponseEntity<>(createdClub, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PutMapping("/clubs/{id}")
  public ResponseEntity<Club> updateClub(@PathVariable Long id, @RequestBody Club updatedClub) {
    Optional<Club> toUpdateClub = clubRepository.findById(id);
    if (toUpdateClub.isPresent()) {
      Club toUpdateClubData = toUpdateClub.get();
      toUpdateClubData.setClubName(Optional.ofNullable(updatedClub.getClubName()).orElse(""));
      toUpdateClubData.setPresidentName(
          Optional.ofNullable(updatedClub.getPresidentName()).orElse(""));
      return ResponseEntity.ok(clubRepository.save(toUpdateClubData));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/clubs/{id}")
  public ResponseEntity<Club> deleteClub(@PathVariable Long id) {
    try {
      Optional<Club> club = clubRepository.findById(id);
      if (club.isEmpty()) {
        return ResponseEntity.notFound().build();
      } else {
        clubRepository.deleteById(id);
        return ResponseEntity.noContent().build();
      }
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/clubs/filter")
  public ResponseEntity<List<Club>> filterClubs(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String presidentName) {
    List<Club> allClubs = clubRepository.findAll();
    List<Club> matchedClubs =
        allClubs.stream()
            .filter(
                c ->
                    (name == null || c.getClubName().equals(name))
                        && (presidentName == null || c.getPresidentName().equals(presidentName)))
            .collect(Collectors.toList());

    if (matchedClubs.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(matchedClubs);
  }
}
