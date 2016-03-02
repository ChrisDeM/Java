package social;

import java.util.Collection;
import java.util.HashSet;

public class Group {
  
	String name;
	HashSet<String> members = new HashSet<String>();

	public Group(String groupName) {
    name = groupName;
	}

	public void addPerson(String codePerson) {
		members.add(codePerson);
	}

	public Collection<String> getMembers() {
		return members;
	}

	public String getName() {
		return name;
	}
}