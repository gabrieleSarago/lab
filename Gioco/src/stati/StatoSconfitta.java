package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import finestra.FinestraSconfitta;
import gioco.Handler;

public class StatoSconfitta extends Stato{

	private BufferedImage img;
	
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
