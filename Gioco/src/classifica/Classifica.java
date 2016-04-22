package classifica;
import java.util.*;
import java.io.*;

public class Classifica implements Iterable<Nominativo>{
	
	private LinkedList<Nominativo> classifica=new LinkedList<Nominativo>();
	
	public Classifica(){
	}//costruttore
	
	public int size(){return classifica.size();}
	
	boolean isEmpty(){return classifica.isEmpty();}
	
	
	public void azzeraClassifica(){
		if(classifica.isEmpty()) return;
		classifica.clear();
	}//azzeraClassifica
	
	public void add(Nominativo n){
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


	@Override
	public Iterator<Nominativo> iterator() {
		return classifica.iterator();
	}
}//classifica
