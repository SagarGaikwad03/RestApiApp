package org.ebi.restapiapp.controller;

import org.ebi.restapiapp.exception.ResourceNotFoundException;
import org.ebi.restapiapp.model.Person;
import org.ebi.restapiapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/person")
    public List<Person> getAllPerson(){
        return personRepository.findAll();
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
        Person person = personRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employe not found with id:"+id));
        return ResponseEntity.ok().body(person);
    }

    @PostMapping("/person")
    public Person createPersonEntity(@Valid @RequestBody Person person){return personRepository.save(person);}

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePersonEntity(@PathVariable(value = "id") Long id,@Valid @RequestBody Person personDetails)
            throws ResourceNotFoundException{
        Person person = personRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employe not found with id:"+id));
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setAge(personDetails.getAge());
        person.setFavouriteColor(personDetails.getFavouriteColor());

        final Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok().body(updatedPerson);
    }

    @DeleteMapping("/person/{id}")
    public Map<String,Boolean> deletePerson(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
        Person person = personRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employe not found with id:"+id));
        personRepository.delete(person);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
