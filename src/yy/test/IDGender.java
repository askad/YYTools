package yy.test;

public class IDGender {

	static int[] muilt = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
	static String[] arr = {"1","0","X","9","8","7","6","5","4","3","2"};
	public static void main(String[]arg){
		String idNo = "12010119850708500";
		int sum=0;
		for(int i=0;i<idNo.length();i++){
			sum+=(Integer.parseInt(idNo.substring(i,i+1))*muilt[i]);
		}
		int i = sum%11;
		System.out.print(idNo+arr[i]);
	}
}
