package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for management of a group fridge
 *
 * @author Anders Austlid
 * @version 1.0.1
 * @since 25.04.2023
 */
@AllArgsConstructor
@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final ProductService productService;

    /**
     * Gets the fridge of a group
     *
     * @param groupId the id of the group
     * @return the fridge of the group
     */
    public Optional<Fridge> getFridgeByGroupId(long groupId) {
        return fridgeRepository.findByGroupId(groupId);
    }

    /**
     * Add a product to the fridge of a group
     *
     * @param groupId the id of the group
     *                group must exist
     * @param ean the ean of the product
     * @return true if the product was added
     */
    public boolean addProductToFridge(long groupId, long ean) {
        Optional<Product> product = productService.getProductById(ean);
        Fridge fridge = fridgeRepository.findByGroupId(groupId).orElseThrow(() -> new IllegalArgumentException("Fridge does not exist"));

        if (product.isPresent()) {
            Product productToAdd = product.get();
            if (fridge.getProducts().contains(productToAdd)) {
                return false;
            }
            fridge.getProducts().add(productToAdd);
            fridgeRepository.save(fridge);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a product from the fridge of a group
     *
     * @param groupId the id of the group
     *                group must exist
     * @param ean the ean of the product
     * @return true if the product was removed
     */
    public boolean removeProductFromFridge(long groupId, long ean) {
        Optional<Product> product = productService.getProductById(ean);
        Fridge fridge = fridgeRepository.findByGroupId(groupId).orElseThrow(() -> new IllegalArgumentException("Fridge does not exist"));

        if (product.isPresent()) {
            Product productToRemove = product.get();
            if (!fridge.getProducts().contains(productToRemove)) {
                return false;
            }
            fridge.getProducts().remove(productToRemove);
            fridgeRepository.save(fridge);
            return true;
        } else {
            return false;
        }
    }
}
