package clinic;

import java.util.Collection;
import java.util.LinkedList;

public class Doctor extends Person {

	private int docID;
	private String specialization;
	
	LinkedList<Person> patientList=new LinkedList<Person>();
	
	
	public Doctor(String first, String last, String ssn,int docId, String specialization) {
		super(first, last, ssn);
		
		this.docID=docId;
		this.specialization=specialization;
		
	}

	public int getId(){
		return docID;
	}
	
	public String getSpecialization(){
		return specialization;
	}
	
	public void addPatient(Person patient){
	   patientList.add(patient);	
	}
	
	public Collection<Person> getPatients() {
		return patientList;
	}

}
