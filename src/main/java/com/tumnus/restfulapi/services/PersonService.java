package com.tumnus.restfulapi.services;

import com.tumnus.restfulapi.exceptions.ResourceNotFoundException;
import com.tumnus.restfulapi.model.Person;
import com.tumnus.restfulapi.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());


    public List<Person> findAll() {
        logger.info("Finding all persons!");
        return personRepository.findAll();
    }

    public Person findById(long id) {

        logger.info("Finding one person!");
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    }

    public Person create(Person person) {
        logger.info("Creating one person!");
        personRepository.save(person);
        return person;
    }

    public Person update(Person person) {
        logger.info("Updating one person!");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        Person entity = personRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        personRepository.delete(entity);
    }


}
