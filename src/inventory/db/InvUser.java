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
 * GAE ENTITY UTIL CLASS: "InvTransaction" <br>
 * PARENT: NONE <br>
 * KEY: A long Id generated by GAE <br>
 * FEATURES: <br>
 * - "name" a {@link String} with the name record for the invuser<br>
 * - "isAdmin" a {@link String} with the isAdmin record for the invuser<br>
 */

public class InvUser {

	//
	// SECURITY
	//

	/**
	 * Private constructor to avoid instantiation.
	 */
	private InvUser() {
	}

	//
	// KIND
	//

	/**
	 * The name of the InvUser ENTITY KIND used in GAE.
	 */
	private static final String ENTITY_KIND = "InvUser";

	//
	// KEY
	//

	/**
	 * Return the Key for a given InvUser id given as String.
	 * 
	 * @param invUserID A string with the InvUser ID (a long).
	 * @return the Key for this invUser.
	 */
	public static Key getKey(String invUserID) {
		long id = Long.parseLong(invUserID);
		Key invUserKey = KeyFactory.createKey(ENTITY_KIND, id);
		return invUserKey;
	}

	/**
	 * Return the string ID corresponding to the key for the invuser.
	 * 
	 * @param invUser The GAE Entity storing the invUser.
	 * @return A string with the invuser ID (a long).
	 */
	public static String getStringID(Entity invUser) {
		return Long.toString(invUser.getKey().getId());
	}

	//
	// ATTRIBUTES
	//
	
	/**
	 * The property name for the <b>name</b> value of the invuser.
	 */
	private static final String NAME_PROPERTY = "Name";
	
	/**
	 * The property isAdmin for the <b>isAdmin</b> value of the invUser.
	 */
	private static final String ISADMIN_PROPERTY = "IsAdmin";

	
	
	
	
	//
	// GETTERS
	//
	
	/**
	 * Return the name for the invuser.
	 * 
	 * @param invUser The GAE Entity storing the invUser
	 * @return the name in the invUser.
	 */
	public static String getName(Entity invUser) {
		Object name = invUser.getProperty(NAME_PROPERTY);
		if (name == null)
			name = "";
		return (String) name;
	}
	
	/**
	 * Return the isAdmin for the invuser.
	 * 
	 * @param invUser The GAE Entity storing the invUser
	 * @return the isAdmin in the invuser.
	 */
	public static String getIsAdmin(Entity invUser) {
		Object isAdmin = invUser.getProperty(ISADMIN_PROPERTY);
		if (isAdmin == null)
			isAdmin = "";
		return (String) isAdmin;
	}
	

	

	

	//
	// CREATE INVUSER
	//

	/**
	 * Create a new invuser if the invUserID is correct and none exists with this id.
	 * 
	 * @param name the name for this invuser
	 * @param isAdmin the isAdmin value for this invuser
	 * @return the Entity created with this id or null if error
	 */
	public static Entity createInvUser(String name, String isAdmin) {
		Entity invUser = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {

			invUser = new Entity(ENTITY_KIND);
			invUser.setProperty(NAME_PROPERTY, name);
			invUser.setProperty(ISADMIN_PROPERTY, isAdmin);
			datastore.put(invUser);

			txn.commit();
		} catch (Exception e) {
			return null;
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}

		return invUser;
	}

	//
	// GET INVUSER
	//

	/**
	 * Get the invuser based on a string containing its long ID.
	 * 
	 * @param invUserID a {@link String} containing the ID key (a <code>long</code> number)
	 * @return A GAE {@link Entity} for the InvUserID or <code>null</code> if none or error.
	 */
	public static Entity getInvUser(String invUserID) {
		Entity invUser = null;
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			long id = Long.parseLong(invUserID);
			Key invUserKey = KeyFactory.createKey(ENTITY_KIND, id);
			invUser = datastore.get(invUserKey);
		} catch (Exception e) {
			// TODO log the error
		}
		return invUser;
	}


	//
	// UPDATE INVUSER
	//

	/**
	 * Update the current description of the invuser
	 * 
	 * @param invUserID the ID for this invuser
	 * @param name the name for this invuser
	 * @param isAdmin The isAdmin value of the invuser
	 * @return true if succeed and false otherwise
	 */
	public static boolean updateInvUser(String invUserID, String name, String isAdmin) {
		Entity invUser = null;
		try {
			invUser = getInvUser(invUserID);
			invUser.setProperty(NAME_PROPERTY, name);
			invUser.setProperty(ISADMIN_PROPERTY, isAdmin);
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(invUser);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//
	// DELETE INVUSER
	//

	/**
	 * Delete the invuser if not linked to anything else.
	 * 
	 * @param invUserID A string with the inuser ID (a long).
	 * @return True if succeed, false otherwise.
	 */
	public static boolean deleteInvUser(String invUserID) {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(getKey(invUserID));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//
	// QUERY INVUSER
	//

	/**
	 * Return the requested number of invuser (e.g. 100).
	 * 
	 * @param limit The number of invuser to be returned.
	 * @return A list of GAE {@link Entity entities}.
	 */
	public static List<Entity> getFirstInvUsers(int limit) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(ENTITY_KIND);
		List<Entity> result = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(limit));
		return result;
	}

}
