package entita_statiche;

import java.io.IOException;

import java.io.ObjectInput;
import java.io.ObjectOutput;

import entita.Entita;
import gioco.Handler;

/**
 * Costruisce l'oggetto EntitaStatica che rappresenta le entità statiche
 * all'interno del gioco,quali interruttori,caramelle,sbarre etc.
 *
 */

public abstract class EntitaStatica extends Entita{

	public EntitaStatica(){} //necessario per esternalizzazione
	/**
	 * Costruttore dell'oggetto EntitaStatica
	 * @param h l'oggetto Handler utile per curare la gestione con le altre classi.
	 * @param x cordinata X dell'oggetto EntitaStatica all'interno del gioco.
	 * @param y cordinata X dell'oggetto EntitaStatica all'interno del gioco.
	 * @param larghezza la larghezza dell'entita statica.
	 * @param altezza l'altezza dell'entita statica.
	 */
	public EntitaStatica(Handler h, float x, float y, int larghezza, int altezza) {
		super(h, x, y, larghezza, altezza);
	}
	
	/**
	 * Metodo utile per salvare le posizioni degli oggetti EntitaStatica.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	/**
	 * Metodo utile per caricare gli oggetti EntitaStatica nelle loro posizioni.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
	}
	
	
}
