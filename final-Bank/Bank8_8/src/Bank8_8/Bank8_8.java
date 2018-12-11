package Bank8_8;
import java.io.*;
import java.util.*;
public class Bank8_8 {
	public static void main(String []args) throws IOException {
		 Date date=new Date(2008, 11, 1);	//��ʼ����
		//���������˻�
		 Account sa1=new SavingsAccount(date, "S3755217", 0.015);
		 Account sa2=new SavingsAccount(date, "02342342", 0.015);
		 Account ca=new CreditAccount(date, "C5392394", 10000, 0.0005, 50);
		 Account accounts[] = {sa1,sa2,ca};
		 int n = 3;	//�˻�����
		 System.out.println("(d)deposit (w)withdraw (s)show (c)change day (n)next month (e)exit");
		 System.out.println();
		 char cmd;
		 do {
			//��ʾ���ں��ܽ��
			 date.show();
			 System.out.println(" Tatal:"+Account.getTotal()+" command");
			 int index, day;
			 double amount;
			 String desc;
			 Scanner sc=new Scanner(System.in);
			 String s=sc.next();
			 cmd=s.charAt(0);
			 switch (cmd) {
			  case 'd':	//�����ֽ�
				  //Scanner sc=new Scanner(System.in);
				  index=sc.nextInt();//�Ӽ��̶�ȡһ��int �ͱ���
				  
				  //Scanner sc2=new Scanner(System.in);
				  amount=sc.nextDouble();//�Ӽ��̶�һ��double�ͱ���
				  
				  //Scanner sc3=new Scanner(System.in);
				  desc=sc.next();//�Ӽ��̶�һ��String�ͱ���
				  
				  accounts[index].deposit(date, amount, desc);
				  break;
				  
			  case 'w':	//ȡ���ֽ�
				  //Scanner sc4=new Scanner(System.in);
				  index=sc.nextInt();//�Ӽ��̶�ȡһ��int �ͱ���
				  
				  //Scanner sc5=new Scanner(System.in);
				  amount=sc.nextDouble();//�Ӽ��̶�һ��double�ͱ���
				  
				  //Scanner sc6=new Scanner(System.in);
				  desc=sc.nextLine();//�Ӽ��̶�һ��String�ͱ���
				  accounts[index].withdraw(date, amount, desc);
				  break;
				  
			  case 's':	//��ѯ���˻���Ϣ
				  for (int i = 0; i < n; i++) {
					  System.out.println("["+i+"]");
					  accounts[i].show();
					  System.out.println();
				  }
				  break;
			case 'c':	//�ı�����
				
				 //Scanner sc7=new Scanner(System.in);
				 day=sc.nextInt();//�Ӽ��̶�ȡһ��int �ͱ���
			
				 if (day < date.getDay())
					 System.out.println("You cannot specify a previous day");
				else if (day > date.getMaxDay())
					System.out.println("Invalid day");
				else
					date =new Date(date.getYear(), date.getMonth(), day);
				break;
			case 'n':	//�����¸���
				if (date.getMonth() == 12)
					date =new  Date(date.getYear() + 1, 1, 1);
				else
					date =new Date(date.getYear(), date.getMonth() + 1, 1);
				for (int i = 0; i < n; i++)
					accounts[i].settle(date);
				break;
			}
		} while (cmd != 'e');
	}
}

