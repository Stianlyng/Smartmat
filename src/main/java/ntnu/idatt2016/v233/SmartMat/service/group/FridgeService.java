package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.product.FridgeProductAssoRepo;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;

import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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

    private final GroupRepository groupRepository;

    private final FridgeProductAssoRepo fridgeProductAssoRepo;

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
     * Adds a product to the fridge of a group
     * @param fridgeProductRequest the fridge product request
     * @return the product that was added to the fridge
     */
    public Optional<Object> addProductToFridge(FridgeProductRequest fridgeProductRequest) {
        Optional<Product> product = productService.getProductById(fridgeProductRequest.ean());
        Optional<Fridge> fridge = fridgeRepository.findByGroupGroupId(fridgeProductRequest.groupId());

        if(product.isEmpty() || fridge.isEmpty()) return Optional.empty();

        fridge.get().addProduct(FridgeProductAsso.builder()
                        .fridgeId(fridge.get())
                        .ean(product.get())
                        .amount(fridgeProductRequest.amount())
                        .daysToExpiration(fridgeProductRequest.days())
                        .purchaseDate(java.sql.Date.valueOf(LocalDate.now()))
                .build());

        fridgeRepository.save(fridge.get());
        return Optional.of(product);

    }
    
    public Optional<Object> updateProductInFridge(FridgeProductRequest request) {
        Optional<FridgeProductAsso> fridgeProductAsso = fridgeProductAssoRepo.findById(request.fridgeProductId());
        if (fridgeProductAsso.isEmpty()) return Optional.empty();
        
        Integer amount = request.amount();
        Integer days = request.days();

        if (amount != null) fridgeProductAsso.get()
                                .setAmount(request.amount());

        if (days != null) fridgeProductAsso.get()
                                .setDaysToExpiration(request.days());
        
        fridgeProductAssoRepo.save(fridgeProductAsso.get());

        return Optional.of(fridgeProductAsso);
    }


    /**
     * Remove a product from the fridge of a group
     *
     * @param FPId the id of the fridge product association
     * @return true if the product was removed
     */
    public boolean removeProductFromFridge(long FPId) {
        Optional<FridgeProductAsso> fridgeProductAsso = fridgeProductAssoRepo.findById(FPId);

        if (fridgeProductAsso.isEmpty()) return false;

        Fridge fridge = fridgeRepository.findById(fridgeProductAsso.get().getFridgeId().getFridgeId())
                .get();


        fridgeProductAssoRepo.delete(fridgeProductAsso.get());

        return true;
    }

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

    /**
     * Delete an amount from a fridge product
     * @param fridgeProductId the id of the fridge product
     * @param amount the amount to delete
     * @return an optional containing the fridge product if it exists
     */
    public Optional<Object> deleteAmountFromFridge(long fridgeProductId, int amount) {
        if(amount < 0 ){
            System.out.println("Given amount " + amount + " < " + " Stored in db");
        } else {
            System.out.println("Given amount " + amount + " > " + " Stored in db");
        }
        return Optional.empty();
    }

    /**
     * Delete all products in a fridge
     * @param fridgeId the id of the fridge
     * @return true if the fridge was deleted
     *
    public boolean deleteAllProductsInFridge(long fridgeId) {
        Optional<Fridge> fridge = fridgeRepository.findById(fridgeId);
        if(fridge.isEmpty()) return false;
        fridgeProductAssoService.deleteAllFridgeProducts(fridgeId);
        return true;
    }
    */
}
