package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import finestra.FinestraSconfitta;
import gioco.Handler;
/**
 * Crea l'oggetto StatoSconfitta.
 */
public class StatoSconfitta extends Stato{

	private BufferedImage img;
	
	/**
	 * Costruisce l'oggetto StatoSconfitta.
	 * @param h oggetot handler utile per la gestione con le altre classi.
	 * @param img sfondo dello StatoSconfitta (ultimoScreen di StatoGioco)
	 */
	public StatoSconfitta(Handler h, BufferedImage img) {
		super(h);
		this.img = img;
		new FinestraSconfitta(h);
	}

	@Override
	public void aggiorna() {

	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
