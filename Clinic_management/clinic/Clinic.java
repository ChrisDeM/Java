package clinic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import static java.util.stream.Collectors.toList;





public class Clinic {

	LinkedList<Person> patientList=new LinkedList<Person>();
	LinkedList<Doctor> doctorList=new LinkedList<Doctor>();
	
	public void addPatient(String first, String last, String ssn) {
     patientList.add(new Person(first, last, ssn));
	}

	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
       doctorList.add( new Doctor(first, last, ssn, docID, specialization) );
	}

	public Person getPatient(String ssn) throws NoSuchPatient {
		for(Person p : patientList)
			if(p.getSSN()==ssn)
				return p;
		
		throw new NoSuchPatient();
	}

	public Doctor getDoctor(int docID) throws NoSuchDoctor {
		for(Doctor d : doctorList)
			if(d.getId()==docID)
				return d;
		
		throw new NoSuchDoctor();
	}
	
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
    
		int eccezione=0;
		Person person=null;
		Doctor doctor=null;
		
		for(Person p : patientList)
			if(p.getSSN()==ssn){
				person=p;
				eccezione=1; 
			}
		if(eccezione==0)
			throw new NoSuchPatient();	
		
		eccezione=0;
		for(Doctor d : doctorList)
			if(d.getId()==docID){
				doctor=d;
				eccezione=1; 
			}
		if(eccezione==0)
		   throw new NoSuchDoctor();
		
		
		doctor.addPatient(person);
		person.addDoctor(doctor);
	}

	/**
	 * returns the collection of doctors that have no patient at all, sorted in alphabetic order.
	 */
	Collection<Doctor> idleDoctors(){
		
		return 
		doctorList.stream()
		.filter( d -> d.getPatients().size()==0 )
		.sorted( (a,b) -> a.getSSN().compareTo( b.getSSN() ) )
		.collect(toList());		
	}

	/**
	 * returns the collection of doctors that a number of patients larger than the average.
	 */
	Collection<Doctor> busyDoctors(){
		int numTotPatient;
		int avg;
		
		numTotPatient=
		doctorList.stream()
		.mapToInt( d -> d.getPatients().size() )
		.sum();
		
		avg=numTotPatient/doctorList.size();
				
		return doctorList.stream()
			   .filter( d -> d.getPatients().size()>avg)
			   .collect(toList());
	}

	/**
	 * returns list of strings
	 * containing the name of the doctor and the relative number of patients
	 * with the relative number of patients, sorted by decreasing number.<br>
	 * The string must be formatted as "<i>### : ID SURNAME NAME</i>" where <i>###</i>
	 * represent the number of patients (printed on three characters).
	 */
	Collection<String> doctorsByNumPatients(){

          return
          doctorList.stream()
          .sorted( (a,b) -> a.getPatients().size() - b.getPatients().size() )
          .map( d -> { String s=null; s= String.format("%3d", d.getPatients().size()) + " : " + d.getId() + " " + d.getLast() + " " + d.getFirst(); return s;   } )
          .collect(toList());		
	}
	
	/**
	 * computes the number of
	 * patients per (their doctor's) specialization.
	 * The elements are sorted first by decreasing count and then by alphabetic specialization.<br>
	 * The strings are structured as "<i>### - SPECIALITY</i>" where <i>###</i>
	 * represent the number of patients (printed on three characters).
	 */
	public Collection<String> countPatientsPerSpecialization(){
		
		
		
		return null;
	}
	
	public void loadData(String path) throws IOException {
		
String identification;
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		LinkedList<String> wordList= new LinkedList<>();
		
		String line = reader.readLine();
		while(line!=null) {
			
			
			wordList=stringDivision(line, ";");	
	
			identification=wordList.removeFirst();
			
			if(identification.equals("P")){
				if( wordList.size()==3 )
				createPerson(wordList);
			}
			else if (identification.equals("M")){
				if( wordList.size()==5 )
				createDoctor(wordList);
			}
							
			line=reader.readLine();  
		}
		
		reader.close();
		
	}

	private void createDoctor(LinkedList<String> wordList){
		doctorList.add(new Doctor( wordList.get(1),
		           wordList.get(2), wordList.get(3), Integer.parseInt(wordList.getFirst()), wordList.get(4)));
		
	}
	private void createPerson(LinkedList<String> wordList){
		patientList.add(new Person( wordList.getFirst(), wordList.get(1), wordList.getLast() ) );
	}

private LinkedList<String> stringDivision(String line, String delimiters){
		
		LinkedList<String> wordList= new LinkedList<>();
		StringTokenizer strTkr= new StringTokenizer(line, delimiters);
		
		while(strTkr.hasMoreTokens())
			wordList.add(strTkr.nextToken());			
		
		return wordList;
	}


}
