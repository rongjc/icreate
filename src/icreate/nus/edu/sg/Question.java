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
	private Key qid;
	
	@Persistent
	private String Q;
	
	@Persistent
	private List<String> A;
	
	@Persistent
	private QuestionSet qs;
	
	@Persistent
	private int answerIndex; 
	
	@Persistent(mappedBy="q")
	private List<Response> responses;
	
	@Persistent
	private boolean isAnonymous;
	
	public int getAnswerIndex() {
		return answerIndex;
	}

	public void setAnswerIndex(int answerIndex) {
		this.answerIndex = answerIndex;
	}

	
	public boolean isAnonymous() {
		return isAnonymous;
	}

	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}


	public Key getQid() {
		return qid;
	}

	public void setQid(Key qid) {
		this.qid = qid;
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
	
	public void copyQuestion(Question qn){
		this.A = qn.getA();
		this.Q = qn.getQ();
		this.answerIndex = qn.getAnswerIndex();
		this.isAnonymous = qn.isAnonymous();
	
	}

}
