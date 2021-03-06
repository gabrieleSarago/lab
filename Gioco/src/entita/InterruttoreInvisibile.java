package entita;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gioco.Handler;

/**
 * Crea l'oggetto InterruttoreInvisibile.
 *
 */
public class InterruttoreInvisibile extends Interruttore{
	private static final long serialVersionUID = 6L;
	public InterruttoreInvisibile() {}// necessario per esternalizzazione
	/**
	 * Costruttore dell'oggetto InterruttoreInvisibile
	 * @param h oggetto Handler utile per curare la gestione con le altre classi.
	 * @param x coordinata X dell'oggetto InterruttoreInvisibile.
	 * @param y coordinata Y dell'oggetto InterruttoreInvisibile.
	 * @param larghezza la larghezza dell'oggetto InterruttoreInvisibile.
	 * @param altezza l'altezza dell'oggetto InterruttoreInvisibile.
	 * @param funzione la funzione svolta dall'oggetto InterruttoreInvisibile.
	 */
	public InterruttoreInvisibile(Handler h, float x, float y, int larghezza, int altezza, Funzione... funzione) {
		super(h, x, y, larghezza, altezza, funzione);	
	}
	/**
	 * 
	 * Costruttore dell'oggetto InterruttoreInvisibile
	 * @param h oggetto Handler utile per curare la gestione con le altre classi.
	 * @param x coordinata X dell'oggetto InterruttoreInvisibile.
	 * @param y coordinata Y dell'oggetto InterruttoreInvisibile.
	 * @param larghezza la larghezza dell'oggetto InterruttoreInvisibile.
	 * @param altezza l'altezza dell'oggetto InterruttoreInvisibile.
	 * @param autoDisattiva se true l' interruttore una volta toccato si disattiva
	 * @param funzione la funzione svolta dall'oggetto InterruttoreInvisibile.
	 */
	public InterruttoreInvisibile(Handler h, float x, float y, int larghezza, int altezza, boolean autoDisattiva, Funzione... funzione) {
		super(h, x, y, larghezza, altezza, autoDisattiva, funzione);
	}

	@Override
	public void aggiorna() {
	}

	@Override
	public void disegna(Graphics g) {
		/*g.setColor(Color.RED);
		g.fillRect((int)(x + bounds.x - h.getCameraGioco().getxOffset()),
				(int)(y + bounds.y - h.getCameraGioco().getyOffset()),
				bounds.width, bounds.height);*/
	}
	
	/**
	 * Salva le posizioni dell'oggetto InterruttoreInvisibile.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	/**
	 * Carica gli oggetti InterruttoreInvisibile nelle loro posizioni.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
	}
	

}

