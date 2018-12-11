//package Bank8_8;

public class CreditAccount extends Account{
    private Accumulator acc;	
    private	double credit;		
    private	double rate;		
    private	double fee;			
    private	double getDebt() 
	{	
		double balance = getBalance();
		return (balance < 0 ? balance : 0);
	}
    public CreditAccount(Date date, String id, double credit, double rate, double fee)
	{
	    super(date, id);
        this.credit=credit;
        this.rate=rate;
        this.fee=fee;
        acc=new Accumulator(date, 0);
	}
	public double getCredit()  
	{ 
	    return credit; 
    }
	public double getRate() 
    { 
	     return rate; 
    }
	public double getFee() 
    { 
        return fee; 
    }
	public double getAvailableCredit() 
	{	
		if (getBalance() < 0) 
			return credit + getBalance();
		else
			return credit;
	}
	public  void deposit(Date date, double amount, String desc)
	{
        record(date, amount, desc);
        acc.change(date, getDebt());
	}
	public  void withdraw(Date date, double amount,String desc)
	{
        if (amount - getBalance() > credit) 
        {
            error("not enough credit");
        } 
        else 
        {
		 record(date, -amount, desc);
		 acc.change(date, getDebt());
        }
	}
	public void settle (Date date)
	{
        double interest = acc.getSum(date) * rate/date.distance(new Date(date.getYear()-1,1,1));
        if (interest != 0)
            record(date, interest, "interest");
        if (date.getMonth() == 1)
            record(date, -fee, "annual fee");
        acc.reset(date, getDebt());
	}
	public  void show()
	{
        super.show();
        System.out.println(" Available credit:"+getAvailableCredit());
	}

}
