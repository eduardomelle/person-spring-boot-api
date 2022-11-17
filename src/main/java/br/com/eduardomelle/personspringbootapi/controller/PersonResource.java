package br.com.eduardomelle.personspringbootapi.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardomelle.dto.PersonSaveRequestDTO;
import br.com.eduardomelle.dto.PersonSaveResponseDTO;
import br.com.eduardomelle.dto.PersonUpdateRequestDTO;
import br.com.eduardomelle.dto.PersonUpdateResponseDTO;
import br.com.eduardomelle.personspringbootapi.model.Person;
import br.com.eduardomelle.personspringbootapi.repository.PersonRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/people")
public class PersonResource {

  @Autowired
  private PersonRepository personRepository;

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

  @GetMapping
  public ResponseEntity<Object> findAll() {
    try {
      return ResponseEntity.ok(this.personRepository.findAll());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
    try {
      Optional<Person> optional = this.personRepository.findById(id);
      if (!optional.isPresent()) {
        return ResponseEntity.noContent().build();
      }

      return ResponseEntity.ok(optional.get());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable("id") Long id,
      @RequestBody PersonUpdateRequestDTO personUpdateRequestDTO) {
    try {
      Optional<Person> optional = this.personRepository.findById(id);
      if (!optional.isPresent()) {
        return ResponseEntity.noContent().build();
      }

      Person person = optional.get();
      person.setName(personUpdateRequestDTO.getName());
      person.setEmail(personUpdateRequestDTO.getEmail());

      Person p = this.personRepository.save(person);

      PersonUpdateResponseDTO personUpdateResponseDTO = new PersonUpdateResponseDTO();
      personUpdateResponseDTO.setId(p.getId());
      return ResponseEntity.ok(personUpdateResponseDTO);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
    try {
      Optional<Person> optional = this.personRepository.findById(id);
      if (!optional.isPresent()) {
        return ResponseEntity.noContent().build();
      }

      this.personRepository.delete(optional.get());

      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
