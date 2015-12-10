package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	
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
	
	public static int parseInt(String numero){
		try{
			return Integer.parseInt(numero);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
