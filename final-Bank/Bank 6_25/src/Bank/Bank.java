package Bank;

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


class SavingsAccount {
     	private String id;		//帐号
		private	double balance;		//余额
		private	double rate;		//存款的年利率
		private	Date lastDate;		//上次变更余额的时期
		private	double accumulation;	//余额按日累加之和
		public	static double total=0;	//所有账户的总金额
			//记录一笔帐，date为日期，amount为金额，desc为说明
		private	void record( Date date, double amount, String desc)//报告错误信息
		{
			accumulation = accumulate(date);
			lastDate = date;
			amount = (amount * 100 + 0.5) / 100;	//保留小数点后两位
			balance += amount;
			total += amount;
			date.show();
			System.out.println("  #"+id+"  "+String.format("%.1f", amount)+"  "+String.format("%.1f",balance)+" "+desc);
		}
			
		private	void error(String msg)
		{
			System.out.println("Error(#"+id+"):"+msg);
		}
			
		private	double accumulate( Date date) {
				return accumulation + balance * date.distance(lastDate);
			}
		
		public 	SavingsAccount( Date date,  String id, double rate)
			{
				this.id=id;
				balance=0;
				this.rate=rate;
				lastDate=date;
				accumulation=0;
				date.show();
				System.out.println("  #"+id+" created");
				
			}
			String getId() { return id; }
			double getBalance() { return balance; }
			double getRate()  { return rate; }
			static double getTotal() { return total; }

			//存入现金
			void deposit(Date date, double amount,String desc)
			{
				record(date, amount, desc);
			}
			//取出现金
			void withdraw(Date date, double amount, String desc)
			{
				if (amount > getBalance())
					error("not enough money");
				else
					record(date, -amount, desc);
				
				
			}
			//结算利息，每年1月1日调用一次该函数
			void settle( Date date)
			{
				double interest = accumulate(date) * rate/ date.distance(new Date(date.getYear()-1,1,1));
					if (interest != 0)
						record(date, interest, "interest");
					accumulation = 0;
			}
			//显示账户信息
			void show()
			{
				System.out.println(id+" Balance:"+String.format("%.1f",balance));
			}
}	

public class Bank {

	public static void main(String[] args) {
			Date date=new Date(2008, 11, 1);	//起始日期
			//建立几个账户
			SavingsAccount s1=new SavingsAccount(date, "S3755217", 0.015);
			SavingsAccount s2=new SavingsAccount(date, "02342342", 0.015);
			System.out.println();
			SavingsAccount accounts[] = {s1,s2};
		    int n = 2; //账户总数
			//11月份的几笔账目
			accounts[0].deposit(new Date(2008, 11, 5), 5000, "salary");
			accounts[1].deposit(new Date(2008, 11, 25), 10000, "sell stock 0323");
			//12月份的几笔账目
			accounts[0].deposit(new Date(2008, 12, 5), 5500, "salary");
			accounts[1].withdraw(new Date(2008, 12, 20), 4000, "buy a laptop");
			System.out.println();

			//结算所有账户并输出各个账户信息
			for (int i = 0; i < n; i++) {
				accounts[i].settle(date=new Date(2009, 1, 1));
				accounts[i].show();
			}
			System.out.println("Total:"+ String.format("%.1f",SavingsAccount.getTotal()));
			Date date1=new Date(2008,0,1);
			//System.out.println(date1.getMaxDay());

	}
}