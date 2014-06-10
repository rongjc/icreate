package icreate.nus.edu.sg;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)


public class Question {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String Q;
	
	@Persistent
	private List<String> A;
	
	@Persistent
	private QuestionSet qs;
	
	/*
	public QuestionSet getQs() {
		return qs;
	}

	public void setQs(QuestionSet qs) {
		this.qs = qs;
	}
	 */
	public Key getId() {
		return id;
	}
	
	public void setId(Key id) {
		this.id = id;
	}
	public String getQ() {
		return Q;
	}
	public void setQ(String q) {
		Q = q;
	}
	public List<String> getA() {
		return A;
	}
	public void setA(List<String> a) {
		A = a;
	}

}
