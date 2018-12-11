package Bank8_8;

public class SavingsAccount extends Account {
	 private Accumulator acc;	
	 private double rate;		
	 public SavingsAccount(Date date, String id, double rate)
	 {
		 super(date, id);
	     this.rate=rate;
	     acc=new Accumulator (date, 0);
	 }
	    public double getRate() 
	    { 
	        return rate; 
	    }
	    public  void deposit(Date date, double amount, String desc)
	    {
	        record(date, amount, desc);
	        acc.change(date, getBalance());
	    }
	    public  void withdraw(Date date, double amount, String desc)
	    {
	        if (amount > getBalance()) {
	            error("not enough money");
	            } 
	        else {
	            record(date, -amount, desc);
	            acc.change(date, getBalance());
	        }
	    }
	    public  void settle(Date date)
	    {
	        if (date.getMonth() == 1) 
	        {	//每年的一月计算一次利息
	            double interest = acc.getSum(date) * rate/ (date.getDay() - new Date(date.getYear() - 1, 1, 1).getDay());
	            if (interest != 0)  
	                record(date, interest, "interest");
	            acc.reset(date, getBalance());
	        }
	        }
}