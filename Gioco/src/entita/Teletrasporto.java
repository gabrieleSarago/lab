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
 * Crea l'oggetto Teletrasporto che � un entit� statica.
 * Utile per teletrasportare il giocatore da una parte all'altra del gioco.
 */
public class Teletrasporto extends EntitaStatica{

	private float destinazioneX, destinazioneY;
	private boolean attivo;
	
	public Teletrasporto(){}
	/**
	 * Costruttore dell'oggetto Teletrasporto.
	 * @param h oggetto Handler utile per curare la gestione con altre classi.
	 * @param x la coordinata X dell'oggetto Teletrasporto.
	 * @param y la coordinata Y dell'oggetto Teletrasporto.
	 * @param destinazioneX coordinata X dove viene teletrasportato il giocatore.
	 * @param destinazioneY coordinata Y dove viene teletrasportato il giocatotre.
	 */
	public Teletrasporto(Handler h, float x, float y, float destinazioneX, float destinazioneY) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_LARGHEZZA);
		attivo = true;
		this.destinazioneX = destinazioneX;
		this.destinazioneY = destinazioneY;
	}
	
	/**
	 * Costruttore dell'oggetto Teletrasporto.
	 * @param h oggetto Handler utile per curare la gestione con altre classi.
	 * @param x la coordinata X dell'oggetto Teletrasporto.
	 * @param y la coordinata Y dell'oggetto Teletrasporto.
	 * @param destinazioneX coordinata X dove viene teletrasportato il giocatore.
	 * @param destinazioneY coordinata Y dove viene teletrasportato il giocatotre.
	 * @param attivo booleano che identifica se il teletrasporto in questione
	 * pu� essere utilizzato dal giocatore.
	 */
	public Teletrasporto(Handler h, float x, float y, float destinazioneX, float destinazioneY, boolean attivo) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_LARGHEZZA);
		this.attivo = attivo;
		this.attraversabile = attivo;
		this.destinazioneX = destinazioneX;
		this.destinazioneY = destinazioneY;
	}

	
	@Override
	public void aggiorna() {		
	}
	
	/**
	 * Disegna l'oggetto Teletrasporto mediante un oggetto Graphics.
	 */
	@Override
	public void disegna(Graphics g) {
		g.drawImage(getAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}
	/**
	 * Ritorna l'animazione corrente dell'oggetto Teletrasporto(attivo/non attivo).
	 * @return oggetto BufferedImage che identifica
	 * l'attuale animazione dell'oggetto Teletrasporto.
	 */
	private BufferedImage getAnimazioneCorrente(){
		if(attivo)
			return Risorse.teletrasporto_attivo;
		else
			return Risorse.teletrasporto_inattivo;
		
	}
	/**
	 * Ritorna la coordinata X dove viene teletrasportato il giocatore.
	 * @return la coordinata X
	 */
	public float getDestinazioneX() {
		return destinazioneX;
	}
	/**
	 * Imposta la coordinata X dove viene teletrasportato il giocatore.
	 * @param destinazioneX la coordinata X della destinazione.
	 */
	public void setDestinazioneX(float destinazioneX) {
		this.destinazioneX = destinazioneX;
	}
	/**
	 * Ritorna la coordinata Y dove viene teletrasportato il giocatore.
	 * @return la coordinata Y
	 */
	public float getDestinazioneY() {
		return destinazioneY;
	}
	/**
	 * Imposta la coordinata Y dove viene teletrasportato il giocatore.
	 * @param destinazioneY la coordinata Y della destinazione.
	 */
	public void setDestinazioneY(float destinazioneY) {
		this.destinazioneY = destinazioneY;
	}
	/**
	 * Vede se � attivo o meno l'interruttore.
	 * @return valore booleano,true se attivo false altrimenti.
	 */
	public boolean eAttivo() {
		return attivo;
	}
	/**
	 * Imposta lo stato di attivazione dell'interruttore.
	 * @param attivo booleano che setta lo stato di attivazione dell'interruttore.
	 */
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	/**
	 * Cambia lo stato di attivazione dell'interruttore.
	 */
	public void cambiaAttivo() {
		this.attivo = !attivo;
	}
	
	/**
	 * Salva le posizioni dell'oggetto Teletrasporto.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(attivo);
		out.writeFloat(destinazioneX);
		out.writeFloat(destinazioneY);
		
	}
	/**
	 * Carica gli oggetti Teletrasporto nelle loro posizioni.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);	
		attivo= in.readBoolean();
		destinazioneX = in.readFloat();
		destinazioneY = in.readFloat();
	}
	
}
