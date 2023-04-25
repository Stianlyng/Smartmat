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
 * @version 1.0
 * @since 24.04.2023
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
        if(product.isPresent()) {
            fridge.getProducts().add(product.get());
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
    public boolean removeProduct(long groupId, long ean) {
        Optional<Product> product = productService.getProductById(ean);
        Fridge fridge = fridgeRepository.findByGroupId(groupId).orElseThrow(() -> new IllegalArgumentException("Fridge does not exist"));
        if (product.isPresent()) {
            fridge.getProducts().remove(product.get());
            fridgeRepository.save(fridge);
            return true;
        } else {
            return false;
        }
    }
}
