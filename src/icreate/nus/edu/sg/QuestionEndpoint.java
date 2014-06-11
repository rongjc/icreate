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

@Api(name = "questionendpoint", namespace = @ApiNamespace(ownerDomain = "nus.icreate", ownerName = "nus.icreate", packagePath = "edu.sg"))
public class QuestionEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listQuestion")
	public CollectionResponse<Question> listQuestion(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Question> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Question.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Question>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Question obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Question> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getQuestion")
	public Question getQuestion(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Question question = null;
		try {
			question = mgr.getObjectById(Question.class, id);
		} finally {
			mgr.close();
		}
		return question;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param question the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertQuestion")
	public Question insertQuestion(Question question) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (question.getQid() != null && containsQuestion(question)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(question);
		} finally {
			mgr.close();
		}
		return question;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param question the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateQuestion")
	public Question updateQuestion(Question question) {
		PersistenceManager mgr = getPersistenceManager();
		Question q;
		try {
			if (!containsQuestion(question)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			else{
				q = mgr.getObjectById(Question.class, question.getQid().getId());
				q.copyQuestion(question);
				//q.setA(question.getA());
				//mgr.makePersistent(q);
			}
		} finally {
			mgr.close();
		}
		return q;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeQuestion")
	public void removeQuestion(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Question question = mgr.getObjectById(Question.class, id);
			mgr.deletePersistent(question);
		} finally {
			mgr.close();
		}
	}

	private boolean containsQuestion(Question question) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Question.class, question.getQid().getId());

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
