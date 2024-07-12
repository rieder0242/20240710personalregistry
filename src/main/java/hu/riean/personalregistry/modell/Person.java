package hu.riean.personalregistry.modell;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author riean
 */
@Entity
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    
    @ManyToOne
    @JoinColumn(name = "constant_address_id", nullable = true)
    Address constantAddress;

    @ManyToOne
    @JoinColumn(name = "temporary_address_id", nullable = true)
    Address temporaryAddress;

}
