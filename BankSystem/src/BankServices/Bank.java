package BankServices;

import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class Bank {
	
	private String bank;
	HashMap<Integer, Account> accountsMap=new HashMap<>();
	HashMap<Integer, Account> accountsCloseMap=new HashMap<>();
	HashMap<Integer, Account> accountValidi=new HashMap<>();
	int numeroConto=1;
	
	public Bank(String n) {
		bank=n;
	}
	
	public String getName() {
		return bank;
	}
	
	public int createAccount(String name, int date, double initial) {
		
		Account a=new Account(name, date, initial);
		a.setNumeroConto(numeroConto);
		Deposit d=new Deposit(numeroConto, date, initial);
		
		a.setDepositiList(d);
		
		accountsMap.put(numeroConto, a);
		accountValidi.put(numeroConto, a);
		
		numeroConto++;
				
		return numeroConto-1;
	}
	
	public Account deleteAccount(int code, int date) throws InvalidCode {
		
		if(!accountsMap.containsKey(code))
		    throw new InvalidCode();
		
		Account a=accountsMap.get(code);
		double saldo=a.closeAccount();
		
		
			if( date < a.getDepositiList().getLast().getDate())
				date=a.getDepositiList().getLast().getDate();
			
			if(a.getWitList()!=null)
			if(date < a.getWitList().getLast().getDate())
				date=a.getWitList().getLast().getDate();
		
		
   
		Withdrawal w= new Withdrawal(code, date, saldo);
		a.setWitList(w);
		
		
		accountsCloseMap.put(code, a);
		accountValidi.remove(code);
		accountsMap.remove(code);
		
		return a;
	}
	
	public Account getAccount(int code) throws InvalidCode {
		if(!accountsMap.containsKey(code))
		    throw new InvalidCode();
		
		return accountsMap.get(code);
	}

	public void deposit(int code, int date, double value) throws InvalidCode {
		if(!accountsMap.containsKey(code))
		    throw new InvalidCode();
		
		Account a=accountsMap.get(code);
		
		if( date < a.getDepositiList().getLast().getDate())
			date=a.getDepositiList().getLast().getDate();
		
		if(a.getWitList()!=null)
		if(date < a.getWitList().getLast().getDate())
			date=a.getWitList().getLast().getDate();
		
		Deposit d=new Deposit(code, date, value);
		a.setDepositiList(d);		
		
	}

	public void withdraw(int code, int date, double value) throws InvalidCode, InvalidValue {
		if(!accountsMap.containsKey(code))
		    throw new InvalidCode();
		
		if( value > accountsMap.get(code).getSaldo() )
			throw new InvalidValue();
		
		Account a=accountsMap.get(code);
		
		if( date < a.getDepositiList().getLast().getDate())
			date=a.getDepositiList().getLast().getDate();
		
		if(a.getWitList()!=null)
		if(date < a.getWitList().getLast().getDate())
			date=a.getWitList().getLast().getDate();
		
		Withdrawal w=new Withdrawal(code, date, value);
		a.setWitList(w);
		
	}
	
	public void transfer(int fromCode, int toCode, int date, double value) throws InvalidCode, InvalidValue {
		
		if(!accountsMap.containsKey(fromCode))
		    throw new InvalidCode();
		if(!accountsMap.containsKey(toCode))
		    throw new InvalidCode();
		
		if( value > accountsMap.get(fromCode).getSaldo() )
			throw new InvalidValue();
		
		Account from=accountsMap.get(fromCode);
		Account to=accountsMap.get(toCode);
		
		int dataFrom=date;
		int dataTo=date;
		
		if( date < from.getDepositiList().getLast().getDate())
			dataFrom=from.getDepositiList().getLast().getDate();
		if(from.getWitList()!=null)
		if(dataFrom < from.getWitList().getLast().getDate())
			dataFrom=from.getWitList().getLast().getDate();
		
		if( date < to.getDepositiList().getLast().getDate())
			dataTo=to.getDepositiList().getLast().getDate();
		if(to.getWitList()!=null)
		if(dataTo < to.getWitList().getLast().getDate())
			dataTo=to.getWitList().getLast().getDate();
		
		if(dataTo<dataFrom)
			dataTo=dataFrom;
		
		
		
		Withdrawal w=new Withdrawal(fromCode, dataFrom, value);
		Deposit d=new Deposit(toCode, dataTo, value);
		
		from.setWitList(w);
		to.setDepositiList(d);
		
	}
	
	public double getTotalDeposit() {
		
		return accountValidi.values().stream()
				.mapToDouble( a -> a.getSaldo() )
				.sum();
	}
	
	public List<Account> getAccounts() {
		
				
		return accountValidi.values().stream()
				.sorted( (a,b) -> a.getNumeroConto()-b.getNumeroConto() )
				.collect( toList() );	
	}
	
	public List<Account> getAccountsByBalance(double low, double high) {
		return accountValidi.values().stream()
				.filter( a -> a.getSaldo()>low && a.getSaldo()<=high)
				.sorted( (a,b) -> Double.compare(b.getSaldo(),a.getSaldo()) )
				.collect(toList());
	}
	
	public double getPerCentHigher(double min) {
		
		long accountSopra=accountValidi.values().stream()
				.filter( a -> a.getSaldo()>=min)
				.count();
		
		double perc=accountSopra*100/accountValidi.values().size();
		
		return perc;
	}
}
