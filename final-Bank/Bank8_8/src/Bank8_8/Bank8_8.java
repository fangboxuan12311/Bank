package Bank8_8;
import java.io.*;
import java.util.*;
public class Bank8_8 {
	public static void main(String []args) throws IOException {
		 Date date=new Date(2008, 11, 1);	//起始日期
		//建立几个账户
		 Account sa1=new SavingsAccount(date, "S3755217", 0.015);
		 Account sa2=new SavingsAccount(date, "02342342", 0.015);
		 Account ca=new CreditAccount(date, "C5392394", 10000, 0.0005, 50);
		 Account accounts[] = {sa1,sa2,ca};
		 int n = 3;	//账户总数
		 System.out.println("(d)deposit (w)withdraw (s)show (c)change day (n)next month (e)exit");
		 System.out.println();
		 char cmd;
		 do {
			//显示日期和总金额
			 date.show();
			 System.out.println(" Tatal:"+Account.getTotal()+" command");
			 int index, day;
			 double amount;
			 String desc;
			 Scanner sc=new Scanner(System.in);
			 String s=sc.next();
			 cmd=s.charAt(0);
			 switch (cmd) {
			  case 'd':	//存入现金
				  //Scanner sc=new Scanner(System.in);
				  index=sc.nextInt();//从键盘读取一个int 型变量
				  
				  //Scanner sc2=new Scanner(System.in);
				  amount=sc.nextDouble();//从键盘读一个double型变量
				  
				  //Scanner sc3=new Scanner(System.in);
				  desc=sc.next();//从键盘读一个String型变量
				  
				  accounts[index].deposit(date, amount, desc);
				  break;
				  
			  case 'w':	//取出现金
				  //Scanner sc4=new Scanner(System.in);
				  index=sc.nextInt();//从键盘读取一个int 型变量
				  
				  //Scanner sc5=new Scanner(System.in);
				  amount=sc.nextDouble();//从键盘读一个double型变量
				  
				  //Scanner sc6=new Scanner(System.in);
				  desc=sc.nextLine();//从键盘读一个String型变量
				  accounts[index].withdraw(date, amount, desc);
				  break;
				  
			  case 's':	//查询各账户信息
				  for (int i = 0; i < n; i++) {
					  System.out.println("["+i+"]");
					  accounts[i].show();
					  System.out.println();
				  }
				  break;
			case 'c':	//改变日期
				
				 //Scanner sc7=new Scanner(System.in);
				 day=sc.nextInt();//从键盘读取一个int 型变量
			
				 if (day < date.getDay())
					 System.out.println("You cannot specify a previous day");
				else if (day > date.getMaxDay())
					System.out.println("Invalid day");
				else
					date =new Date(date.getYear(), date.getMonth(), day);
				break;
			case 'n':	//进入下个月
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

