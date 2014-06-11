package icreate.nus.edu.sg;

import icreate.nus.edu.sg.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.google.appengine.api.datastore.Key;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "questionsetendpoint", namespace = @ApiNamespace(ownerDomain = "nus.icreate", ownerName = "nus.icreate", packagePath = "edu.sg"))
public class QuestionSetEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listQuestionSet")
	public CollectionResponse<QuestionSet> listQuestionSet(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<QuestionSet> execute = null;

		try {
			mgr = getPersistenceManager();
			mgr.getFetchPlan().setMaxFetchDepth(2);
			Query query = mgr.newQuery(QuestionSet.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<QuestionSet>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (QuestionSet obj : execute){
				//obj.getqList();
				
			}
				
		} finally {
			mgr.close();
		}

		return CollectionResponse.<QuestionSet> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getQuestionSet")
	public QuestionSet getQuestionSet(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		QuestionSet questionset = null;
		try {
			questionset = mgr.getObjectById(QuestionSet.class, id);
			
			try{questionset.getqList();}catch(Exception e){}
		} finally {
			mgr.close();
		}
		return questionset;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param questionset the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertQuestionSet")
	public QuestionSet insertQuestionSet(QuestionSet questionset) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (questionset.getQsid()!=null && containsQuestionSet(questionset)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(questionset);
		} finally {
			mgr.close();
		}
		return questionset;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param questionset the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateQuestionSet")
	public QuestionSet updateQuestionSet(QuestionSet questionset) {
		PersistenceManager mgr = getPersistenceManager();
		QuestionSet qs;
		try {
			if (!containsQuestionSet(questionset)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			
			
			qs = mgr.getObjectById(QuestionSet.class, questionset.getQsid().getId());
			qs.copyQS(questionset);
		} finally {
			mgr.close();
		}
		return qs;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeQuestionSet")
	public void removeQuestionSet(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			QuestionSet questionset = mgr.getObjectById(QuestionSet.class, id);
			mgr.deletePersistent(questionset);
		} finally {
			mgr.close();
		}
	}
	

	/*
	@ApiMethod(name = "addQuestionById")
	public void addQuestionById(@Named("qid") Long qid, @Named("qsid") Long qsid) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			QuestionSet questionset = mgr.getObjectById(QuestionSet.class, qsid);
			Question question = mgr.getObjectById(Question.class, qid);
			//if (questionset.getqList().contains(question) == false){
			List<Question> q = questionset.getqList();
			q.add(question);
			questionset.setqList(q);
			//}
			mgr.makePersistent(questionset);
		} finally {
			mgr.close();
		}
	}*/

	private boolean containsQuestionSet(QuestionSet questionset) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {

			mgr.getObjectById(QuestionSet.class, questionset.getQsid().getId());
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
