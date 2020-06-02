package com.order.details.client;

import com.order.details.model.ProductDetails;

public interface ProductDetailsClient {

	public Boolean checkProductExistsByProductCode(final String productCode) throws Exception;

	public ProductDetails getProductDetailsByProductCode(final String productCode) throws Exception;
	
	public Boolean checkQuantityAvailableByProductCode(final String productCode, final int quantityOrdered) throws Exception;
	
	public void updateQuantity(final String productCode, final int quantityOrdered) throws Exception;
}
