package br.com.eduardomelle.personspringbootapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardomelle.personspringbootapi.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
