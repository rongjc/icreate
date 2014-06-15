package icreate.nus.edu.sg;

import icreate.nus.edu.sg.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "responseendpoint", namespace = @ApiNamespace(ownerDomain = "nus.icreate", ownerName = "nus.icreate", packagePath = "edu.sg"))
public class ResponseEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listResponse")
	public CollectionResponse<Response> listResponse(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Response> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Response.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Response>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Response obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Response> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getResponse")
	public Response getResponse(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Response response = null;
		try {
			response = mgr.getObjectById(Response.class, id);
		} finally {
			mgr.close();
		}
		return response;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param response the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertResponse")
	public Response insertResponse(Response response) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsResponse(response)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(response);
		} finally {
			mgr.close();
		}
		return response;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param response the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateResponse")
	public Response updateResponse(Response response) {
		PersistenceManager mgr = getPersistenceManager();
		Response r;
		try {
			if (!containsResponse(response)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			r = mgr.getObjectById(Response.class, response.getRid().getId());
			r.copyResponse(response);
		} finally {
			mgr.close();
		}
		return response;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeResponse")
	public void removeResponse(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Response response = mgr.getObjectById(Response.class, id);
			mgr.deletePersistent(response);
		} finally {
			mgr.close();
		}
	}

	private boolean containsResponse(Response response) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Response.class, response.getRid().getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
