package entita;

import gioco.Handler;
import grafica.Risorse;
import grafica.Tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Crea l'oggetto Sbarra che e' un entita' statica.
 *
 */
public class Sbarra extends EntitaStatica{
	private static final long serialVersionUID = 10L;
	public Sbarra(){} // per esternalizzazione
	/**
	 * Costruttore dell'oggetto Sbarra.
	 * @param h oggetto Handler utile per curare la gestione con le altre classi.
	 * @param x coordinata X dell'oggetto Sbarra.
	 * @param y coordinata Y dell'oggetto Sbarra.
	 * @param aperta booleano che identifica l'apertura della sbarra.
	 */
	public Sbarra(Handler h, float x, float y, boolean aperta) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA);
		this.attraversabile = aperta;
	}

	@Override
	public void aggiorna() {}

	/**
	 * Disegna l'oggetto Sbarra mediante un oggetto Graphics.
	 */
	@Override
	public void disegna(Graphics g) {
		g.drawImage(getAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
	}
	
	/**
	 * Ritorna l'animazione corrente dell'oggetto Sbarra(aperta/chiusa).
	 * @return oggetto BufferedImage che identifica
	 * l'attuale animazione dell'oggetto Sbarra.
	 */
	private BufferedImage getAnimazioneCorrente(){
		if(eAttraversabile())
			return Risorse.sbarra_aperta;
		else
			return Risorse.sbarra_chiusa;
	}
	/**
	 * Apre la sbarra.
	 */
	public void apri(){
		setAttraversabile(true);
	}
	/**
	 * Chiude la sbarra.
	 */
	public void chiudi(){
		setAttraversabile(false);
	}
	/**
	 * Cambia lo stato del valore booleano che
	 * identifica l'apertura della sbarra.
	 */
	public void cambia(){
		setAttraversabile(!attraversabile);
	}
	
	/**
	 * Salva le posizioni degli oggetti Sbarra.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(attraversabile);
	}
	/**
	 * Carica gli oggetti Sbarra nelle loro posizioni.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);	
		attraversabile= in.readBoolean();
	}
	
}
