package Bank7_10;

class Date {	//������
	private int year;		//��
	private	int month;		//��
	private	int day;		//��
	private	int totalDays;	//�������Ǵӹ�ԪԪ��1��1�տ�ʼ�ĵڼ���
	private int DAYS_BEFORE_MONTH[]={ 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
	public Date(int year, int month, int day)	//���ꡢ�¡��չ�������
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
	public	int getMaxDay() 		//��õ����ж�����
	{
	    if (isLeapYear() && month == 2)
           return 29;
        else
           return DAYS_BEFORE_MONTH[month]- DAYS_BEFORE_MONTH[month - 1];
	}
	public	boolean isLeapYear() {	//�жϵ����Ƿ�Ϊ����
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}
	public 	void show() 			//�����ǰ����
	{
		System.out.print(getYear()+"-"+getMonth()+"-"+getDay());
	}
	//������������֮��������	
	public 	int distance( Date date)  {
		return totalDays - date.totalDays;
	}
}

class Accumulator {	//��ĳ����ֵ�����ۼ�
	private Date lastDate;	//�ϴα����ֵ��ʱ��
	private	double value;	//��ֵ�ĵ�ǰֵ
	private	double sum;		//��ֵ�����ۼ�֮��
	public	Accumulator(Date date, double value)//���캯����dateΪ��ʼ�ۼӵ����ڣ�valueΪ��ʼֵ
	{
		   lastDate=date;
		   this.value=value;
		   sum=0;
	}
	public double getSum(Date date) {//��õ�����date���ۼӽ��
		   return sum + value * date.distance(lastDate);
	}
	public 	void change( Date date, double value) {//��date����ֵ���Ϊvalue
		   sum = getSum(date);
		   lastDate = date;
		   this.value = value;
	}
	public void reset(Date date, double value) {//��ʼ���������ڱ�Ϊdate����ֵ��Ϊvalue���ۼ�������
		   lastDate = date;
		   this.value = value;
		   sum = 0;
	}
}

class Account { //�˻���
    private String id;	//�ʺ�
    private	double balance;	//���
    private	static double total=0; //�����˻����ܽ��
//����������õĹ��캯����idΪ�˻�
    public  Account(Date date, String id)
    {
        this.id=id;
        balance=0;
	    date.show();	    
	    System.out.println("  #" +id + " created");  
    }

//��¼һ���ʣ�dateΪ���ڣ�amountΪ��descΪ˵��
    protected void record(Date date, double amount, String desc)
    {
        amount = (amount * 100 + 0.5) / 100;	//����С�������λ
        balance += amount;
        total += amount;
        date.show();
        System.out.println("  #" +id+"  " + String.format("%.1f",amount) +"\t" + String.format("%.1f",balance) +"\t" + desc );
    }
//���������Ϣ
    protected void error(String msg)
    {
        System.out.println("Error(#" + id + "): " +msg);
    }
    public String getId()  { return id; }
    public double getBalance(){ return balance; }
	public static double getTotal() { return total; }
	//��ʾ�˻���Ϣ
	void show()
	{
	     System.out.println(id + "\tBalance: " +String.format("%.1f",balance));
	}
}

class SavingsAccount extends Account { //�����˻���
    private Accumulator acc;	//����������Ϣ���ۼ���
    private	double rate;		//����������
    public SavingsAccount(Date date, String id, double rate)
    {
        super(date, id);
        this.rate=rate;
        acc=new Accumulator(date, 0);
    }
	double getRate() { return rate; }//�����ֽ�
	
	void deposit(Date date, double amount, String desc)//ȡ���ֽ�
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
	//������Ϣ��ÿ��1��1�յ���һ�θú���
	void settle(Date date)
	{
        double interest = acc.getSum(date) * rate/ date.distance(new Date(date.getYear() - 1, 1, 1));
        if (interest != 0)
            record(date, interest, "interest");
            acc.reset(date, getBalance());
	}
}

class CreditAccount extends Account { //�����˻���
    private Accumulator acc;	//����������Ϣ���ۼ���
    private	double credit;		//���ö��
    private	double rate;		//Ƿ���������
    private	double fee;			//���ÿ����

    private double getDebt()  {	//���Ƿ���
		double balance = getBalance();
		return (balance < 0 ? balance : 0);
	}
    public CreditAccount(Date date, String id, double credit, double rate, double fee)//���캯��
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
	double getAvailableCredit()  {	//��ÿ�������
		if (getBalance() < 0) 
			return credit + getBalance();
		else
			return credit;
	}
	//�����ֽ�
	void deposit(Date date, double amount, String desc)
	{
        record(date, amount, desc);
        acc.change(date, getDebt());
	}
	//ȡ���ֽ�
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
	//������Ϣ����ѣ�ÿ��1�յ���һ�θú���
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
		Date date=new Date(2008, 11, 1);	//��ʼ����
		//���������˻�
		SavingsAccount sa1=new SavingsAccount(date, "S3755217", 0.015);
		SavingsAccount sa2=new SavingsAccount(date, "02342342", 0.015);
		CreditAccount ca=new CreditAccount(date, "C5392394", 10000, 0.0005, 50);
		//11�·ݵļ�����Ŀ
		sa1.deposit(new Date(2008, 11, 5), 5000, "salary");
		ca.withdraw(new Date(2008, 11, 15), 2000, "buy a cell");
		sa2.deposit(new Date(2008, 11, 25), 10000, "sell stock 0323");
		//�������ÿ�
		ca.settle(new Date(2008, 12, 1));
		//12�·ݵļ�����Ŀ
		ca.deposit(new Date(2008, 12, 1), 2016, "repay the credit");
		sa1.deposit(new Date(2008, 12, 5), 5500, "salary");
		//���������˻�
		sa1.settle(new Date(2009, 1, 1));
		sa2.settle(new Date(2009, 1, 1));
		ca.settle(new Date(2009, 1, 1));
		//��������˻���Ϣ
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

