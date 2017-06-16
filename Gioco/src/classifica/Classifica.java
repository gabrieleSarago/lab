package classifica;
import java.util.*;

import java.io.*;

/**
 * Costruisce l'oggetto classifica utile per aggiornare lo StatoClassifica
 *  appogiandosi ad una LinkedList di Java.
 */

public class Classifica implements Iterable<Nominativo>{
	
	private LinkedList<Nominativo> classifica = new LinkedList<Nominativo>();
	
	public Classifica(){
	}//costruttore
	
	public int size(){return classifica.size();}
		
	boolean isEmpty(){return classifica.isEmpty();}
	
	/**
	 * Cancella dalla classifica tutti i Nominativi e relativi punteggi presenti.
	 */
	public void azzeraClassifica(){
		if(classifica.isEmpty()) return;
		classifica.clear();
	}//azzeraClassifica
	
	/**
	 * Aggiunge un nominativo alla classifica.
	 * @param n l'oggetto Nominativo da aggiungere.
	 */
	public void add(Nominativo n){
		if(classifica.size() > 10)
			return;
		ListIterator<Nominativo> lit = classifica.listIterator();
		boolean flag = false;
		while(lit.hasNext() && ! flag){
			Nominativo x = lit.next();
			if(x.compareTo(n)<0){
				lit.previous();
				lit.add(n);
				flag=true;
			}
		}
		if(!flag)lit.add(n);
	}//add
	
	/**
	 * Rimuove un nominativo dalla classifica.
	 * @param n L'oggetto Nominativo da rimuovere.
	 */
	public void remove(Nominativo n){
		this.remove(n);
	}//rimuovi
	
	/**
	 * Salva il contenuto della classifica su file testuale.
	 * Utile per memorizzare la classifica.
	 * @param nomeFile il nome del file sul quale viene salvata la classifica.
	 * @throws IOException
	 */
	public void salva(String nomeFile) throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter(nomeFile));
		for(Nominativo n: classifica)
			pw.println(n);
		pw.close();
	}//salva
	
	/**
	 * Carica il contenuto della classifica presente su file.
	 * Utile per mostrare l'attuale contenuto della classifica.
	 * @param nomeFile il file da caricare
	 * @throws IOException
	 */
	public void carica(String nomeFile) throws IOException{
		File f = new File(nomeFile);
		if(!f.exists()){
			f.createNewFile();
		}
		BufferedReader br = new BufferedReader(new FileReader(nomeFile));
		String linea = null;
		StringTokenizer st = null;
		String punt = "";
		String nome = "";
		for(;;){
			linea = br.readLine();
			if(linea==null) break;
			st = new StringTokenizer(linea," ");
			punt = st.nextToken();
			nome = st.nextToken();
			classifica.add(new Nominativo(punt, nome));
		}//for
		br.close();
	}//carica
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder(10);
		for(Nominativo n : classifica)
			sb.append(n+"\n");
		return sb.toString();
	}//toString

	/**
	 * Iteratore della classifica,necessario per scorrere il contenuto della classifica.
	 */
	@Override
	public Iterator<Nominativo> iterator() {
		return classifica.iterator();
	}
}//classifica
