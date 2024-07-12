package hu.riean.personalregistry.repository;

import hu.riean.personalregistry.modell.Contact;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author riean
 */
public interface  ContactRepository extends CrudRepository<Contact, Long> {
    
}
