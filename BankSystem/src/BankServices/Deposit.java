package BankServices;

public class Deposit extends Operation {	
	
	private int code;
	private int date; 
	private double value;
	
	
	
	public int getCode() {
		return code;
	}



	public int getDate() {
		return date;
	}



	public double getValue() {
		return value;
	}



	public Deposit(int code, int date, double value) {
		super();
		this.code = code;
		this.date = date;
		this.value = value;
	}
	
	
	public String toString(){
		String s=null;
		
		s=date + "," + value +  "+";
		
		return s;
	}
	
}
