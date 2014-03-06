/**
 * Copyright 2014 -
 * Licensed under the Academic Free License version 3.0
 * http://opensource.org/licenses/AFL-3.0
 * 
 * Authors: Alex Verkhovtsev
 */

package inventory.db;

import java.util.List;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;


/**
 * GAE ENTITY UTIL CLASS: "Product" <br>
 * PARENT: NONE <br>
 * KEY: A long Id generated by GAE <br>
 * FEATURES: <br>
 * - "name" a {@link String} with the name record for the product<br>
 */

public class Product {

	//
	// SECURITY
	//

	/**
	 * Private constructor to avoid instantiation.
	 */
	private Product() {
	}

	//
	// KIND
	//

	/**
	 * The name of the Permit ENTITY KIND used in GAE.
	 */
	private static final String ENTITY_KIND = "Product";

	//
	// KEY
	//

	/**
	 * Return the Key for a given Permit id given as String.
	 * 
	 * @param recordID A string with the record ID (a long).
	 * @return the Key for this recordID.
	 */
	public static Key getKey(String productID) {
		long id = Long.parseLong(productID);
		Key productKey = KeyFactory.createKey(ENTITY_KIND, id);
		return productKey;
	}

	/**
	 * Return the string ID corresponding to the key for the permit.
	 * 
	 * @param record The GAE Entity storing the record.
	 * @return A string with the record ID (a long).
	 */
	public static String getStringID(Entity product) {
		return Long.toString(product.getKey().getId());
	}

	//
	// ATTRIBUTES
	//

	/**
	 * The property name for the <b>name</b> value of the product.
	 */
	private static final String NAME_PROPERTY = "Name";

	/**
	 * The property quantaty for the <b>quantity</b> value of the product.
	 */
	private static final String QTY_PROPERTY = "Quantity";
	
	/**
	 * The property purchase_price for the <b>purchace_price</b> value of the product.
	 */
	private static final String PURCH_PRICE_PROPERTY = "Purchase_Price";
	
	/**
	 * The property sales_price for the <b>sales_price</b> value of the product.
	 */
	private static final String SALES_PRICE_PROPERTY = "Sales_Price";
	
	/**
	 * The property min_quantity for the <b>min_quantity</b> value of the product.
	 */
	private static final String MIN_QUANT_PROPERTY = "Min_Quantity";
	
	/**
	 * The property max_quantity for the <b>max_quantity</b> value of the product.
	 */
	private static final String MAX_QUANT_PROPERTY = "Max_Quantity";
	
	
	
	
	
	//
	// GETTERS
	//
	
	
	
	/**
	 * Return the name for the product.
	 * 
	 * @param record The GAE Entity storing the name
	 * @return the name in the product.
	 */
	public static String getName(Entity record) {
		Object name = record.getProperty(NAME_PROPERTY);
		if (name == null)
			name = "";
		return (String) name;
	}
	
	
	/**
	 * Return the quantity for the product.
	 * 
	 * @param record The GAE Entity storing the quantity
	 * @return the quantity in the product.
	 */
	public static String getQuantity(Entity record) {
		Object quantity = record.getProperty(QTY_PROPERTY);
		if (quantity == null)
			quantity = "";
		return (String) quantity;
	}
	
	
	/**
	 * Return the purchase_price for the product.
	 * 
	 * @param record The GAE Entity storing the purchase_price
	 * @return the purchase_price in the product.
	 */
	public static String getPurchasePrice(Entity record) {
		Object purchase_price = record.getProperty(PURCH_PRICE_PROPERTY);
		if (purchase_price == null)
			purchase_price = "";
		return (String) purchase_price;
	}
	
	
	/**
	 * Return the sales_price for the product.
	 * 
	 * @param record The GAE Entity storing the sales_price
	 * @return the sales_price in the product.
	 */
	public static String getSalesPrice(Entity record) {
		Object sales_price = record.getProperty(SALES_PRICE_PROPERTY);
		if (sales_price == null)
			sales_price = "";
		return (String) sales_price;
	}
	
	
	/**
	 * Return the min_quantity for the product.
	 * 
	 * @param record The GAE Entity storing the min_quantity
	 * @return the quantity in the product.
	 */
	public static String getMinQuantity(Entity record) {
		Object min_quantity = record.getProperty(MIN_QUANT_PROPERTY);
		if (min_quantity == null)
			min_quantity = "";
		return (String) min_quantity;
	}
	
