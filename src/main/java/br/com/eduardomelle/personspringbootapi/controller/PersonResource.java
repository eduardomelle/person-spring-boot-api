package br.com.eduardomelle.personspringbootapi.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardomelle.dto.PersonSaveRequestDTO;
import br.com.eduardomelle.dto.PersonSaveResponseDTO;
import br.com.eduardomelle.personspringbootapi.model.Person;
import br.com.eduardomelle.personspringbootapi.repository.PersonRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/people")
public class PersonResource {

  @Autowired
  private PersonRepository personRepository;

  @GetMapping
  public ResponseEntity<Object> findAll() {
    try {
      return ResponseEntity.ok(this.personRepository.findAll());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody PersonSaveRequestDTO personSaveRequestDTO) {
    try {
      System.out.print("personSaveRequestDTO => " + personSaveRequestDTO);

      Person person = new Person();
      BeanUtils.copyProperties(personSaveRequestDTO, person);

      Person p = this.personRepository.save(person);
      System.out.print("person => " + person);

      PersonSaveResponseDTO personSaveResponseDTO = new PersonSaveResponseDTO();
      BeanUtils.copyProperties(p, personSaveResponseDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(personSaveResponseDTO);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
