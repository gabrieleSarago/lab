package entita;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gioco.Handler;
import grafica.Animazione;
import grafica.Risorse;
import grafica.Tile;

/**
 * Crea l'oggetto Sbarra che e' un entita' statica.
 *
 */
public class Sbarra extends EntitaStatica{
	
	private static final long serialVersionUID = 10L;
	
	private boolean aperta;
	
	//velocita animazione
	private final int VELOCITA = 300;
	
	private enum Movimento{APERTA, IN_APERTURA, IN_CHIUSURA, CHIUSA}
	
	private Movimento ultimoMovimento;
	
	private Animazione porta_aperta, porta_chiusa;
	
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
		this.aperta = aperta;
		porta_aperta = new Animazione(VELOCITA, Risorse.porta_media_aperta);
		porta_chiusa = new Animazione(VELOCITA, Risorse.porta_media_chiusa);
		if(aperta)
			ultimoMovimento = Movimento.APERTA;
		else
			ultimoMovimento = Movimento.CHIUSA;
	}

	@Override
	public void aggiorna() {
		
		switch(ultimoMovimento){
		case IN_APERTURA: porta_aperta.aggiorna();break;
		case IN_CHIUSURA: porta_chiusa.aggiorna();break;
		default: break;
		}
	}

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
		if(eAttraversabile()){
			//se si trova in apertura, dobbiamo aspettare che il tempo
			//iniziato in aggiorna dell'animazione, arrivati all'elemento dell'array
			//che coincide con porta aperta o porta chiusa, cambia lo stato del Movimento in
			//aperta o chiusa
			if(ultimoMovimento == Movimento.IN_APERTURA){
				BufferedImage img = porta_aperta.getFrameCorrente();
				if(img.equals(Risorse.porta_aperta)){
					ultimoMovimento = Movimento.APERTA;
				}
				return porta_aperta.getFrameCorrente();
			}
			else
				return Risorse.porta_aperta;
		}
		else{
			
			if(ultimoMovimento == Movimento.IN_CHIUSURA){
				BufferedImage img = porta_chiusa.getFrameCorrente();
				if(img.equals(Risorse.porta_chiusa)){
					ultimoMovimento = Movimento.CHIUSA;
				}
				return porta_chiusa.getFrameCorrente();
			}
			else
				return Risorse.porta_chiusa;
		}
	}
	/**
	 * Apre la sbarra.
	 */
	public void apri(){
		ultimoMovimento = Movimento.IN_APERTURA;
		setAttraversabile(true);
	}
	/**
	 * Chiude la sbarra.
	 */
	public void chiudi(){
		ultimoMovimento = Movimento.IN_CHIUSURA;
		setAttraversabile(false);
	}
	/**
	 * Cambia lo stato del valore booleano che
	 * identifica l'apertura della sbarra.
	 */
	public void cambia(){
		if(ultimoMovimento == Movimento.APERTA)
			ultimoMovimento = Movimento.IN_CHIUSURA;
		else
			ultimoMovimento = Movimento.IN_APERTURA;
		setAttraversabile(!attraversabile);
	}
	
	/**
	 * Salva le posizioni degli oggetti Sbarra.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(attraversabile);
		out.writeBoolean(aperta);
	}
	/**
	 * Carica gli oggetti Sbarra nelle loro posizioni.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);	
		attraversabile= in.readBoolean();
		porta_aperta = new Animazione(VELOCITA, Risorse.porta_media_aperta);
		porta_chiusa = new Animazione(VELOCITA, Risorse.porta_media_chiusa);
		if(aperta)
			ultimoMovimento = Movimento.APERTA;
		else
			ultimoMovimento = Movimento.CHIUSA;
	}
	
}
