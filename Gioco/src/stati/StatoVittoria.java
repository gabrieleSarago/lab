package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import classifica.Classifica;
import classifica.Nominativo;
import finestra.FinestraHaiVinto;
import gioco.Handler;
import grafica.Risorse;
/**
 * Costruisce l'oggetto StatoVittoria.
 */
public class StatoVittoria extends Stato {

	private BufferedImage img;
	
	private int tempo;
	private Classifica classifica;

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
		classifica = h.getGioco().getClassifica();
		int cont=0;
		for(Nominativo n:classifica){
			if(Integer.parseInt(n.getPunteggio())>this.tempo)
				cont++;
			else break;
		}
		if(cont>=10)
			new FinestraHaiVinto(h);
		else {
			classifica.add(new Nominativo(String.valueOf(tempo), Risorse.utenteCorrente));
			try {
				classifica.salva(Risorse.CLASSIFICA);
			} catch (IOException e) {
				e.printStackTrace();
			}
			h.getGioco().setClassifica(classifica);
			new FinestraHaiVinto(h);
		}
	}

	@Override
	public void aggiorna() {

	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
