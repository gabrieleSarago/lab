package entita_statiche;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import entita_statiche.funzione.Funzione;
import gioco.Handler;

public abstract class Interruttore extends EntitaStatica{

	protected boolean attivo; // dice se l' interruttore funziona o no
	protected EntitaStatica collegamento; // collega l' interruttore a un entita statica
	protected Funzione[] funzione;
	protected boolean cambiaPosizione; //cambia da true a false, da false a true ogni volta che si esegue funzione
	
	public Interruttore() {}// per esternalizzazione
	public Interruttore(Handler h, float x, float y, int larghezza, int altezza, Funzione... funzione) {
		super(h, x, y, larghezza, altezza);
		attivo = true;
		this.funzione = funzione;
		cambiaPosizione = false;
	}
	// dice cosa fa l' interruttore
	public void funzione(Handler h){
		if(!attivo) return;
		cambiaPosizione = !cambiaPosizione;
		for(int i=0; i < funzione.length; i++)
			funzione[i].eseguiFunzione(h);
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
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(attivo);
		out.writeObject(funzione);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
		attivo = in.readBoolean();
		funzione = (Funzione[])in.readObject();
		cambiaPosizione = false;
		
	}
	
	

}
