package entita_statiche.funzione;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import personaggio.Nemico;
import entita.Entita;
import entita_statiche.Interruttore;
import entita_statiche.Sbarra;
import entita_statiche.Teletrasporto;
import gfx.Suono;
import gioco.Handler;
/**
 * Crea l'oggetto Funzione che implementa tutte
 * le possibili funzioni di un oggetto Interruttore.
 */

public class Funzione implements Externalizable{
	
	private Entita collegamento;
	private Funzionalita funzione;
	
	public Funzione(){}
	/**
	 * 
	 * @param array_entita un ArrayList che contiene oggetti Entitò
	 * @param index parametro che identifica la posizione dell'oggetto Entità nell'ArrayList.
	 * @param funzione la funzione che svolge l'oggetto array_entita[index]
	 */
	public Funzione(ArrayList<Entita> array_entita, int index, Funzionalita funzione)
	{
		this.funzione = funzione;
		this.collegamento = array_entita.get(index); 
		if(collegamento == null || funzione == null) throw new IllegalArgumentException("parametri null");
	}
	
	/**
	 * Costruttore di copia.
	 * @param f oggetto Funzione da copiare.
	 */
	public Funzione(Funzione f)
	{
		this.collegamento = f.collegamento;
		this.funzione = f.funzione;
	}
	
	/**
	 * Metodo usato per eseguire la funzione scelta.
	 * @param h oggetto Handler,utile nella gestione con le altre classi.
	 */
	public void eseguiFunzione(Handler h)
	{
		switch(funzione) {
			case APRI_SBARRA:{
				if(!(collegamento instanceof Sbarra)) 
					throw new IllegalArgumentException("aspettata Sbarra");
				((Sbarra) collegamento).apri();
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_APERTO);
				break;
			}
			case CHIUDI_SBARRA:
				if(!(collegamento instanceof Sbarra))
					throw new IllegalArgumentException("aspettata Sbarra");
				((Sbarra)collegamento).chiudi();	
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_CHIUSO);
				break;
			case CAMBIA_SBARRA:{
				if(!(collegamento instanceof Sbarra))
					throw new IllegalArgumentException("aspettata Sbarra");
				((Sbarra)collegamento).cambia();
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_APERTO);
				break;
			}
			case ATTIVA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore))
					throw new IllegalArgumentException("aspettato Interruttore");
				((Interruttore)collegamento).setAttivo(true);
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_APERTO);
				break;
			case DISATTIVA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) 
					throw new IllegalArgumentException("aspettato Interruttore");
				((Interruttore)collegamento).setAttivo(false);
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_CHIUSO);
				break;
			case CAMBIA_INTERRUTTORE:
				if(!(collegamento instanceof Interruttore)) 
					throw new IllegalArgumentException("aspettato Interruttore");
				((Interruttore)collegamento).cambiaAttivo();
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_APERTO);
				break;
			case ATTIVA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto))
					throw new IllegalArgumentException("aspettato Teletrasporto");
				((Teletrasporto)collegamento).setAttivo(true);
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_APERTO);
				break;
			case DISATTIVA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto))
					throw new IllegalArgumentException("aspettato Teletrasporto");
				((Teletrasporto)collegamento).setAttivo(false);
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_CHIUSO);
				break;
			case CAMBIA_TELETRASPORTO:
				if(!(collegamento instanceof Teletrasporto)) 
					throw new IllegalArgumentException("aspettato Teletrasporto");
				((Teletrasporto)collegamento).cambiaAttivo();
				h.getGioco().getSuono().riproduci(Suono.suoni.INTERRUTTORE_APERTO);
				break;
			case ATTIVA_NEMICO:
				if(!(collegamento instanceof Nemico))
					throw new IllegalArgumentException("aspettato Nemico");
				((Nemico)collegamento).setVivo(true);
				break;
			case DISATTIVA_NEMICO:
				if(!(collegamento instanceof Nemico))
					throw new IllegalArgumentException("aspettato Nemico");
				((Nemico)collegamento).setVivo(false);
				break;
			default:
				throw new IllegalArgumentException("funzione non gestita");
				
			}			
	}

	public Funzione getFunzioni() {
		return this;
	}

	public void setFunzione(Funzione funzione) {
		this.funzione = funzione.funzione;
		this.collegamento = funzione.collegamento;
	}
	
	public Funzionalita getFunzionalita() {
		return funzione;
	}
	
	/**
	 * Metodo per salvare le funzioni degli oggetti Entità.
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(collegamento);
		out.writeObject(funzione);
		
	}
	
	/**
	 * Metodo per caricare le entità che implementano una data funzione.
	 */
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		collegamento = (Entita)in.readObject();
		funzione = (Funzionalita) in.readObject();
	}
	
	
}