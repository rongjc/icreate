package icreate.nus.edu.sg;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key uid;
	
	@Persistent
	private String nusId;
	
	@Persistent(mappedBy="user")
	List<QuestionSet> qs;

	public Key getUid() {
		return uid;
	}

	public void setUid(Key uid) {
		this.uid = uid;
	}

	public String getNusId() {
		return nusId;
	}

	public void setNusId(String nusId) {
		this.nusId = nusId;
	}
	
	
}
