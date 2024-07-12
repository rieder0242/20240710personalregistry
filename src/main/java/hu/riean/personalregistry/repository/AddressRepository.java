package hu.riean.personalregistry.repository;

import hu.riean.personalregistry.modell.Address;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author riean
 */
public interface  AddressRepository extends CrudRepository<Address, Long> {
    
}
