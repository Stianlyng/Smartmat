package ntnu.idatt2016.v233.SmartMat.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Allergy is an entity class representing an allergy
 * 
 * @author Stian Lyng and Anders
 * @version 1.3
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "product_allergy",
            joinColumns = @JoinColumn(name = "allergy_name"),
            inverseJoinColumns = @JoinColumn(name = "ean") )
    @JsonIgnore
    private List<Product> products;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_allergy",
            joinColumns = @JoinColumn(name = "allergy_name"),
            inverseJoinColumns = @JoinColumn(name = "username") )
    @JsonIgnore
    private List<User> users;

    /**
     * adds a user to the allergy
     * @param tempuser adds a user to the list of users with this allergy
     */
    public void addUser(User tempuser) {
        if (users == null)
            users = new ArrayList<>();

        users.add(tempuser);

    }

    /**
     * adds a product to the allergy
     * @param product adds a product to the list of products with this allergy
     */
    public void addProduct(Product product) {
        if (products == null)
            products = new ArrayList<>();

        if(!products.contains(product))
            products.add(product);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Allergy allergy)) return false;
        return getName().equals(allergy.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}