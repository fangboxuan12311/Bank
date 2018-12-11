//package Bank9_4;

public  abstract class Account {
    private String id;	
    private	double balance;	
    private	static double total=0; 
    protected Account(Date date, String id)
    {
        this .id=id;
        balance=0; 
        date.show();
        System.out.println(" #"+id+" created");
    }
    protected void record(Date date, double amount, String desc)
    {
        amount =  Math.floor(amount * 100 + 0.5) / 100;	//保留小数点后两位
        balance += amount;
        total += amount;
        date.show();
        System.out.println(" #" +id+ " " +amount+" " +balance + " " + desc);
    }
    protected void error(String msg) 
    {
    	System.out.println("Error(#"+id+"):"+msg);
    }
    public String getId() 
    { 
         return id; 
    }
    public	double getBalance()
    { 
        return balance; 
    }
    public	static double getTotal() 
    { 
        return total; 
    }
    public abstract void deposit (Date date, double amount, String desc);
    public abstract void withdraw(Date date, double amount, String desc) ;
    public	abstract void settle(Date date) ;
    public	void show() 
    {
    	System.out.println(id+"  Balance:"+String.format("%.2f",balance));
    }
}
