package entita;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gioco.Handler;
import grafica.Risorse;
import grafica.Tile;

/**
 * Crea l'oggetto InterruttorePressione.
 *
 */
public class InterruttorePressione extends Interruttore{

	public InterruttorePressione() {}// necessario per esternalizzazione
	/**
	 * Costruttore dell'oggetto InterruttorePressione.
	 * @param h oggetto Handler utile per curare la gestione con le altre classi.
	 * @param x coordinata X dell'oggetto InterruttorePressione.
	 * @param y coordinata Y dell'oggetto InterruttorePressione.
	 * @param funzione la funzione che svolge l'oggetto InterruttorePressione.
	 */
	public InterruttorePressione(Handler h, float x, float y, Funzione... funzione) {
		super(h, x, y, Tile.TILE_LARGHEZZA/2, Tile.TILE_ALTEZZA/2, funzione);
		
	}
	/**
	 * Costruttore dell'oggetto InterruttorePressione.
	 * @param h oggetto Handler utile per curare la gestione con le altre classi.
	 * @param x coordinata X dell'oggetto InterruttorePressione.
	 * @param y coordinata Y dell'oggetto InterruttorePressione.
	 * @param autoDisattiva se true l' oggetto disattiva se stesso una volta usato
	 * @param funzione la funzione che svolge l'oggetto InterruttorePressione.
	 */
	public InterruttorePressione(Handler h, float x, float y, boolean autoDisattiva, Funzione... funzione) {
		super(h, x, y, Tile.TILE_LARGHEZZA/2, Tile.TILE_ALTEZZA/2, autoDisattiva, funzione);
		
	}

	@Override
	public void aggiorna() {
	}
	
	/**
	 * Disegna l'oggetto InterrutorePressione mediante un oggetto Graphics.
	 */
	@Override
	public void disegna(Graphics g) {
		g.drawImage(getAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		/*g.setColor(Color.RED);
		g.fillRect((int)(x + bounds.x - h.getCameraGioco().getxOffset()),
				(int)(y + bounds.y - h.getCameraGioco().getyOffset()),
				bounds.width, bounds.height);*/
		
	}
	
	/**
	 * Restituisce l'animazione corrente dell'oggetto InterruttorePressione.
	 * @return oggetto BufferedIamge che rappresenta
	 *  l'attuale animazione dell'oggetto InterruttorePressione.
	 */
	private BufferedImage getAnimazioneCorrente(){
		if(attivo){
			if(cambiaPosizione)
				return Risorse.interruttore_acceso_destra; 
			else return Risorse.interruttore_acceso_sinistra;
		}
		else
			return Risorse.interruttore_spento;
		
	}
	
	/**
	 * Salva le posizioni dell'oggetto InterruttorePressione.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	/**
	 * Carica gli oggetti InterruttorePressione nelle loro posizioni.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
	}
	

}
