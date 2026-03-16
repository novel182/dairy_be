package com.alpine.dairy.inventoryManagementService;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alpine.dairy.orderRequestService.OrderRequest;

@Service
public class InventoryManager {
    private final InventoryRepository inventoryRepository;

    InventoryManager(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean canFulfill(OrderRequest orderRequest){
        boolean canFulfill = hasSufficientItem("mozzarella", orderRequest.getMozzarella())
            && hasSufficientItem("paneer", orderRequest.getPaneer())
            && hasSufficientItem("kanchan", orderRequest.getKanchan());
        return canFulfill;
    }

    private boolean hasSufficientItem(String productId, int amount){
        InventoryItem requestedItem = inventoryRepository.findById(productId).orElse(null);
        if(requestedItem == null){
            return false;
        }
        int availableQuantity = requestedItem.getTotalQuantity() - requestedItem.getQuantityTemporaryHold() - requestedItem.getQuantityPromised();
        if(availableQuantity < amount){
            return false;
        }
        return true;
    }

    /* Hold Requests */
    @Transactional
    public boolean addHoldQuantity(int mozzarella, int paneer, int kanchan){
        updateHoldQuantity("mozzarella", mozzarella);
        updateHoldQuantity("paneer", paneer);
        updateHoldQuantity("kanchan", kanchan);
        return true;
    }

    private void updateHoldQuantity(String productId, int quantity) {
        if (quantity == 0) return;
        inventoryRepository.findById(productId).ifPresent(item -> {
            item.setQuantityTemporaryHold(item.getQuantityTemporaryHold() + quantity);
            inventoryRepository.save(item);
        });
    }

    @Transactional
    public boolean holdRequest(OrderRequest orderRequest){
        return addHoldQuantity(orderRequest.getMozzarella(), orderRequest.getPaneer(), orderRequest.getKanchan());
    }

    @Transactional
    public boolean changeHoldRequest(OrderRequest oldRequest, OrderRequest newRequest){
        int mozzarellaDiff = newRequest.getMozzarella() - oldRequest.getMozzarella();
        int paneerDiff = newRequest.getPaneer() - oldRequest.getPaneer();
        int kanchanDiff = newRequest.getKanchan() - oldRequest.getKanchan();
        
        if(mozzarellaDiff == 0 && paneerDiff == 0 && kanchanDiff == 0){
            return false;
        }
        return addHoldQuantity(mozzarellaDiff, paneerDiff, kanchanDiff);
    }


    /* Fulfillment of Hold Requests */
    @Transactional
    public boolean fulfillHoldRequest(OrderRequest orderRequest){
    InventoryItem mozzarellaItem = getItemById("mozzarella");
        InventoryItem paneerItem = getItemById("paneer");
        InventoryItem kanchanItem = getItemById("kanchan");

        if(mozzarellaItem == null || paneerItem == null || kanchanItem == null){
            return false;
        }

        if(mozzarellaItem.getQuantityTemporaryHold() < orderRequest.getMozzarella()
            || paneerItem.getQuantityTemporaryHold() < orderRequest.getPaneer()
            || kanchanItem.getQuantityTemporaryHold() < orderRequest.getKanchan()){
            return false;
        }

        updateFulfillmentQuantities(mozzarellaItem, orderRequest.getMozzarella());
        updateFulfillmentQuantities(paneerItem, orderRequest.getPaneer());
        updateFulfillmentQuantities(kanchanItem, orderRequest.getKanchan());

        return true;
    }

    private void updateFulfillmentQuantities(InventoryItem item, int quantity) {
        if (quantity == 0) return;
        item.setQuantityPromised(item.getQuantityPromised() + quantity);
        item.setQuantityTemporaryHold(item.getQuantityTemporaryHold() - quantity);
        inventoryRepository.save(item);
    }


    /* Fulfillment of Promised Requests */
    @Transactional
    public boolean fulfillDeliveredRequest(OrderRequest orderRequest){
        InventoryItem mozzarellaItem = getItemById("mozzarella");
        InventoryItem paneerItem = getItemById("paneer");
        InventoryItem kanchanItem = getItemById("kanchan");

        if(mozzarellaItem == null || paneerItem == null || kanchanItem == null){
            return false;
        }

        if(mozzarellaItem.getQuantityPromised() < orderRequest.getMozzarella()
            || paneerItem.getQuantityPromised() < orderRequest.getPaneer()
            || kanchanItem.getQuantityPromised() < orderRequest.getKanchan()){
            return false;
        }

        updateDeliveryQuantities(mozzarellaItem, orderRequest.getMozzarella());
        updateDeliveryQuantities(paneerItem, orderRequest.getPaneer());
        updateDeliveryQuantities(kanchanItem, orderRequest.getKanchan());

        return true;
    }

    private void updateDeliveryQuantities(InventoryItem item, int quantity) {
        if (quantity == 0) return;
        item.setTotalQuantity(item.getTotalQuantity() - quantity);
        item.setQuantityPromised(item.getQuantityPromised() - quantity);
        inventoryRepository.save(item);
    }


    /* Basic operations */
    public List<InventoryItem> getAllItems(){
        return inventoryRepository.findAll();
    }

    public InventoryItem getItemById(String productId){
        return inventoryRepository.findById(productId).orElse(null);
    }

    public InventoryItem addProducedItem(String productId, int quantityProduced){
        InventoryItem existingItem = getItemById(productId);
        if (existingItem == null) {
            return null;
        }
        existingItem.setTotalQuantity(existingItem.getTotalQuantity() + quantityProduced);
        return inventoryRepository.save(existingItem);
    }
}
