package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * Classe di utilità.
 */
public class Utils {
	
	/**
	 * Carica i file come stringhe.
	 * @param path il file da caricare.
	 * @return una stinga che identifica il file.
	 */
	public static String caricaFileComeString ( String path){
		StringBuilder sb = new StringBuilder();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String linea;
			while((linea = br.readLine()) != null)
				sb.append(linea + "\n");
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	

	/**
	 * Trasforma una stringa (composta da soli numeri) in un intero.
	 * @param numero Stringa da trasformare.
	 * @return Stringa in veste di intero
	 */
	public static int parseInt(String numero){
		try{
			return Integer.parseInt(numero);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
