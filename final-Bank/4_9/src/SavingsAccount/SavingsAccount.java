package SavingsAccount;
public class SavingsAccount {
	private int id;				//账号
	private	double balance;		//余额
	private	double rate;		//存款的年利率
	private	int lastDate;		//上次变更余额的时期
	private	double accumulation;	//余额按日累加之和

		//记录一笔帐，date为日期，amount为金额，desc为说明
	private	void record(int date, double amount)//获得到指定日期为止的存款金额按日累积值
		{
		    accumulation = accumulate(date);
	        lastDate = date;
	        amount = (amount * 100 + 0.5) / 100;	//保留小数点后两位
	        balance += amount;
	        System.out.println(date+"  #"+id+"  "+String.format("%.1f", amount)+"  "+String.format("%.1f", balance));
		}
	private	double accumulate(int date)  {
			return accumulation + balance * (date - lastDate);
		}
	
		
		public SavingsAccount(int date, int id, double rate)//构造函数
		{
		    this.id=id;
		    balance=0;
		    this.rate=rate;
		     lastDate=date;
		     accumulation=0; 
		     System.out.println(date +"  #" + id + "  is created"); 
		}
		int getId() { return id; }
		double getBalance() { return balance; }
		double getRate() { return rate; }

		//存入现金
		void deposit(int date, double amount)	//取出现金
		{
		    record(date, amount);
		}
		void withdraw(int date, double amount)//结算利息，每年1月1日调用一次该函数
		{
		    if (amount > getBalance())
			System.out.println("Error: not enough money"); 
		else
			record(date, -amount);
		}
		void settle(int date)//显示账户信息
		{
	        double interest = accumulate(date) * rate / 365;	//计算年息
	        if (interest != 0)
	            record(date, interest);
	        accumulation = 0;
		}
		void show()
		{
		    System.out.println("# "+id+" Balance:"+String.format("%.1f", balance));
		}

		public static void main(String args[]){
		//建立几个账户
		SavingsAccount sa0=new SavingsAccount(1, 21325302, 0.015);
		SavingsAccount sa1=new SavingsAccount(1, 58320212, 0.015);

		//几笔账目
		sa0.deposit(5, 5000);
		sa1.deposit(25, 10000);
		sa0.deposit(45, 5500);
		sa1.withdraw(60, 4000);
		sa1.withdraw(61,7000);//测试用例

		//开户后第90天到了银行的计息日，结算所有账户的年息
		sa0.settle(90);
		sa1.settle(90);

		//输出各个账户信息
		sa0.show();	
		sa1.show();	
	}

}
