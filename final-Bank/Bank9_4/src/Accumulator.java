//package Bank8_8;

public class Accumulator {
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
		  // return sum + value * date.distance(lastDate);
		return sum + value*date.distance(lastDate) ;
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
