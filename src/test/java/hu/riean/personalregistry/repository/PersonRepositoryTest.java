/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hu.riean.personalregistry.repository;

import hu.riean.personalregistry.DBConfig;
import hu.riean.personalregistry.Personalregistry;
import hu.riean.personalregistry.modell.Person;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author riean
 */
@SpringBootTest(classes = {Personalregistry.class, DBConfig.class})
public class PersonRepositoryTest {

    @Autowired
    PersonRepository rersonRepository;

    @Test
    public void testFindById() {
        System.out.println("findById");
        long id = 32L;
        Optional<Person> result = rersonRepository.findById(id);
        
        assertFalse(result.isEmpty(), "Need person entity (id="+id+") not found");
        assertEquals("Szabó", result.get().getLastName());
    }

}
