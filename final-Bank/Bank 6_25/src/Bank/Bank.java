package Bank;

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


class SavingsAccount {
     	private String id;		//�ʺ�
		private	double balance;		//���
		private	double rate;		//����������
		private	Date lastDate;		//�ϴα������ʱ��
		private	double accumulation;	//�����ۼ�֮��
		public	static double total=0;	//�����˻����ܽ��
			//��¼һ���ʣ�dateΪ���ڣ�amountΪ��descΪ˵��
		private	void record( Date date, double amount, String desc)//���������Ϣ
		{
			accumulation = accumulate(date);
			lastDate = date;
			amount = (amount * 100 + 0.5) / 100;	//����С�������λ
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

			//�����ֽ�
			void deposit(Date date, double amount,String desc)
			{
				record(date, amount, desc);
			}
			//ȡ���ֽ�
			void withdraw(Date date, double amount, String desc)
			{
				if (amount > getBalance())
					error("not enough money");
				else
					record(date, -amount, desc);
				
				
			}
			//������Ϣ��ÿ��1��1�յ���һ�θú���
			void settle( Date date)
			{
				double interest = accumulate(date) * rate/ date.distance(new Date(date.getYear()-1,1,1));
					if (interest != 0)
						record(date, interest, "interest");
					accumulation = 0;
			}
			//��ʾ�˻���Ϣ
			void show()
			{
				System.out.println(id+" Balance:"+String.format("%.1f",balance));
			}
}	

public class Bank {

	public static void main(String[] args) {
			Date date=new Date(2008, 11, 1);	//��ʼ����
			//���������˻�
			SavingsAccount s1=new SavingsAccount(date, "S3755217", 0.015);
			SavingsAccount s2=new SavingsAccount(date, "02342342", 0.015);
			System.out.println();
			SavingsAccount accounts[] = {s1,s2};
		    int n = 2; //�˻�����
			//11�·ݵļ�����Ŀ
			accounts[0].deposit(new Date(2008, 11, 5), 5000, "salary");
			accounts[1].deposit(new Date(2008, 11, 25), 10000, "sell stock 0323");
			//12�·ݵļ�����Ŀ
			accounts[0].deposit(new Date(2008, 12, 5), 5500, "salary");
			accounts[1].withdraw(new Date(2008, 12, 20), 4000, "buy a laptop");
			System.out.println();

			//���������˻�����������˻���Ϣ
			for (int i = 0; i < n; i++) {
				accounts[i].settle(date=new Date(2009, 1, 1));
				accounts[i].show();
			}
			System.out.println("Total:"+ String.format("%.1f",SavingsAccount.getTotal()));
			Date date1=new Date(2008,0,1);
			//System.out.println(date1.getMaxDay());

	}
}