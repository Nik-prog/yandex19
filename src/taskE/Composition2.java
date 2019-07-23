package taskE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Composition2 {
    static int [] backPack;//массив с результатами
	static int index2=-1;
	static int Kindex=-1;
    static int [] arr2;

 	public static boolean knapsack(int targ, int[] Ves, int[] work,int K, int index) {
 		int SIZE=Ves.length;
 		index2++;
		//if(work[index2]==targ || targ==1)
 		if(Kindex==0 && targ==1)
 		{
        	System.out.println(" " + work[--index2]+ " точное совпадение. Успех!");
			backPack = work;
			return true;
		}
		 while(index < SIZE) {
	            arr2=work;		// скопировать список для отката
	            arr2[index2]=Ves[index++];                  // добавить множитель
	            Kindex=K;
	            Kindex--;
	            display();
			    System.out.print("Цель " + targ+".");
	            if(arr2[index2]!=0 && targ%arr2[index2]==0) {
	            	if (Kindex>0) {
		                if(index==SIZE) {
		            		System.out.println(" " + work[index2]+" мало. Предметов больше нет");
		            	}else {
		            		System.out.println(" " + work[index2]+" мало.");
		            	}
			            if(knapsack(targ/arr2[index2],Ves,arr2,Kindex,index)) {
			                return true;
			            }
	            	}else if(Kindex==0) {
		            	if(targ/arr2[index2]==1) {
		            		if(knapsack(targ/arr2[index2],Ves,arr2,Kindex,index))
		            			return true;
		            	}
		            	else {
		            		System.out.println(" " + work[index2]+" Не подходит.");
		            		arr2[index2]=0;
		            		Kindex++;
		            	}
	            	}
	            }
	            else {

	        		if(index==SIZE)
	        			System.out.println(" " + work[index2]+" Не делится. Предметов больше нет");
	        		else
	        			System.out.println(" " + work[index2]+" Не делится.");
	            	arr2[index2]=0;
	            	Kindex++;
	            }
	        }
		index2--;
		return false;
	}
 	
 	public static void display() {
 		System.out.print("Набор ");
 		for(int s=0; s<=index2;s++)
        	//if(arr2[s]!=0)
        		System.out.print(" "+arr2[s]);
 		System.out.print(".  // ");
 	}
  
    
	public static void main(String[] args) throws IOException {
		String inpLine;
		StringTokenizer tk;
		File inp= new File("inputE.txt");
		BufferedReader br = new BufferedReader(new FileReader(inp));
		inpLine = br.readLine();
		tk = new StringTokenizer(inpLine);
		
		int SIZE = Integer.parseInt(tk.nextToken());
		int target = Integer.parseInt(tk.nextToken());
		int Ksize = Integer.parseInt(tk.nextToken());

		inpLine =  br.readLine();
		br.close();
		tk = new StringTokenizer(inpLine);
		
//		String[] arrS=inpLine.split(" ");
		int [] Weights= new int[SIZE];
		int[] arr=new int [SIZE];
		
		for(int i=0;i<SIZE;i++) {
			Weights[i]=Integer.parseInt(tk.nextToken());
		}
		
		for(int s: Weights)
        	System.out.print(s+" "); //печатаем исходный массив
		System.out.println(" ");
		
		if(target==0) {
			int count=0;
        	while(Weights[count++]!=0) {
        	}
        	if(count<=Ksize)
        		for (int i=0;i<Ksize;i++)
         			System.out.print(i+1+" ");
        	else {
        		for (int i=0;i<Ksize-1;i++)
         			System.out.print(i+1+" ");
        		System.out.print(count);
        	}
        }
		else if(knapsack(target,Weights, arr,Ksize, 0)) { //если результат найден, печатаем его
            System.out.print("Результат: ");
            for(int i=0;i<Ksize;i++)
            	System.out.print(backPack[i]+" ");
        }else {
            System.out.println("Результат не найден"); //результат не найден
        }
	}
}
