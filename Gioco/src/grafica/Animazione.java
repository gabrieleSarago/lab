package grafica;

import java.awt.image.BufferedImage;
/**
 * Oggetto che crea l'animazione del gioco.
 */

public class Animazione {
	
	private int velocita,indice;
	private long ultimoTempo, timer;
	private BufferedImage[] frames;
	
	/**
	 * Costruttore dell'oggetto Animazione.
	 * @param velocita la velocit� con cui viene eseguita l'animazione.
	 * @param frames le immagini che costituiscono l'animazione.
	 */
	public Animazione(int velocita, BufferedImage[] frames){
		this.velocita = velocita;
		this.frames = frames;
		indice = 0;
		timer = 0;
		ultimoTempo = System.currentTimeMillis();
	}
	
	/**
	 * Regolarizza i movimenti.
	 */
	public void aggiorna(){
		timer += System.currentTimeMillis() - ultimoTempo;
		ultimoTempo = System.currentTimeMillis();
		
		if(timer > velocita){
			indice = (indice + 1) % frames.length;
			timer = 0;
		}
	}
	
	/**
	 * Ritorna l'animazione corrente.
	 * @return l'animazione corrente
	 */
	public BufferedImage getFrameCorrente(){
		return frames[indice];
	}
	
}
