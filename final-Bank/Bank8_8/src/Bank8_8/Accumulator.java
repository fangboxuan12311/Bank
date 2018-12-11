package Bank8_8;

public class Accumulator {
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
