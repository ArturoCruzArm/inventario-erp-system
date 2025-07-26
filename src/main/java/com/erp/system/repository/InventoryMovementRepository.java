package com.erp.system.repository;

import com.erp.system.entity.InventoryMovement;
import com.erp.system.entity.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    
    List<InventoryMovement> findByProductId(Long productId);
    
    List<InventoryMovement> findByMovementType(MovementType movementType);
    
    @Query("SELECT im FROM InventoryMovement im WHERE im.product.id = :productId ORDER BY im.movementDate DESC")
    List<InventoryMovement> findByProductIdOrderByMovementDateDesc(@Param("productId") Long productId);
    
    @Query("SELECT im FROM InventoryMovement im WHERE im.movementDate BETWEEN :startDate AND :endDate ORDER BY im.movementDate DESC")
    List<InventoryMovement> findByMovementDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(CASE WHEN im.movementType = 'IN' THEN im.quantity ELSE -im.quantity END) FROM InventoryMovement im WHERE im.product.id = :productId")
    Integer getCurrentStock(@Param("productId") Long productId);
    
    @Query("SELECT im FROM InventoryMovement im WHERE im.referenceNumber = :referenceNumber AND im.referenceType = :referenceType")
    List<InventoryMovement> findByReferenceNumberAndType(@Param("referenceNumber") String referenceNumber, @Param("referenceType") String referenceType);
}