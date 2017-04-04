package classifica;

/**
 * Costruisce l'oggetto Nominativo,caraterizzato da due oggetti String:
 * punteggio del giocatore e nome del giocatore.
 *
 */

public class Nominativo implements Comparable<Nominativo>{

	String punteggio,nomeGiocatore;
	
	/**
	 * Costruttore dell'oggetto Nominativo
	 * @param punteggio il punteggio del giocatore.
	 * @param nome il nome del giocatore.
	 */
	public Nominativo(String punteggio,String nome){
		this.punteggio=punteggio; this.nomeGiocatore=nome;
	}
	
	public String getPunteggio(){return punteggio;}
	public String getNome(){return nomeGiocatore;}
	
	public boolean equals(Object o){
		if(!(o instanceof Nominativo)) return false;
		if(o==this)return true;
		Nominativo n = (Nominativo)o;
		return this.punteggio==n.punteggio && this.nomeGiocatore==n.nomeGiocatore;
	}//equals

	@Override
	public int compareTo(Nominativo n) {
		int x1 = Integer.parseInt(this.punteggio);
		int x2 = Integer.parseInt(n.punteggio);
		return x1-x2;
	}
	
	public String toString(){
		return ""+punteggio+" "+nomeGiocatore;
	}//toString
	
}//Nominativo
