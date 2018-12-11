package SavingsAccount;
public class SavingsAccount {
	private int id;				//�˺�
	private	double balance;		//���
	private	double rate;		//����������
	private	int lastDate;		//�ϴα������ʱ��
	private	double accumulation;	//�����ۼ�֮��
	private static double total=0;
		//��¼һ���ʣ�dateΪ���ڣ�amountΪ��descΪ˵��
	private	void record(int date, double amount)//��õ�ָ������Ϊֹ�Ĵ������ۻ�ֵ
		{
		    accumulation = accumulate(date);
	        lastDate = date;
	        amount = (amount * 100 + 0.5) / 100;	//����С�������λ
	        balance += amount;
	        total+=amount;
	        System.out.println(date+"  #"+id+"  "+amount+"  "+balance);
		}
	private	double accumulate(int date)  {
			return accumulation + balance * (date - lastDate);
		}
	
		
		public SavingsAccount(int date, int id, double rate)//���캯��
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
		public static double getTotal() { return total; }
		double getRate() { return rate; }

		//�����ֽ�
		void deposit(int date, double amount)	//ȡ���ֽ�
		{
		    record(date, amount);
		}
		void withdraw(int date, double amount)//������Ϣ��ÿ��1��1�յ���һ�θú���
		{
		    if (amount > getBalance())
			System.out.println("Error: not enough money"); 
		else
			record(date, -amount);
		}
		void settle(int date)//��ʾ�˻���Ϣ
		{
	        double interest = accumulate(date) * rate / 365;	//������Ϣ
	        if (interest != 0)
	            record(date, interest);
	        accumulation = 0;
		}
		void show()
		{
		    System.out.println("# "+id+" Balance:"+balance);
		}

		public static void main(String args[]){
		//���������˻�
		SavingsAccount sa0=new SavingsAccount(1, 21325302, 0.015);
		SavingsAccount sa1=new SavingsAccount(1, 58320212, 0.015);

		//������Ŀ
		sa0.deposit(5, 5000);
		sa1.deposit(25, 10000);
		sa0.deposit(45, 5500);
		sa1.withdraw(60, 4000);

		//�������90�쵽�����еļ�Ϣ�գ����������˻�����Ϣ
		sa0.settle(90);
		sa1.settle(90);

		//��������˻���Ϣ
		sa0.show();	
		sa1.show();	
		System.out.println("Total:"+SavingsAccount.getTotal());
	}

}