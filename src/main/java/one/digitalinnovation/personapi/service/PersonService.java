package one.digitalinnovation.personapi.service;


import one.digitalinnovation.personapi.controller.PersonController;
import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    public PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public MessageResponseDTO createPerson(Person person){
        Person savedPerson = personRepository.save(person);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + savedPerson.getId())
                .build();
    }


    public List<Person> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople;
    }

    public Person findById(long id) throws PersonNotFoundException {


        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isEmpty()){
            throw new PersonNotFoundException(id);
        }
        return optionalPerson.get();
    }

    public void delete(long id) throws PersonNotFoundException {
        personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));


        personRepository.deleteById(id);
    }
}
