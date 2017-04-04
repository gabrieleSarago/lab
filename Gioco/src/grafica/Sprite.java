package grafica;

import java.awt.image.BufferedImage;

/**
 * Crea l'oggetto Sprite,utile per disegnare le entit� del gioco.
 */
public class Sprite {
	
	private BufferedImage sink;
	
	/**
	 * Costruisce l'oggetto sprite.
	 * @param sink lo sprite che rappresenta sink.
	 */
	public Sprite(BufferedImage sink){
		this.sink = sink;
	}
	
	public BufferedImage prendiSprite(int x, int y, int larghezza, int altezza){
		return sink.getSubimage(x, y, larghezza, altezza);
	}
		
}
