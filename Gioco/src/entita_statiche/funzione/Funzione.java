package entita_statiche.funzione;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import entita.Entita;
import entita_statiche.EntitaStatica;
import entita_statiche.Interruttore;
import entita_statiche.Sbarra;
import entita_statiche.Teletrasporto;
import gfx.Suono;
import gioco.Handler;
//implementa tutte le possibili funzioni di un iterruttore

public class Funzione implements Externalizable{
	
	private EntitaStatica collegamento;	
	private Funzionalita funzione;
	
	public Funzione(){}
	public Funzione(ArrayList<Entita> array_entita, int index, Funzionalita funzione)
	{
		this.funzione = funzione;
		this.collegamento = (EntitaStatica)array_entita.get(index);
		if(collegamento == null || funzione == null) throw new IllegalArgumentException("parametri null");
	}
	
	public Funzione(Funzione f)
	{
		this.collegamento = f.collegamento;
		this.funzione = f.funzione;
	}
	
	public void eseguiFunzione(Handler h)
	{
		//TO-DO aggiungere tutte le possibili azioni
		switch(funzione) {
			case APRI_SBARRA:{
				if(!(collegamento instanceof Sbarra)) throw new IllegalArgumentException("aspettata Sbarra");
					((Sbarra) collegamento).apri();
				break;
			}
			case CHIUDI_SBARRA:
				if(!(collegamento instanceof Sbarra)) throw new IllegalArgumentException("aspettata Sbarra");
					((Sbarra)collegamento).chiudi();
				break;
			case CAMBIA_SBARRA:{
				if(!(collegamento instanceof Sbarra)) throw new IllegalArgumentException("aspettata Sbarra");
					((Sbarra)collegamento).cambia();
				try{
					h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_APERTO);
				}catch(Exception e){e.printStackTrace();}
				break;
			}
			case ATTIVA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) throw new IllegalArgumentException("aspettato Interruttore");
					((Interruttore)collegamento).setAttivo(true);
				break;
			case DISATTIVA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) throw new IllegalArgumentException("aspettato Interruttore");
					((Interruttore)collegamento).setAttivo(false);
				break;
			case CAMBIA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) throw new IllegalArgumentException("aspettato Interruttore");
					((Interruttore)collegamento).cambiaAttivo();
				break;
			case ATTIVA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto)) throw new IllegalArgumentException("aspettato Teletrasporto");
					((Teletrasporto)collegamento).setAttivo(true);
				break;
			case DISATTIVA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto)) throw new IllegalArgumentException("aspettato Teletrasporto");
					((Teletrasporto)collegamento).setAttivo(false);
				break;
			case CAMBIA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto)) throw new IllegalArgumentException("aspettato Teletrasporto");
					((Teletrasporto)collegamento).cambiaAttivo();
				break;
			default:
				throw new IllegalArgumentException("funzione non gestita");
				
			}//chiudo switch			
	}

	public Funzione getFunzioni() {
		return this;
	}

	public void setFunzione(Funzione funzione) {
		this.funzione = funzione.funzione;
		this.collegamento = funzione.collegamento;
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(collegamento);
		out.writeObject(funzione);
		
	}
	
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		collegamento = (EntitaStatica)in.readObject();
		funzione = (Funzionalita) in.readObject();
	}
	
	
}