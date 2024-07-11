package hu.riean.personalregistry.modell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    Person person;
    boolean deleted;
    @Enumerated(EnumType.STRING)
    AddressKind kind;
    String zipCode;
    String settlement;
    String address;
}
