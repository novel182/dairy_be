package com.alpine.dairy.inventoryManagementService;

import java.util.List;
import org.springframework.stereotype.Service;

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
        int availableQuantity = requestedItem.getTotalQuantity() - requestedItem.getQuantityTemporaryHold();
        if(availableQuantity < amount){
            return false;
        }
        return true;
    }

    public boolean fulfilHoldRequest(OrderRequest orderRequest){
        List<InventoryItem> items = getAllItems();
        InventoryItem mozzarellaItem = items.get(0);
        InventoryItem paneerItem = items.get(1);
        InventoryItem kanchanItem = items.get(2);

        if(mozzarellaItem == null || paneerItem == null || kanchanItem == null){
            return false;
        }

        if(mozzarellaItem.getQuantityTemporaryHold() < orderRequest.getMozzarella()
            || paneerItem.getQuantityTemporaryHold() < orderRequest.getPaneer()
            || kanchanItem.getQuantityTemporaryHold() < orderRequest.getKanchan()){
            return false;
        }

        mozzarellaItem.setQuantityPromised(mozzarellaItem.getQuantityPromised() + orderRequest.getMozzarella());
        mozzarellaItem.setQuantityTemporaryHold(mozzarellaItem.getQuantityTemporaryHold() - orderRequest.getMozzarella());
        inventoryRepository.save(mozzarellaItem);

        paneerItem.setQuantityPromised(paneerItem.getQuantityPromised() + orderRequest.getPaneer());
        paneerItem.setQuantityTemporaryHold(paneerItem.getQuantityTemporaryHold() - orderRequest.getPaneer());
        inventoryRepository.save(paneerItem);

        kanchanItem.setQuantityPromised(kanchanItem.getQuantityPromised() + orderRequest.getKanchan());
        kanchanItem.setQuantityTemporaryHold(kanchanItem.getQuantityTemporaryHold() - orderRequest.getKanchan());
        inventoryRepository.save(kanchanItem);

        return true;
    }

    public boolean deliveredPromisedOrder(OrderRequest orderRequest){
        List<InventoryItem> items = getAllItems();
        InventoryItem mozzarellaItem = items.get(0);
        InventoryItem paneerItem = items.get(1);
        InventoryItem kanchanItem = items.get(2);

        if(mozzarellaItem == null || paneerItem == null || kanchanItem == null){
            return false;
        }

        if(mozzarellaItem.getQuantityPromised() < orderRequest.getMozzarella()
            || paneerItem.getQuantityPromised() < orderRequest.getPaneer()
            || kanchanItem.getQuantityPromised() < orderRequest.getKanchan()){
            return false;
        }

        mozzarellaItem.setTotalQuantity(mozzarellaItem.getTotalQuantity() - orderRequest.getMozzarella());
        mozzarellaItem.setQuantityPromised(mozzarellaItem.getQuantityPromised() - orderRequest.getMozzarella());
        inventoryRepository.save(mozzarellaItem);

        paneerItem.setTotalQuantity(paneerItem.getTotalQuantity() - orderRequest.getPaneer());
        paneerItem.setQuantityPromised(paneerItem.getQuantityPromised() - orderRequest.getPaneer());
        inventoryRepository.save(paneerItem);

        kanchanItem.setTotalQuantity(kanchanItem.getTotalQuantity() - orderRequest.getKanchan());
        kanchanItem.setQuantityPromised(kanchanItem.getQuantityPromised() - orderRequest.getKanchan());
        inventoryRepository.save(kanchanItem);
        
        return true;
    }

    private List<InventoryItem> getAllItems(){
        InventoryItem mozzarellaItem = inventoryRepository.findById("mozzarella").orElse(null);
        InventoryItem paneerItem = inventoryRepository.findById("paneer").orElse(null);
        InventoryItem kanchanItem = inventoryRepository.findById("kanchan").orElse(null);
        return List.of(mozzarellaItem, paneerItem, kanchanItem);
    }
}
