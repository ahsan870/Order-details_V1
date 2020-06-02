package com.order.details.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.order.details.entity.OrderEntity;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderEntity, Long> {

	@Query(value = "SELECT c FROM OrderEntity c WHERE c.orderNumber = :orderNumber")
	@Transactional
	Optional<OrderEntity> findByOrderNumber(@Param("orderNumber") String orderNumber);

	@Query(value = "Delete FROM OrderEntity c WHERE c.orderNumber = :orderNumber")
	@Modifying
	@Transactional
	void deleteByOrderNumber(@Param("orderNumber") String orderNumber);

	@Query(value = "Update OrderEntity c set c.orderStatus =:orderStatus WHERE c.orderNumber = :orderNumber")
	@Modifying
	@Transactional
	void updateOrderStatus(@Param("orderNumber") String orderNumber, @Param("orderNumber") String orderStatus);

}
