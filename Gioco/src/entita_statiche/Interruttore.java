package entita_statiche;

import entita_statiche.funzione.Funzione;
import gioco.Handler;

public abstract class Interruttore extends EntitaStatica{

	protected boolean attivo; // dice se l' interruttore funziona o no
	protected EntitaStatica collegamento; // collega l' interruttore a un entita statica
	protected Funzione[] funzione;
	
	public Interruttore(Handler h, float x, float y, int larghezza, int altezza, Funzione... funzione) {
		super(h, x, y, larghezza, altezza);
		attivo = true;
		this.funzione = funzione;
	}
	// dice cosa fa l' interruttore
	public void funzione(){
		if(!attivo) return;
		for(int i=0; i < funzione.length; i++)
			funzione[i].eseguiFunzione();
	}
	
	public boolean eAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
	public void cambiaAttivo()
	{
		attivo = !attivo;
	}
	
	public EntitaStatica getCollegamento() {
		return collegamento;
	}
	public void setCollegamento(EntitaStatica collegamento) {
		this.collegamento = collegamento;
	}
	
	public Funzione[] getFunzione() {
		return funzione;
	}
	public void setFunzione(Funzione[] funzione) {
		this.funzione = funzione;
	}
	
	

}
