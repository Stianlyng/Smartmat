package ntnu.idatt2016.v233.SmartMat.model;

/**
 * Recipe is a record class representing a recipe in the system.
 *
 * @author Anders
 * @version 1.0
 * @since 04.04.2023
 *
 * @param id
 * @param name
 * @param description
 */
public record Recipe(long id, String name, String description) {
}
