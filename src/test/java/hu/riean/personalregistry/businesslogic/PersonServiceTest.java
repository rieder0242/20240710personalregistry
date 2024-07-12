/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hu.riean.personalregistry.businesslogic;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.riean.personalregistry.Personalregistry;
import hu.riean.personalregistry.modell.Address;
import hu.riean.personalregistry.modell.Contact;
import hu.riean.personalregistry.modell.Person;
import hu.riean.personalregistry.repository.AddressRepository;
import hu.riean.personalregistry.repository.PersonRepository;
import jakarta.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author riean
 */
@SpringBootTest(classes = {Personalregistry.class})
public class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @ParameterizedTest
    @Transactional
    @MethodSource("provideTestCreate")
    public void testCreate(Person goalPerson) {
        System.out.println("create");

        Person result = personService.create(goalPerson);
        Person dbPerson = personRepository.findById(result.getId()).orElseThrow(() -> {
            throw new AssertionFailedError("Not found persisted entity");
        });
        assetPersonEqual(dbPerson, goalPerson);
        personService.delete(result.getId());
    }

    static List<Person> provideTestCreate() throws IOException {
        InputStream stream = new FileInputStream("src/test/resources/presonlist.json");
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Person.class);
        return mapper.readValue(stream, type);

    }

    static Person provideEtalonPerson() throws IOException {
        InputStream stream = new FileInputStream("src/test/resources/etalonpreson.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stream, Person.class);

    }

    @ParameterizedTest
    @Transactional
    @MethodSource("provideTestCreate")
    public void testUpdate(Person goalPerson) throws IOException {
        System.out.println("update");

        Person result = personService.create(provideEtalonPerson());
        personService.update(result.getId(), goalPerson);
        Person dbPerson = personRepository.findById(result.getId()).orElseThrow(() -> {
            throw new AssertionFailedError("Not found persisted entity");
        });
        assetPersonEqual(dbPerson, goalPerson);
        personService.delete(result.getId());
    }

    private void assetPersonEqual(Person dbPerson, Person goalPerson) {
        assertEquals(dbPerson.getFirstName(), goalPerson.getFirstName());
        assertEquals(dbPerson.getLastName(), goalPerson.getLastName());
        assertAddressEqual(dbPerson.getConstantAddress(), goalPerson.getConstantAddress());
        assertAddressEqual(dbPerson.getTemporaryAddress(), goalPerson.getTemporaryAddress());
    }

    private void assertAddressEqual(Address dbAddress, Address goalAddress) {
        if (dbAddress == null && goalAddress == null) {
            return;
        }
        if (dbAddress == null || goalAddress == null) {
            throw new AssertionFailedError("assimetric null");
        }
        assertEquals(dbAddress.getZipCode(), goalAddress.getZipCode());
        assertEquals(dbAddress.getSettlement(), goalAddress.getSettlement());
        assertEquals(dbAddress.getAddress(), goalAddress.getAddress());

        assertContactListEqual(dbAddress.getContacts(), goalAddress.getContacts());

    }

    private void assertContactListEqual(List<Contact> dbContacts, List<Contact> goalContacts) {
        assertEquals(dbContacts.size(), goalContacts.size());
        for (Contact goalContact : goalContacts) {
            if (!foundInContactList(goalContact, dbContacts)) {
                throw new AssertionFailedError("Not found matching contact");
            }
        }

    }

    private boolean foundInContactList(Contact goalContact, List<Contact> contacts) {
        for (Contact candidateContact : contacts) {
            if (candidateContact.getKind().equals(goalContact.getKind())
                    && candidateContact.getValue().equals(goalContact.getValue())) {
                return true;
            }
        }
        return false;
    }

}
