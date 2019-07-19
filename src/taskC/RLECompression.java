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
		String str=br.readLine();	//строка, написанная с использованием RLE сжатия
		//String[] StrLine = str.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //разбивает строку на числа и символы по порядку
		//подряд идущие цифры- одно число, подряд идущие буквы- одна подстрока.
		
		//Matcher match = Pattern.compile("[0-9]+|[a-z]").matcher(str);//разбивает строку на числа и символы по порядку
		Matcher match = Pattern.compile("[a-z]").matcher(str);//разбивает строку на числа и символы по порядку
//		List<String> StrLine = new ArrayList<String>();
		List<SubStr> SubStringLine = new ArrayList<SubStr>();
		
		int strLength=0;
		int strSum=0;
		//int count=0;
		while(match.find()) {		
			if((match.end()-strSum)==1) {
				strLength=1;	//если повторов нет, пишем 1
			}
			else {
				strLength=Integer.parseInt(str.substring(strSum, match.end()-1));// если повторы есть, пишем их кол-во
			}
			strSum=match.end();
			SubStr symbol= new SubStr(strLength,match.group(),strSum);
			SubStringLine.add(symbol); //
//			System.out.println(strLength+" "+strSum);
			//count++;
//			StrLine.add(match.group());
		}
		

		int Nsize = Integer.parseInt(br.readLine()); //кол-во запросов	
		int [][] Request= new int[Nsize][2]; //массив с запросами
		
		for(int i=0;i<Nsize;i++) {
			inpLine = br.readLine().split(" ");			
			Request[i][0]=Integer.parseInt(inpLine[0]);
			Request[i][1]=Integer.parseInt(inpLine[1]);
		}
		br.close();
		
		System.out.println("Ввод");
		System.out.println(str);
		
		for(int i=0;i<Nsize;i++) {
			System.out.println(Request[i][0]+" "+Request[i][1]);
		}
		System.out.println("Вывод");
//		System.out.format(Locale.US,"%1.10f%n", ExpectValue);

	}
}

//класс с данными по встречам
class SubStr{
	private String Symb; //символ
	private int nSymb; //кол-во сжатых символов
	private int strSum; //длина строки всего 
	
	public SubStr(int nsymb, String Symbol, int sum) {
		nSymb=nsymb;
		Symb=Symbol;
		strSum=sum;
	}
	public String getSymb() {
		return Symb;
	}
	public int getN() {
		return nSymb;
	}
	public int getStrSum() {
		return strSum;
	}
}
