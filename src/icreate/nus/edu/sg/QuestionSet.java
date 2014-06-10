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
	private Key uid;
	
	@Persistent(mappedBy="qs")
	private List<Question> qList;
	
	
	public Key getUid() {
		return uid;
	}
	public void setUid(Key uid) {
		this.uid = uid;
	}
	public List<Question> getqList() {
		return qList;
	}
	public void setqList(List<Question> qList) {
		this.qList = qList;
	}
	
}
