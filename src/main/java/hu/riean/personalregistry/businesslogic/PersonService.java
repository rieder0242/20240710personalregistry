package hu.riean.personalregistry.businesslogic;

import hu.riean.personalregistry.modell.Address;
import hu.riean.personalregistry.modell.Contact;
import hu.riean.personalregistry.modell.Person;
import hu.riean.personalregistry.repository.AddressRepository;
import hu.riean.personalregistry.repository.ContactRepository;
import hu.riean.personalregistry.repository.PersonRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author riean
 */
@Service
@Transactional
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ContactRepository contactRepository;

    public Person create(Person person) {
        Person dbPerson = new Person();
        savePersonFields(dbPerson, person);
        return dbPerson;
    }

    public Person update(long id, Person person) {
        Person dbPerson = personRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person not found");
        });
        savePersonFields(dbPerson, person);
        return dbPerson;

    }

    void savePersonFields(Person dbPerson, Person person) {
        dbPerson.setFirstName(person.getFirstName());
        dbPerson.setLastName(person.getLastName());
        dbPerson.setConstantAddress(saveAddress(dbPerson.getConstantAddress(), person.getConstantAddress()));
        dbPerson.setTemporaryAddress(saveAddress(dbPerson.getTemporaryAddress(), person.getTemporaryAddress()));
        personRepository.save(dbPerson);
    }

    Address saveAddress(Address dbAddress, Address address) {
        if (address == null) {
            if (dbAddress != null) {
                contactRepository.deleteAll(dbAddress.getContacts());
                addressRepository.delete(dbAddress);
            }
            return null;
        } else {
            if (dbAddress == null) {
                dbAddress = new Address();
                dbAddress.setContacts(new ArrayList<>());
            }
        }
        dbAddress.setZipCode(address.getZipCode());
        dbAddress.setSettlement(address.getSettlement());
        dbAddress.setAddress(address.getAddress());

        addressRepository.save(dbAddress);
        saveContactList(dbAddress, address);
        return dbAddress;

    }

    private void saveContactList(Address dbAddress, Address address) {
        Map<Long, Contact> dbContactMap = dbAddress.getContacts().stream().collect(Collectors.toMap(Contact::getId, Function.identity()));
        for (Contact contact : address.getContacts()) {
            Contact dbContact;
            if (contact.getId() == null) {
                dbContact = new Contact();
                //dbAddress.getContacts().add(dbContact);
                dbContact.setAddress(dbAddress);
                dbAddress.getContacts().add(dbContact);
            } else {
                dbContactMap.remove(contact.getId());
                dbContact = contactRepository.findById(contact.getId()).orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                });
            }
            dbContact.setValue(contact.getValue());
            dbContact.setKind(contact.getKind());
            contactRepository.save(dbContact);
        }
        for (Contact dbContact : dbContactMap.values()) {
            dbAddress.getContacts().remove(dbContact);
            contactRepository.delete(dbContact);
        }
    }

    public void delete(long id) {
        Person dbPerson = personRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person not found");
        });
        dbPerson.setConstantAddress(saveAddress(dbPerson.getConstantAddress(), null));
        dbPerson.setTemporaryAddress(saveAddress(dbPerson.getTemporaryAddress(), null));
        personRepository.delete(dbPerson);
    }
}
