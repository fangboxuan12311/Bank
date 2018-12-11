//package Bank8_8;

public class Date {
	private int year;		//��
	private	int month;		//��
	private	int day;		//��
	private	int totalDays=0;	//�������Ǵӹ�ԪԪ��1��1�տ�ʼ�ĵڼ���
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
