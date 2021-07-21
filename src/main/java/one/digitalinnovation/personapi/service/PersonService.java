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
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
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
        verifyIfExists(id);


        personRepository.deleteById(id);
    }

    private Person verifyIfExists(long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public MessageResponseDTO updateById(Long id, Person person) throws PersonNotFoundException {
        verifyIfExists(id);

        Person updatedPerson = personRepository.save(person);

        return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }
}
