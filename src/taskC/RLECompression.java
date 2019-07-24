package taskC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RLECompression {

	public static void main(String[] args) throws IOException {
		String[] inpLine;
		File inp= new File("inputC.txt");
		BufferedReader br = new BufferedReader(new FileReader(inp));
		String str=br.readLine();	//������, ���������� � �������������� RLE ������
		//String[] StrLine = str.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //��������� ������ �� ����� � ������� �� �������
		//������ ������ �����- ���� �����, ������ ������ �����- ���� ���������.
		
		//Matcher match = Pattern.compile("[0-9]+|[a-z]").matcher(str);//��������� ������ �� ����� � ������� �� �������
		Matcher match = Pattern.compile("[a-z]").matcher(str);//��������� ������ �� ����� � ������� �� �������
		List<SubStr> SubStringLine = new ArrayList<SubStr>();
		
		int strLength=0;
		int strSumS=0;
		long strSumT=0;
		while(match.find()) {		
			if((match.end()-strSumS)==1) {
				strLength=1;	//���� �������� ���, ����� 1
			}
			else {
				strLength=Integer.parseInt(str.substring(strSumS, match.end()-1));// ���� ������� ����, ����� �� ���-��
			}
			strSumT=strSumT+strLength;
			strSumS=match.end();
			SubStr symbol= new SubStr(strLength,match.group(),strSumS,strSumT);
			SubStringLine.add(symbol); //
		//	System.out.println(strLength+" "+strSumS+" "+strSumT);
//			StrLine.add(match.group());
		}		

		
		int Nsize = Integer.parseInt(br.readLine()); //���-�� ��������	
		long [][] Request= new long[Nsize][2]; //������ � ���������
		
		for(int i=0;i<Nsize;i++) {
			inpLine = br.readLine().split(" ");			
			Request[i][0]=Long.parseLong(inpLine[0]);
			Request[i][1]=Long.parseLong(inpLine[1]);
		}
		br.close();
		
	//	System.out.println("����");
	//	System.out.println(str);
		
	//	for(int i=0;i<Nsize;i++) {
	//		System.out.println(Request[i][0]+" "+Request[i][1]);
	//	}
	//	System.out.println("�����");
		long startTime1 = System.nanoTime();
		
		for (int i=0;i<Nsize;i++) {
			RequestAnswer(Request[i][0],Request[i][1],SubStringLine);
		}	

	    long stopTime1 = System.nanoTime();
	    System.out.println((stopTime1 - startTime1)/1000 + " ������������");	    
 
//		System.out.format(Locale.US,"%1.10f%n", ExpectValue);
	}
	
	public static void RequestAnswer(long Start, long End, List<SubStr> SubStringLine) { //�����, ������� ��������� �������
		int count=0;
		while(Start>SubStringLine.get(count).getStrSumT()) {
			count++;
		}
		int count2=count;
		while(End>SubStringLine.get(count2).getStrSumT()) {
			count2++;
		}
		
		
		int answer=0;
		if (count==count2) { //����� ������ � ����� ������� � ����� �����
			if (End==Start) {
				answer=1;
			}
			else {
				String addAnswer=""+(End-Start+1);
				answer=addAnswer.length()+1;
			}			
		}
		else {
			if(count>0 && Start==SubStringLine.get(count-1).getStrSumT()+1 ||count==0 & Start==1) { //������ ������� � ������ ������
				int SubAnswer;
				if (Start==1) {
					SubAnswer=0;
				}
				else {
					SubAnswer=SubStringLine.get(count-1).getStrSumS();
				}				
				
				if(End==SubStringLine.get(count2-1).getStrSumT()+1) {//����� ������� � ������ �����
					answer=(SubStringLine.get(count2-1).getStrSumS()-SubAnswer)+1;					
				}
				else if(End<SubStringLine.get(count2).getStrSumT()) {//����� ������� � �������� �����
					String addAnswer1=""+(End-SubStringLine.get(count2-1).getStrSumT());
					answer=(SubStringLine.get(count2-1).getStrSumS()-SubAnswer)+addAnswer1.length()+1;
				}
				else {								//����� ������� � ����� �����
					answer=(SubStringLine.get(count2).getStrSumS()-SubAnswer);
				}
			}
			else {
				String addAnswer2;
				if(Start==SubStringLine.get(count).getStrSumT()) {//������ ������� � ����� ������
					addAnswer2="";
				}
				else {			//������ ������� � �������� ������
					addAnswer2=""+(SubStringLine.get(count).getStrSumT()-Start+1);
				}
				
				if(End==SubStringLine.get(count2-1).getStrSumT()+1) {//����� ������� � ������ �����
					answer=(SubStringLine.get(count2-1).getStrSumS()-SubStringLine.get(count).getStrSumS())+addAnswer2.length()+2;	
				}
				else if(End<SubStringLine.get(count2).getStrSumT()) {//����� ������� � �������� �����
					String addAnswer1=""+(End-SubStringLine.get(count2-1).getStrSumT());
					answer=(SubStringLine.get(count2-1).getStrSumS()-SubStringLine.get(count).getStrSumS())+addAnswer2.length()+addAnswer1.length()+2;					
				}
				else {							//����� ������� � ����� �����
					answer=SubStringLine.get(count2).getStrSumS()-SubStringLine.get(count).getStrSumS()+addAnswer2.length()+1;					
				}				
			}
		}	
		System.out.println(answer);
	//	System.out.println(Start+" "+End+" | "+ answer);
	//	System.out.println(count+" "+count2);
		//System.out.println(SubStringLine.get(count2).getStrSumS()-SubStringLine.get(count-1).getStrSumS());
	}
}

//����� � ������� �� ������
class SubStr{
	private String Symb; //������
	private int nSymb; //���-�� ������ ��������
	private int strSumS; //����� ������ ������ ����� 
	private long strSumT; //����� �������� ������ ����� 
	
	public SubStr(int nsymb, String Symbol, int sumS, long sumT) {
		nSymb=nsymb;
		Symb=Symbol;
		strSumS=sumS;
		strSumT=sumT;
	}
	public String getSymb() {
		return Symb;
	}
	public int getN() {
		return nSymb;
	}
	public int getStrSumS() {
		return strSumS;
	}
	public long getStrSumT() {
		return strSumT;
	}
}
