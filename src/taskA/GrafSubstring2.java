package taskA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.StringTokenizer;

public class GrafSubstring2 {

	public static void main(String[] args) throws IOException {
		int nVert=0;
		int nEdge=0;
		
		String line;
		File inp= new File("inputA.txt");
		BufferedReader br = new BufferedReader(new FileReader(inp));
	//	StringTokenizer tk;
		int NWord = Integer.parseInt(br.readLine());//количество слов
		
		List<WordList> ListWord=new ArrayList<>();
		List<String> lWord=new ArrayList<>();
		
		for (int i = 0; i < NWord; ++i)
		{
			String[] subString;
			line = br.readLine();
			subString=line.split("");
			
//			tk = new StringTokenizer(line);
//			String cmd = tk.nextToken();
			
			for (int j=0;j<subString.length-3;j++) {
				int rep=1;
				boolean metka=false;
				
				String WordA=subString[j]+subString[j+1]+subString[j+2];
				String WordB=subString[j+1]+subString[j+2]+subString[j+3];
	//			newWord= {1,2,3,2,3,4};
				if (i==0 && j==0) {
					//if (WordA.equals(WordB)) nEdge++;
					WordList WL=new WordList(WordA,WordB,rep);
					ListWord.add(WL);
					lWord.add(WordA);
					if (!WordA.equals(WordB))
						lWord.add(WordB);
					nEdge++;
				}
				else {
					for (int kk=0;kk<ListWord.size();kk++) {
						if (!metka && WordA.equals(ListWord.get(kk).getListWord()[0]) && WordB.equals(ListWord.get(kk).getListWord()[1])) {
							ListWord.get(kk).setRepeatWord();
							metka=true;
							break;
						}
					}
					if (!metka) {
						
						WordList WL=new WordList(WordA,WordB,rep);
						ListWord.add(WL);
						nEdge++;
						int met=0;
						if(WordA.equals(WordB)) {
							for (String iter:lWord) {
								if(iter.equals(WordA)){
									break;
								}
							}
							lWord.add(WordA);
						}
						else {
							for (String iter:lWord) {
								if(iter.equals(WordA)){
									met=1;
									break;
								}
							}
							if(met==0) lWord.add(WordA);
							else met=0;
							
							for (String iter:lWord) {
								if(iter.equals(WordB)){
									met=1;
									break;
								}
							}
							if(met==0) lWord.add(WordB);
							else met=0;
						}
						
					}
					//break;
				}
				
			}
			
			
		}
		
		nVert=lWord.size();
		
		System.out.println(nVert);//количество вершин графа
		System.out.println(nEdge);
		for (int i=0;i<ListWord.size();i++) {
			System.out.println(ListWord.get(i).getListWord()[0]+" "+ListWord.get(i).getListWord()[1]+" "+ListWord.get(i).getrepeatWord());
			//System.out.println(ListWord.get(i)[0]+" "+ListWord.get(i)[1]+ " "+ ListWord.get(i)[0].equals(ListWord.get(i)[1]));
		}

	}

}


