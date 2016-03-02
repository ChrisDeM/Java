package BankServices;
import static java.util.stream.Collectors.toList;

import java.util.LinkedList;
import java.util.List;

public class Account {
	
	private String name;
	private int date;
	double initial;
	private int numeroConto;
	LinkedList<Deposit> depositiList= new LinkedList<>();
	private double saldo=0;
	LinkedList<Withdrawal> witList=new LinkedList<>();
	
	
	
	
	public int getDate() {
		return date;
	}



	public double closeAccount(){
		
		return saldo;
	}
	
	
	
	public LinkedList<Withdrawal> getWitList() {
		if(witList.size()>0)
		return witList;
		
		else
			return null;
	}



	public void setWitList(Withdrawal w) {
		this.witList.addLast(w);
		saldo=saldo-w.getValue();
	}



	public double getSaldo() {
		return saldo;
	}

	

	public LinkedList<Deposit> getDepositiList() {
		return depositiList;
	}

	public void setDepositiList(Deposit d) {
		this.depositiList.addLast(d);
		saldo=saldo + d.getValue();
	}

	public int getNumeroConto() {
		return numeroConto;
	}

	public void setNumeroConto(int numeroConto) {
		this.numeroConto = numeroConto;
	}

	public Account(String name, int date, double initial) {
		super();
		this.name = name;
		this.date = date;
		this.initial = initial;
		saldo=0;
	}

	public String toString() {
		String s=null;
		
		int lastData=depositiList.getLast().getDate();
		
		if(witList.size()>0)
		if(lastData < this.witList.getLast().getDate())
			lastData=witList.getLast().getDate();
		
		s=numeroConto + "," + name + "," + lastData + "," +saldo;
		
		return s;
	}
		
	public List<Operation> getMovements() {
		
		LinkedList<Operation> ll=new LinkedList<>();
		
		for(Operation o : witList)
			ll.add(o);
		for(Operation o : depositiList)
			ll.add(o);
		
		return ll.stream()
				.sorted( (a,b) -> b.getDate() -a.getDate() )
				.collect(toList());
	}
	
	public List<Deposit> getDeposits() {
		return depositiList.stream()
				.sorted( (a,b) -> Double.compare(b.getValue(), a.getValue()) )
				.collect(toList());
	}

	public List<Withdrawal> getWithdrawals() {
		return witList.stream()
				.sorted( (a,b) -> Double.compare(b.getValue(), a.getValue()) )
				.collect(toList());
	}
}
