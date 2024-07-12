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
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    Address address;
    @Enumerated(EnumType.STRING)
    ContactKind kind;
    String value;
}
