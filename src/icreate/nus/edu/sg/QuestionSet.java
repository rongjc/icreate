package icreate.nus.edu.sg;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class QuestionSet {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key qsid;
	
	@Persistent(mappedBy="qs")
	private List<Question> qList;
	
	@Persistent
	private User user;
	
	@Persistent
	private String name;

	public Key getQsid() {
		return qsid;
	}
	public void setQsid(Key qsid) {
		this.qsid = qsid;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Question> getqList() {
		return qList;
	}
	public void setqList(List<Question> qList) {
		this.qList = qList;
	}
	public void copyQS(QuestionSet qs){
		this.qList = qs.getqList();
	}
}
