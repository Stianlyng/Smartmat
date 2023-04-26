package ntnu.idatt2016.v233.SmartMat.service.group;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.entity.fridgeProduct.FridgeProductAsso;
import ntnu.idatt2016.v233.SmartMat.entity.group.Fridge;
import ntnu.idatt2016.v233.SmartMat.entity.product.Product;
import ntnu.idatt2016.v233.SmartMat.repository.product.ProductRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeProductAssoRepository;
import ntnu.idatt2016.v233.SmartMat.repository.group.FridgeRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@AllArgsConstructor
public class FridgeProductAssoService {

    FridgeProductAssoRepository fridgeProductAssoRepository;

    FridgeRepository fridgeRepository;

    ProductRepository productRepository;

    /**
     * Creates a fridge product association
     * the FridgeProductAssosiation object needs to be added to the fridge and product
     * @param fridge the fridge to add the product to
     * @param product the product to add to the fridge
     * @param purchaseDate the date the product was purchased
     * @return the fridge product association
     */
    public FridgeProductAsso createFridgeProductAsso(Fridge fridge, Product product, Date purchaseDate) {
        FridgeProductAsso temp = new FridgeProductAsso();
        temp.setFridgeId(fridge);
        temp.setEan(product);
        temp.setPurchaseDate(purchaseDate);
        fridgeProductAssoRepository.save(temp);

        fridge.addProduct(temp);
        product.addFridge(temp);

        fridgeRepository.save(fridge);

        productRepository.save(product);

        return temp;
    }

    /**
     * Deletes a fridge product association
     * @param fridgeProductAsso the fridge product association to delete
     */
    public boolean deleteFridgeProductAsso(FridgeProductAsso fridgeProductAsso) {
        fridgeProductAsso.getFridgeId().getProducts().remove(fridgeProductAsso);
        fridgeProductAsso.getEan().getFridges().remove(fridgeProductAsso);
        fridgeProductAssoRepository.delete(fridgeProductAsso);

        fridgeRepository.save(fridgeProductAsso.getFridgeId());

        productRepository.save(fridgeProductAsso.getEan());

        return true;
    }
}
