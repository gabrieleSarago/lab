package lingue;

import java.io.*;

/**
 * Oggetto che seleziona la lingua da impostare nel gioco.
 */
public class Lingua {
	
	private String linea;
	
	public String getLingua(){
		try{
		BufferedReader br = new BufferedReader(new FileReader("res/lingue/lingua.txt"));
		linea = br.readLine();
		br.close();
		}catch(IOException e){e.printStackTrace();}
		return linea;
	}
	/**
	 * Imposta la lingua del gioco,salvandola su file.
	 * @param s la lingua da selezionare.
	 */
	public void setLingua(String s){
		try{
			PrintWriter pw = new PrintWriter(new FileWriter("res/lingue/lingua.txt"));
			switch(s){
			case "ITALIANO" : pw.write("ITALIANO"); break;
			case "ENGLISH" : pw.write("ENGLISH"); break;
			case "DEUTSCH" : pw.write("DEUTSCH"); break;
			}
			pw.close();
		}catch(IOException e){e.printStackTrace();}
	}
}
