package hu.riean.personalregistry.api;

import hu.riean.personalregistry.businesslogic.PersonService;
import hu.riean.personalregistry.modell.Person;
import hu.riean.personalregistry.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author riean
 */
@RestController
@RequestMapping("/api/person")
public class PersonRestController {

    private static final int PAGE_SIZE = 20;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonService personService;

    @GetMapping("/list/{page}/")
    public Page<Person> getList(@PathVariable int page) {
        return getList(page, "");
    }

    @GetMapping("/list/{page}/{fragment}")
    public Page<Person> getList(@PathVariable int page, @PathVariable String fragment) {
        return personRepository.findByLastNameContainingIgnoreCaseOrderByLastNameAscFirstNameAsc(fragment, PageRequest.of(page, PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public Person getOne(@PathVariable long id) {
        return personRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person not found");
        });
    }

    @PostMapping("")
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable long id, @RequestBody Person person) {
        return personService.update(id, person);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
         personService.delete(id);
    }

}