	/**
	 * Return the max_quantity for the product.
	 * 
	 * @param record The GAE Entity storing the max_quantity
	 * @return the quantity in the product.
	 */
	public static String getMaxQuantity(Entity record) {
		Object max_quantity = record.getProperty(MAX_QUANT_PROPERTY);
		if (max_quantity == null)
			max_quantity = "";
		return (String) max_quantity;
	}
	

	

	//
	// CREATE PRODUCT
	//

	/**
	 * Create a new product if the productID is correct and none exists with this id.
	 * 
	 * @param productName The name for this product.
	 * @param quantity The quantity for this product.
	 * @param puchasePrice The purchase price for this product.
	 * @param salesPrice The sales price for this product.
	 * @param minQuantity The min quantity for this product.
	 * @param maxQuantity The max quantity for this product.
	 * 
	 * @return the Entity created with this id or null if error
	 */
	public static Entity createProduct(String productName, String quantity,  String purchasePrice,  String salesPrice,
			 String minQuantity,  String maxQuantity) {
		Entity product = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {

			product = new Entity(ENTITY_KIND);
			product.setProperty(NAME_PROPERTY, productName);
			product.setProperty(QTY_PROPERTY, quantity);
			product.setProperty(PURCH_PRICE_PROPERTY, purchasePrice);
			product.setProperty(SALES_PRICE_PROPERTY, salesPrice);
			product.setProperty(MIN_QUANT_PROPERTY, minQuantity);
			product.setProperty(MAX_QUANT_PROPERTY, maxQuantity);
			
			datastore.put(product);

			txn.commit();
		} catch (Exception e) {
			return null;
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}

		return product;
	}

	//
	// GET PRODUCT
	//

	/**
	 * Get the product based on a string containing its long ID.
	 * 
	 * @param productID a {@link String} containing the ID key (a <code>long</code> number)
	 * @return A GAE {@link Entity} for the Product or <code>null</code> if none or error.
	 */
	public static Entity getProduct(String productID) {
		Entity product = null;
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			long id = Long.parseLong(productID);
			Key productKey = KeyFactory.createKey(ENTITY_KIND, id);
			product = datastore.get(productKey);
		} catch (Exception e) {
			// TODO log the error
		}
		return product;
	}


	//
	// UPDATE PRODUCT
	//

	/**
	 * Update the current description of the product
	 * 
	 * @param productID A string with the product ID (a long).
	 * @param name The name of the product as a String.
	 * @return true if succeed and false otherwise
	 */
	public static boolean updateProduct(String productID, String name, String quantity,  String purchasePrice,  String salesPrice,
			 String minQuantity,  String maxQuantity) {
		Entity product = null;
		try {
			product = getProduct(productID);
			product.setProperty(NAME_PROPERTY, product);
			product.setProperty(QTY_PROPERTY, quantity);
			product.setProperty(PURCH_PRICE_PROPERTY, purchasePrice);
			product.setProperty(SALES_PRICE_PROPERTY, salesPrice);
			product.setProperty(MIN_QUANT_PROPERTY, minQuantity);
			product.setProperty(MAX_QUANT_PROPERTY, maxQuantity);
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(product);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//
	// DELETE PRODUCT
	//

	/**
	 * Delete the product if not linked to anything else.
	 * 
	 * @param productID A string with the product ID (a long).
	 * @return True if succeed, false otherwise.
	 */
	public static boolean deleteProduct(String productID) {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(getKey(productID));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//
	// QUERY PRODUCT
	//

	/**
	 * Return the requested number of products (e.g. 100).
	 * 
	 * @param limit The number of products to be returned.
	 * @return A list of GAE {@link Entity entities}.
	 */
	public static List<Entity> getFirstProducts(int limit) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(ENTITY_KIND);
		List<Entity> result = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(limit));
		return result;
	}

}
