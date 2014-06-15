package icreate.nus.edu.sg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

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
	
	@Persistent
	private String keyString;

	public String getKeyString() {
		if(this.keyString == null)
		  this.keyString = KeyFactory.keyToString(this.qsid); 
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
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
		List<Question> t = qs.getqList();
		this.qList = new LinkedList<Question>();
		for(int i = 0; i < t.size(); i++){
			this.qList.add(t.get(i));
		}
		//Question b = t.get(2);
		this.name = qs.getName();
		//this.qList.set(0,qs.getqList().get(qs.getqList().size()-1));
	}
}
