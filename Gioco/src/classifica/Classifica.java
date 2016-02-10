package classifica;
import java.util.*;
import java.io.*;

public class Classifica {
	
	private TreeSet<Nominativo> classifica;
	
	public Classifica(){
		classifica = new TreeSet<Nominativo>();
	}//costruttore
	
	public void azzeraClassifica(){
		if(classifica.isEmpty()) return;
		classifica.clear();
	}//azzeraClassifica
	
	public void add(Nominativo n){
		classifica.add(n);
	}//add
	
	public void remove(Nominativo n){
		this.remove(n);
	}//rimuovi
	
	public void salva(String nomeFile) throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter(nomeFile));
		for(Nominativo n: classifica)
			pw.println(n);
		pw.close();
	}//salva
	
	public void carica(String nomeFile) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(nomeFile));
		String linea = null;
		StringTokenizer st = null;
		for(;;){
			linea = br.readLine();
			if(linea==null) break;
			st = new StringTokenizer(linea," ");
			String punt = st.nextToken();
			String nome = st.nextToken();
			classifica.add(new Nominativo(punt,nome));
		}//for
		br.close();
	}//carica
	
	public String toString(){
		StringBuilder sb = new StringBuilder(10);
		for(Nominativo n : classifica)
			sb.append(n+"\n");
		return sb.toString();
	}//toString

}//classifica
