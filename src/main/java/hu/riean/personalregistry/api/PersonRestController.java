package hu.riean.personalregistry.api;

import hu.riean.personalregistry.modell.Person;
import hu.riean.personalregistry.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author riean
 */
@RestController
@RequestMapping("/api/person")
public class PersonRestController {

    @Autowired
    PersonRepository personRepository;
    private static final int PAGE_SIZE = 20;


    @GetMapping("/list/{page}/")
    public Page<Person> getList(@PathVariable int page) {
        return getList(page, "");
    }

    @GetMapping("/list/{page}/{fragment}")
    public Page<Person> getList(@PathVariable int page, @PathVariable String fragment) {
        return personRepository.findByLastNameContainingIgnoreCaseAndDeletedFalseOrderByLastNameAscFirstNameAsc(fragment, PageRequest.of(page, PAGE_SIZE));
    }

}
