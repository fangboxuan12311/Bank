package Bank7_10;

class Date {	//日期类
	private int year;		//年
	private	int month;		//月
	private	int day;		//日
	private	int totalDays;	//该日期是从公元元年1月1日开始的第几天
	private int DAYS_BEFORE_MONTH[]={ 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
	public Date(int year, int month, int day)	//用年、月、日构造日期
	{
		this.year=year;
		this.month=month;
		this.day=day;
		if (day <= 0 || day > getMaxDay()) {
			System.out.println("Invalid date: "); 
			show();
			Runtime.getRuntime().exit(1);
		}
		int years = year - 1;
		totalDays = years * 365 + years / 4 - years / 100 + years / 400+ DAYS_BEFORE_MONTH[month - 1] + day;
		if (isLeapYear() && month > 2) totalDays++;
	}
	public	int getYear()  { return year; }
	public	int getMonth()  { return month; }
	public	int getDay()  { return day; }
	public	int getMaxDay() 		//获得当月有多少天
	{
	    if (isLeapYear() && month == 2)
           return 29;
        else
           return DAYS_BEFORE_MONTH[month]- DAYS_BEFORE_MONTH[month - 1];
	}
	public	boolean isLeapYear() {	//判断当年是否为闰年
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}
	public 	void show() 			//输出当前日期
	{
		System.out.print(getYear()+"-"+getMonth()+"-"+getDay());
	}
	//计算两个日期之间差多少天	
	public 	int distance( Date date)  {
		return totalDays - date.totalDays;
	}
}

class Accumulator {	//将某个数值按日累加
	private Date lastDate;	//上次变更数值的时期
	private	double value;	//数值的当前值
	private	double sum;		//数值按日累加之和
	public	Accumulator(Date date, double value)//构造函数，date为开始累加的日期，value为初始值
	{
		   lastDate=date;
		   this.value=value;
		   sum=0;
	}
	public double getSum(Date date) {//获得到日期date的累加结果
		   return sum + value * date.distance(lastDate);
	}
	public 	void change( Date date, double value) {//在date将数值变更为value
		   sum = getSum(date);
		   lastDate = date;
		   this.value = value;
	}
	public void reset(Date date, double value) {//初始化，将日期变为date，数值变为value，累加器清零
		   lastDate = date;
		   this.value = value;
		   sum = 0;
	}
}

class Account { //账户类
    private String id;	//帐号
    private	double balance;	//余额
    private	static double total=0; //所有账户的总金额
//供派生类调用的构造函数，id为账户
    public  Account(Date date, String id)
    {
        this.id=id;
        balance=0;
	    date.show();	    
	    System.out.println("  #" +id + " created");  
    }

//记录一笔帐，date为日期，amount为金额，desc为说明
    protected void record(Date date, double amount, String desc)
    {
        amount = (amount * 100 + 0.5) / 100;	//保留小数点后两位
        balance += amount;
        total += amount;
        date.show();
        System.out.println("  #" +id+"  " + String.format("%.1f",amount) +"\t" + String.format("%.1f",balance) +"\t" + desc );
    }
//报告错误信息
    protected void error(String msg)
    {
        System.out.println("Error(#" + id + "): " +msg);
    }
    public String getId()  { return id; }
    public double getBalance(){ return balance; }
	public static double getTotal() { return total; }
	//显示账户信息
	void show()
	{
	     System.out.println(id + "\tBalance: " +String.format("%.1f",balance));
	}
}

class SavingsAccount extends Account { //储蓄账户类
    private Accumulator acc;	//辅助计算利息的累加器
    private	double rate;		//存款的年利率
    public SavingsAccount(Date date, String id, double rate)
    {
        super(date, id);
        this.rate=rate;
        acc=new Accumulator(date, 0);
    }
	double getRate() { return rate; }//存入现金
	
	void deposit(Date date, double amount, String desc)//取出现金
	{
        record(date, amount, desc);
        acc.change(date, getBalance());
	}
	void withdraw(Date date, double amount, String desc)
	{
	    if (amount > getBalance()) {
		error("not enough money");
        } 
        else {
		record(date, -amount, desc);
		acc.change(date, getBalance());
        }
	}
	//结算利息，每年1月1日调用一次该函数
	void settle(Date date)
	{
        double interest = acc.getSum(date) * rate/ date.distance(new Date(date.getYear() - 1, 1, 1));
        if (interest != 0)
            record(date, interest, "interest");
            acc.reset(date, getBalance());
	}
}

class CreditAccount extends Account { //信用账户类
    private Accumulator acc;	//辅助计算利息的累加器
    private	double credit;		//信用额度
    private	double rate;		//欠款的日利率
    private	double fee;			//信用卡年费

    private double getDebt()  {	//获得欠款额
		double balance = getBalance();
		return (balance < 0 ? balance : 0);
	}
    public CreditAccount(Date date, String id, double credit, double rate, double fee)//构造函数
    {
        super(date, id);
        this.credit=credit;
        this.rate=rate;
        this.fee=fee;
        acc=new Accumulator(date, 0);
    }
	double getCredit() { return credit; }
	double getRate()  { return rate; }
	double getFee()  { return fee; }
	double getAvailableCredit()  {	//获得可用信用
		if (getBalance() < 0) 
			return credit + getBalance();
		else
			return credit;
	}
	//存入现金
	void deposit(Date date, double amount, String desc)
	{
        record(date, amount, desc);
        acc.change(date, getDebt());
	}
	//取出现金
	void withdraw(Date date, double amount, String desc)
	{
        if (amount - getBalance() > credit) {
            error("not enough credit");
        } 
	    else {
		record(date, -amount, desc);
		acc.change(date, getDebt());
        }
	}
	//结算利息和年费，每月1日调用一次该函数
	void settle(Date date)
	{
        double interest = acc.getSum(date) * rate;
        if (interest != 0)
            record(date, interest, "interest");
        if (date.getMonth() == 1)
            record(date, -fee, "annual fee");
        acc.reset(date, getDebt());
	}

	void show()
	{
	    super.show();
        System.out.println("\tAvailable credit:" + String.format("%.1f",getAvailableCredit()));
	}
}
public class Bank8_8{
	public static void main(String args[])
	{
		Date date=new Date(2008, 11, 1);	//起始日期
		//建立几个账户
		SavingsAccount sa1=new SavingsAccount(date, "S3755217", 0.015);
		SavingsAccount sa2=new SavingsAccount(date, "02342342", 0.015);
		CreditAccount ca=new CreditAccount(date, "C5392394", 10000, 0.0005, 50);
		//11月份的几笔账目
		sa1.deposit(new Date(2008, 11, 5), 5000, "salary");
		ca.withdraw(new Date(2008, 11, 15), 2000, "buy a cell");
		sa2.deposit(new Date(2008, 11, 25), 10000, "sell stock 0323");
		//结算信用卡
		ca.settle(new Date(2008, 12, 1));
		//12月份的几笔账目
		ca.deposit(new Date(2008, 12, 1), 2016, "repay the credit");
		sa1.deposit(new Date(2008, 12, 5), 5500, "salary");
		//结算所有账户
		sa1.settle(new Date(2009, 1, 1));
		sa2.settle(new Date(2009, 1, 1));
		ca.settle(new Date(2009, 1, 1));
		//输出各个账户信息
		System.out.println();
		sa1.show();
		System.out.println();
		sa2.show(); 
		System.out.println();
		ca.show(); 
		System.out.println();
		System.out.println("Total: "+String.format("%.1f",Account.getTotal()));
	}
}

