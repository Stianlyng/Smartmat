package ntnu.idatt2016.v233.SmartMat.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Allergy is an entity class representing an allergy
 * 
 * @author Stian Lyng and Anders
 * @version 1.2
 * @since 19.04.2023
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "allergy")
@Data
public class Allergy{

    @Id
    @Column(name = "allergy_name")
    String name;
    @Column(name = "allergy_description")
    String description;

    @ManyToMany(mappedBy = "allergies")
    @JsonIgnoreProperties({"allergies"})
    private List<Product> products;
}