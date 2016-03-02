package social;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;


public class Social {
	HashMap<String, Person> people = new HashMap<String, Person>();
	HashMap<String, Group> groups = new HashMap<String, Group>();

	public void addPerson(String code, String name, String surname)
			throws PersonExistsException {
		Person p = new Person(code, name, surname);
		if (people.containsKey(code))
			throw new PersonExistsException();
		people.put(code, p);
	}

	public String getPerson(String code) throws NoSuchCodeException {
		Person p = people.get(code);
		if (p == null)
			throw new NoSuchCodeException();
		return p.toString();
	}

	public void addFriendship(String codePerson1, String codePerson2)
			throws NoSuchCodeException {
		Person p1 = people.get(codePerson1);
		Person p2 = people.get(codePerson2);
		if (p1 == null || p2 == null)
			throw new NoSuchCodeException();
		p1.addFriend(codePerson2);
		p2.addFriend(codePerson1);
	}

	public Collection<String> listOfFriends(String codePerson)
			throws NoSuchCodeException {
		if (people.containsKey(codePerson)) {
			if (people.get(codePerson).getFriends().size() == 0)
				return new LinkedList<>();
			else
				return people.get(codePerson).getFriends();
		} else
			throw new NoSuchCodeException();

	
	}

	private final static Collection<String> emptyList=new LinkedList<>();
	public Collection<String> friendsOfFriends(String codePerson)
			throws NoSuchCodeException {
		if (people.size() == 0)
			return emptyList;
		Person p1 = people.get(codePerson);
		if (p1 == null)
			throw new NoSuchCodeException();
		Collection<String> friends = p1.getFriends();
		Iterator<String> it = friends.iterator();
		HashSet<String> friends2 = new HashSet<String>();
		while (it.hasNext()) {
			String s = it.next();
			Person friend = people.get(s);
			Iterator<String> it2 = friend.getFriends().iterator();
			while (it2.hasNext()) {
				String friend2 = it2.next();
				if (!friends.contains(friend2) && !friend2.equals(codePerson))
					friends2.add(friend2);
			}
		}
		if (friends2.size() == 0)
			return emptyList;
		return friends2;
	}

	public Collection<String> friendsOfFriendsNoRepitition(String codePerson)
			throws NoSuchCodeException {
		return friendsOfFriends(codePerson);
	}

	public void addGroup(String groupName) {
		groups.put(groupName, new Group(groupName));
	}

	public Collection<String> listOfGroups() {
		if (groups.size() == 0)
			return emptyList;
		return groups.keySet();
	}

	public void addPersonToGroup(String codePerson, String groupName) {
		Group g = groups.get(groupName);
		Person p = people.get(codePerson);
		if (p == null || g == null)
			return;
		g.addPerson(codePerson);
		p.addGroup(groupName);
	}

	public Collection<String> listOfPeopleInGroup(String groupName) {
		Group g = groups.get(groupName);
		if (g.getMembers().size() == 0)
			return null;
		return g.getMembers();
	}

	public String personWithLargestNumberOfFriends() {
		Collection<Person> pp = people.values();
		Iterator<Person> it = pp.iterator();
		int max = 0;
		String pmax = null;
		while (it.hasNext()) {
			Person p = it.next();
			int s = p.getFriends().size();
			if (s > max) {
				max = s;
				pmax = p.getCode();
			}
		}
		return pmax;
	}

	public String personWithMostFriendsOfFriends() {
		Collection<Person> pp = people.values();
		Iterator<Person> it = pp.iterator();
		int max = 0;
		String pmax = null;
		while (it.hasNext()) {
			Person p = it.next();
			int s=0;
			try {
				if (friendsOfFriends(p.getCode())!=null)
					s = friendsOfFriends(p.getCode()).size();
			} catch (NoSuchCodeException e) {
				return null;
			}
			if (s > max) {
				max = s;
				pmax = p.getCode();
			}
		}
		return pmax;
	}

	public String largestGroup() {
		Collection<Group> pp = groups.values();
		Iterator<Group> it = pp.iterator();
		int max = 0;
		String gmax = null;
		while (it.hasNext()) {
			Group p = it.next();
			int s = p.getMembers().size();
			if (s > max) {
				max = s;
				gmax = p.getName();
			}
		}
		return gmax;
	}

	public String personInLargestNumberOfGroups() {
		Collection<Person> pp = people.values();
		Iterator<Person> it = pp.iterator();
		int max = 0;
		String pmax = null;
		while (it.hasNext()) {
			Person p = it.next();
			int s;

			s = p.getGroups().size();

			if (s > max) {
				max = s;
				pmax = p.getCode();
			}
		}
		return pmax;
	}

}