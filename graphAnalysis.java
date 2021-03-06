package graphAnalysis;
import java.io.*;
import java.util.*;

public class graphAnalysis {

	public static boolean decayTrend (ArrayList<Double> temp){
		
		double ratio = 0.0; 
		
		// finds the initial base ratio
		if ((ratio = temp.get(1)/temp.get(0)) < 0.95){
					
			//double hiBuffer;
			double loBuffer;
					
			// adjusts hiBuffer so it remains under 1.0
			if (ratio >= 0.9){
				//hiBuffer = 0.98;
				loBuffer = ratio-0.1;
			}
					
			// adjusts loBuffer so it remains above 0.0
			else if (ratio <= 0.1){
				loBuffer = 0.2;
				//hiBuffer = ratio+0.1;
			}
					
			// sets a margin of error of +- 0.1
			else {
				//hiBuffer = ratio+0.1;
				loBuffer = ratio-0.1;
			}
		
			// checks the ratios between the remaining values to see if they follow the same trend 
			for (int i = 1; i < temp.size()-1; i++){
				
				if ((temp.get(i+1)/temp.get(i))>=1.02 || (temp.get(i+1)/temp.get(i))<=loBuffer){
					return false;
				}
			}
			
			return true;
		
		}
		
		else {
			return false;
		}
	
	}
	
	public static void printSymbol (int num) throws IOException{
		
		File file = new File("symbols.txt");
		
		FileReader readFile = new FileReader(file);
		BufferedReader read = new BufferedReader (readFile);
		String symbol = "";
		
		for (int i = 0; i < num; i++){
			read.readLine();
		}
		
		System.out.println(read.readLine());
	
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		File file = new File("prices.txt");
		
		// initializing variables
		FileReader readFile = new FileReader(file);
		BufferedReader read = new BufferedReader (readFile);
		String lineOfText = "";
		ArrayList<ArrayList<Double>> prices = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> temp = new ArrayList<Double>();
		StringTokenizer s;
		Double parseDouble;
		int n = 0;
		s = new StringTokenizer(lineOfText);
		
		// adds values from file to a 2d arraylist
		while ((lineOfText = read.readLine()) != null){
			
			s = new StringTokenizer(lineOfText);
			prices.add(new ArrayList<Double>());
			
			//System.out.println(lineOfText);
			
			// fills the array and parses strings to doubles
			while (s.hasMoreTokens()){	
				parseDouble = Double.parseDouble(s.nextToken());
				prices.get(n).add(parseDouble);
			}
			
			n++; 
		}	
		
		System.out.println("----- FOLLOWS TREND -----");
		// adds the values from each stock to a temporary arraylist 
		for (int i = 0; i < prices.get(0).size(); i++){
			for (int j = 0; j < prices.size(); j++){
				temp.add(prices.get(j).get(i));
			}
			
			// passes the temporary array to the decayTrend method
			if (decayTrend(temp)){
				printSymbol(i);
				System.out.println("");
			}
						
			temp.clear();
		}
	
		read.close();
	}

}
