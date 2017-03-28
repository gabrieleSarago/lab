package lingue;

import java.io.*;

import gfx.Risorse;

/**
 * Oggetto che seleziona la lingua da impostare nel gioco.
 */
public class Lingua {
	
	private String linea;
	private File f = new File(Risorse.LINGUA);
	
	public String getLingua(){
		try{
			if(!f.exists()){
				f.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(Risorse.LINGUA));
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
			if(!f.exists()){
				f.createNewFile();
			}
			PrintWriter pw = new PrintWriter(new FileWriter(Risorse.LINGUA));
			switch(s){
			case "ITALIANO" : pw.write("ITALIANO"); break;
			case "ENGLISH" : pw.write("ENGLISH"); break;
			case "DEUTSCH" : pw.write("DEUTSCH"); break;
			}
			pw.close();
		}catch(IOException e){e.printStackTrace();}
	}
}
