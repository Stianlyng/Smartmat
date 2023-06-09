package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.dto.request.product.FridgeProductRequest;
import ntnu.idatt2016.v233.SmartMat.entity.group.Waste;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.group.Group;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.group.GroupRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.WasteRepository;
import ntnu.idatt2016.v233.SmartMat.repository.product.FridgeProductAssoRepo;
import ntnu.idatt2016.v233.SmartMat.service.product.ProductService;

import ntnu.idatt2016.v233.SmartMat.util.GroupUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Service for management of a group fridge
 *
 * @author Anders Austlid & Birk
 * @version 2
 */
@AllArgsConstructor
@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final ProductService productService;

    private final GroupRepository groupRepository;

    private final WasteRepository wasteRepository;

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
    public Optional<Product> addProductToFridge(FridgeProductRequest fridgeProductRequest) {
        Optional<Product> product = productService.getProductById(fridgeProductRequest.ean());
        Optional<Fridge> fridge = fridgeRepository.findByGroupGroupId(fridgeProductRequest.groupId());
        double price = 10.0;
        if(fridgeProductRequest.price() != 0.0){
            price = fridgeProductRequest.price();
        }

        if(product.isEmpty() || fridge.isEmpty()) return Optional.empty();

        fridge.get().addProduct(FridgeProductAsso.builder()
                        .fridgeId(fridge.get())
                        .ean(product.get())
                        .amount(fridgeProductRequest.amount())
                        .daysToExpiration(fridgeProductRequest.days())
                        .purchaseDate(java.sql.Date.valueOf(LocalDate.now()))
                        .buyPrice(price)
                .build());

        fridgeRepository.save(fridge.get());
        return product;

    }

    /**
     * Updates a product in the fridge of a group
     * @param request the fridge product request
     * @return the product that was added to the fridge
     */
    public Optional<FridgeProductAsso> updateProductInFridge(FridgeProductRequest request) {
        Optional<FridgeProductAsso> fridgeProductAsso = fridgeProductAssoRepo.findById(request.fridgeProductId());
        if (fridgeProductAsso.isEmpty()) return Optional.empty();

        fridgeProductAsso.get()
                                .setAmount(request.amount());

        fridgeProductAsso.get()
                                .setDaysToExpiration(request.days());
        


        return Optional.of(fridgeProductAssoRepo.save(fridgeProductAsso.get()));
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



        fridgeProductAssoRepo.delete(fridgeProductAsso.get());

        return true;
    }



    /**
     * Delete an amount from a fridge product
     * @param fridgeProductId the id of the fridge product
     * @param amount the amount to delete
     * @return an optional containing the fridge product if it exists
     */
    public Optional<FridgeProductAsso> deleteAmountFromFridge(long fridgeProductId, double amount) {
        Optional<FridgeProductAsso> fridgeProductAsso = fridgeProductAssoRepo.findAllById(fridgeProductId);
        if(fridgeProductAsso.isEmpty()) return Optional.empty();
        FridgeProductAsso fridgeProductAsso1 = fridgeProductAsso.get();
        if(amount < fridgeProductAsso1.getAmount() ){
            fridgeProductAsso1.setAmount(fridgeProductAsso1.getAmount() - amount);
            return Optional.of(fridgeProductAssoRepo.save(fridgeProductAsso1));
        } else {
            Group group = fridgeProductAsso1.getFridgeId().getGroup();
            group.setPoints(group.getPoints() + 1);
            group.setLevel(GroupUtil.getLevel(group.getPoints()));
            groupRepository.save(group);
            fridgeProductAssoRepo.delete(fridgeProductAsso.get());
            return Optional.empty();
        }
    }

    /**
     * Deletes a product from the fridge and saves a waste object representing the discarded product.
     * @param fridgeProductId the ID of the fridge product association to delete
     * @return an Optional containing the saved waste object, or an empty Optional if the fridge product association with the given ID is not found
     */
    public Optional<Waste> wasteProductFromFridge(long fridgeProductId){
        Optional<FridgeProductAsso> fridgeProductAsso = fridgeProductAssoRepo.findById(fridgeProductId);
        if(fridgeProductAsso.isEmpty()) return Optional.empty();
        FridgeProductAsso fridgeProductAsso1 = fridgeProductAsso.get();
        fridgeProductAssoRepo.delete(fridgeProductAsso1);
        Group group = fridgeProductAsso1.getFridgeId().getGroup();
        if(group.getPoints() > 10.0){
            group.setPoints(group.getPoints() - 1);
            group.setLevel(GroupUtil.getLevel(group.getPoints()));
        }
        groupRepository.save(group);
        return Optional.of(wasteRepository.save(Waste.builder()
                        .buyPrice(fridgeProductAsso1.getBuyPrice())
                .amount(fridgeProductAsso1.getAmount())
                .unit(fridgeProductAsso1.getEan().getUnit())
                .ean(fridgeProductAsso1.getEan())
                .groupId(fridgeProductAsso1.getFridgeId().getGroup())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build()));
    }


    /**
     * Get all the fridge products of a group
     * @param username the username of the user
     * @param fridgeProductId the id of the fridge product
     * @return true if the user is in the group of the fridge product
     */
    public boolean isUserInGroupWithFridgeProduct(String username, long fridgeProductId) {
        Optional<Fridge> fridge = fridgeProductAssoRepo.findById(fridgeProductId)
                .map(FridgeProductAsso::getFridgeId);
        return fridge.map(value -> value.getGroup().getUser().stream()
                .anyMatch(user -> user.getUser().getUsername().equals(username))).orElse(false);
    }

    /**
     * check if user has accsess to fridge
     * @param username the username of the user
     * @param fridgeId the id of the fridge
     * @return true if the user is in the group of the fridge
     */
    public boolean isUserInFridge(String username, long fridgeId) {
        Optional<Fridge> fridge = fridgeRepository.findById(fridgeId);
        return fridge.map(value -> value.getGroup().getUser().stream()
                .anyMatch(user -> user.getUser().getUsername().equals(username))).orElse(false);
    }


    /**
     * Get the group id of a fridge product
     * @param fridgeProductId the id of the fridge product
     * @return the id of the group of the fridge product
     */
    public long getGroupIdFromFridgeProuctId(long fridgeProductId){
        return fridgeProductAssoRepo.findById(fridgeProductId)
                .map(fridgeProductAsso -> fridgeProductAsso.getFridgeId().getGroup().getGroupId()).orElse(0L);

    }
}
