package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import classifica.Classifica;
import classifica.Nominativo;
import finestra.FinestraHaiVinto;
import finestra.FinestraSalvataggio;
import gfx.Risorse;
import gioco.Handler;
/**
 * Costruisce l'oggetto StatoVittoria.
 */
public class StatoVittoria extends Stato {

	private BufferedImage img;
	
	private int tempo;
	private Classifica classifica = new Classifica();

	/**
	 * Costruisce l'oggetto StatoVittoria.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param img sfondo dello StatoVittoria (ultimoScreen dello StatoGioco)
	 * @param tempo tempo che rappresenta il punteggio del giocatore.
	 */
	public StatoVittoria(Handler h, BufferedImage img, int tempo) {
		super(h);
		this.img = img;
		this.tempo = tempo;
		try {
			classifica.carica(Risorse.PATH+"\\classificaPunteggio.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int cont=0;
		for(Nominativo n:classifica){
			if(Integer.parseInt(n.getPunteggio())>this.tempo)
				cont++;
			else break;
		}
		if(cont>=3)
			new FinestraHaiVinto(h);
		else new FinestraSalvataggio(h,tempo);
	}

	@Override
	public void aggiorna() {

	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
