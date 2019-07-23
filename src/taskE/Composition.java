package taskE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Composition {
	
//	final static int[] weights = {11, 8, 7, 6, 5};
 //   static int SIZE = weights.length;
    static int [] backPack;//массив с результатами
	static int index2=-1;
	static int Kindex=-1;
    static int [] arr2;

 	public static boolean knapsack(int targ, int[] Ves, int[] work,int K, int index) {
 		int SIZE=Ves.length;
 		index2++;
 		if(Kindex==0 && targ==1)
 		{
			backPack = work;
			return true;
		}
		 while(index < SIZE) {
	            arr2=work;		// скопировать список для отката
	            arr2[index2]=1+index++;                  // добавить множитель
	            Kindex=K;
	            Kindex--;
	           display();
	            if(Ves[arr2[index2]-1]!=0 && targ%Ves[arr2[index2]-1]==0 && Kindex>0) {
		            if(knapsack(targ/Ves[arr2[index2]-1],Ves,arr2,Kindex,index)) {
		                return true;
		            }
	            }else if(Ves[arr2[index2]-1]!=0 && targ%Ves[arr2[index2]-1]==0 && Kindex==0) {
	            	if(targ/Ves[arr2[index2]-1]==1) {
	            		if(knapsack(targ/Ves[arr2[index2]-1],Ves,arr2,Kindex,index))
	            			return true;
	            	}
	            	else {
	            		arr2[index2]=0;
	            		Kindex++;
	            	}
	            }
	            else {
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
        	if(arr2[s]!=0)
        		System.out.print(" "+arr2[s]);
 		System.out.println("");
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
		
/*		for(int s: Weights)
        	System.out.print(s+" "); //печатаем исходный массив
		System.out.println(" ");*/
		
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
            for(int i=0;i<Ksize;i++)
            	System.out.print(backPack[i]+" ");
        } 
        else{
            System.out.println("Результат не найден"); //результат не найден
        }
	}
}
