package taskF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;

public class SquareCircle   {

	public static void main(String[] args) throws IOException {
		String inpLine;
		StringTokenizer tk;
		File inp= new File("inputF.txt");
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(inp));
		inpLine = br.readLine();
		tk = new StringTokenizer(inpLine);
		
		int Nsize = Integer.parseInt(tk.nextToken());
		double R = Double.parseDouble(tk.nextToken());

		double [][] Coor= new double[Nsize][2];
		
		for(int i=0;i<Nsize;i++) {
			inpLine =  br.readLine();
			tk = new StringTokenizer(inpLine);
			Coor[i][0]=Double.parseDouble(tk.nextToken());
			Coor[i][1]=Double.parseDouble(tk.nextToken());
		}
		
		for(int i=0;i<Nsize;i++) {
			System.out.println(Coor[i][0]+" "+ Coor[i][1]);
		}
		double ExpectValue=Expectation(R, Coor);
		System.out.format(Locale.US,"%1.10f%n", ExpectValue);

	}
	
	public static double Expectation(double Radius, double[][] Coord) {
		double ExpectValue=0.0;
		ExpectValue=Math.PI*Radius*Radius;
		return ExpectValue;
	}

}
