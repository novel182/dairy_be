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
        int availableQuantity = requestedItem.getTotalQuantity() - requestedItem.getQuantityTemporaryHold() - requestedItem.getQuantityPromised();
        if(availableQuantity < amount){
            return false;
        }
        return true;
    }

    public boolean addHoldQuantity(int mozzarella, int paneer, int kanchan){
        inventoryRepository.findById("mozzarella").ifPresent(item -> {
            item.setQuantityTemporaryHold(item.getQuantityTemporaryHold() + mozzarella);
            inventoryRepository.save(item);
        });
        inventoryRepository.findById("paneer").ifPresent(item -> {
            item.setQuantityTemporaryHold(item.getQuantityTemporaryHold() + paneer);
            inventoryRepository.save(item);
        });
        inventoryRepository.findById("kanchan").ifPresent(item -> {
            item.setQuantityTemporaryHold(item.getQuantityTemporaryHold() + kanchan);
            inventoryRepository.save(item);
        });
        return true;
    }

    public boolean holdRequest(OrderRequest orderRequest){
        return addHoldQuantity(orderRequest.getMozzarella(), orderRequest.getPaneer(), orderRequest.getKanchan());
    }

    public boolean changeHoldRequest(OrderRequest oldRequest, OrderRequest newRequest){
        int mozzarellaDiff = newRequest.getMozzarella() - oldRequest.getMozzarella();
        int paneerDiff = newRequest.getPaneer() - oldRequest.getPaneer();
        int kanchanDiff = newRequest.getKanchan() - oldRequest.getKanchan();
        if(mozzarellaDiff == 0 || paneerDiff == 0 || kanchanDiff == 0){
            return true;
        }
        return addHoldQuantity(mozzarellaDiff, paneerDiff, kanchanDiff);
    }

    public boolean fulfillHoldRequest(OrderRequest orderRequest){
        List<InventoryItem> items = getAllItems();
        InventoryItem mozzarellaItem = items.get(0);
        InventoryItem paneerItem = items.get(1);
        InventoryItem kanchanItem = items.get(2);

        if(mozzarellaItem == null || paneerItem == null || kanchanItem == null){
            return false;
        }

        if(mozzarellaItem.getQuantityTemporaryHold() > mozzarellaItem.getTotalQuantity()
            || paneerItem.getQuantityTemporaryHold() > paneerItem.getTotalQuantity()
            || kanchanItem.getQuantityTemporaryHold() > kanchanItem.getTotalQuantity()){
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

    public boolean fulfillDeliveredRequest(OrderRequest orderRequest){
        List<InventoryItem> items = getAllItems();
        InventoryItem mozzarellaItem = items.get(0);
        InventoryItem paneerItem = items.get(1);
        InventoryItem kanchanItem = items.get(2);

        if(mozzarellaItem == null || paneerItem == null || kanchanItem == null){
            return false;
        }

        if(mozzarellaItem.getQuantityPromised() > mozzarellaItem.getTotalQuantity()
            || paneerItem.getQuantityPromised() > paneerItem.getTotalQuantity()
            || kanchanItem.getQuantityPromised() > kanchanItem.getTotalQuantity()){
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

    public List<InventoryItem> getAllItems(){
        // The items have to be on the fixed order: mozzarella, paneer, kanchan
        return inventoryRepository.findAll();
    }
}
