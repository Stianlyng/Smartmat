package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeProductAssoRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Service for management of a group fridge
 *
 * @author Anders Austlid & Birk
 * @version 1.2
 * @since 26.04.2023
 */
@AllArgsConstructor
@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final ProductService productService;

    private final FridgeProductAssoRepository fridgeProductAssoRepository;

    private final FridgeProductAssoService fridgeProductAssoService;

    private final GroupRepository groupRepository;

    /**
     * Gets the fridge of a group
     *
     * @param groupId the id of the group
     * @return the fridge of the group
     */
    public Optional<Fridge> getFridgeByGroupId(long groupId) {
        return fridgeRepository.findByGroupGroupId(groupId);
    }
    
    /**
     * Gets the fridge by its fridge id
     * 
     * @param fridgeId the id of the fridge
     * @return the fridge if it exists
     */
    public Optional<Fridge> getFridgeByFridgeId(long fridgeId) {
        return fridgeRepository.findById(fridgeId);
    }


    /**
     * Add a product to the fridge of a group
     *
     * @param groupId the id of the group
     *                group must exist
     * @param ean the ean of the product
     * @return true if the product was added
     */
    public Optional<Object> addProductToFridge(long groupId, long ean, int amount, int days) {
        Optional<Product> product = productService.getProductById(ean);
        Fridge fridge = fridgeRepository.findByGroupGroupId(groupId).orElseThrow(() -> new IllegalArgumentException("Fridge does not exist"));

        if (product.isPresent()) {
            Product productToAdd = product.get();
            FridgeProductAsso temp = FridgeProductAsso.builder().fridgeId(fridge).ean(productToAdd).amount(amount).daysToExpiration(days).build();
            if (fridge.getProducts().contains(temp)) {
                return Optional.empty();
            }
            return Optional.of(fridgeProductAssoService.createFridgeProductAsso(fridge, productToAdd,amount,days));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Remove a product from the fridge of a group
     *
     * @param groupId the id of the group
     *                group must exist
     * @return true if the product was removed
     */
    /*public boolean removeProductFromFridge( long FPId) {
        FridgeProductAsso fridgeProductAsso = fridgeProductAssoRepository.findById(FPId).get();
        Optional<Product> product = productService.getProductById(fridgeProductAsso.getEan().getEan());
        Fridge fridge = fridgeRepository.findByGroupGroupId(fridgeProductAsso.getFridgeId().getFridgeId()).orElseThrow(() -> new IllegalArgumentException("Fridge does not exist"));
        if (product.isPresent()) {
            Product productToRemove = product.get();
            if (!fridge.getProducts().contains(
                    new FridgeProductAsso(fridge, productToRemove,))) {
                return false;
            }
            FridgeProductAsso fridgeProductAsso1 = FridgeProductAsso.builder().id(FPId)..build()
            fridgeProductAssoService.deleteFridgeProductAsso(new FridgeProductAsso(fridge,
                    productToRemove, purchaseDate));

            return true;
        } else {
            return false;
        }
    }*/

    /**
     * Updates a fridge
     * @param fridge the fridge to update
     */
    public void updateFridge(Fridge fridge) {
        if (fridgeRepository.findById(fridge.getFridgeId()).isEmpty())
            return;
        fridgeRepository.save(fridge);
    }

    /**
     * Adds a group to a fridge
     * @param fridgeId the id of the fridge
     * @param groupId the id of the group
     */
    public void addGroupToFridge(long fridgeId, long groupId) {
        Optional<Fridge> fridge = fridgeRepository.findById(fridgeId);
        Optional<Group> group = groupRepository.findByGroupId(groupId);
        if (fridge.isPresent() && group.isPresent()) {
            fridge.get().setGroup(group.get());
            group.get().setFridge(fridge.get());
            groupRepository.save(group.get());
            fridgeRepository.save(fridge.get());
        }
    }
}
