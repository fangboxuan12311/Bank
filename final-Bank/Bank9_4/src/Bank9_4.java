//package Bank8_8;
import java.io.*;
import java.util.*;
public class Bank9_4 {
	public static void main(String []args) throws IOException {
		 Date date=new Date(2008, 11, 1);	//起始日期
		//建立几个账户
		 ArrayList <Account> accounts=new ArrayList <Account>(0);
		 System.out.println("(a)add amount(d)deposit (w)withdraw (s)show (c)change day (n)next month (e)exit");
		 char cmd;
		 Scanner sc=new Scanner(System.in);
		 do {
			//显示日期和总金额
			 date.show();
			 System.out.println(" Tatal:"+Account.getTotal()+" command");
			 int index, day;
			 char type;
			 double amount,credit,rate,fee;
			 String desc,id;
			 
			 String s=sc.next();
			 cmd=s.charAt(0);
			 Account account;
			 switch (cmd) {
			  case 'a':
				  type=sc.next().charAt(0);
				  id=sc.next();
				  if(type=='s'){
					  rate=sc.nextDouble();
					  account=new SavingsAccount(date,id,rate);
				
				  }
				  else {
					  credit=sc.nextDouble();
					  rate=sc.nextDouble();
					  fee=sc.nextDouble();
					  account=new CreditAccount(date,id,credit,rate,fee);
				
				  }
				  accounts.add(account);
				  accounts.trimToSize();
				  break;
				  
			  case 'd':	//存入现金
				  index=sc.nextInt();
				  amount=sc.nextDouble();
				  desc=sc.next();			  
				  accounts.get(index).deposit(date, amount, desc);
				  break;
				  
			  case 'w':	//取出现金
				  index=sc.nextInt();
				  amount=sc.nextDouble();
				  desc=sc.nextLine();
				  accounts.get(index).withdraw(date, amount, desc);
				  break;
				  
			  case 's':	//查询各账户信息
				  for (int i = 0; i < accounts.size(); i++) {
					  System.out.println("["+i+"]");
					  accounts.get(i).show();
				  }
				  break;
			case 'c':	//改变日期
				 day=sc.nextInt();
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
				for (int i = 0; i < accounts.size(); i++)
					accounts.get(i).settle(date);
				break;
			}
		} while (cmd != 'e');
	}
}

