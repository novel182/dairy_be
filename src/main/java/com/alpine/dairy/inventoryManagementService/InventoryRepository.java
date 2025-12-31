package com.alpine.dairy.inventoryManagementService;

import org.springframework.data.jpa.repository.JpaRepository;

interface InventoryRepository extends JpaRepository<InventoryItem, String> {
    
}
