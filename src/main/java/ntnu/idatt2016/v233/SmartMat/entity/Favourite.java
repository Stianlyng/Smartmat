package ntnu.idatt2016.v233.SmartMat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "favorite")
@Data
public class Favourite {

    @Id
    @Column(name = "recipe_id")
    long recipeId;

    @Column(name = "username")
    String username;
}
