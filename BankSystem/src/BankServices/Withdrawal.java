package BankServices;

public class Withdrawal extends Operation {	

	private int code;
	private  int date; 
	private double value;
	
	public Withdrawal(int code, int date, double value) {
		
		this.code = code;
		this.date = date;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public int getDate() {
		return date;
	}

	public double getValue() {
		return value;
	}
	
	public String toString(){
		String s=null;
		
		s=date + "," + value  + "-";
		
		return s;
	}
	
	
	

}
