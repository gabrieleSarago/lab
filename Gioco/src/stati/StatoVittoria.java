package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import finestra.FinestraSalvataggio;
import finestra.FinestraUscita;
import gioco.Handler;

public class StatoVittoria extends Stato{
	
	private BufferedImage img;
	private int tempo;
	
	public StatoVittoria(Handler h,BufferedImage img,int tempo){
		super(h);
		this.img=img;
		this.tempo=tempo;
		new FinestraSalvataggio(h,tempo);
	}

	@Override
	public void aggiorna() {
		
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(img,0,0,null);
	}

}
