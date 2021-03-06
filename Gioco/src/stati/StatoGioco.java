package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entita.Sink;
import gioco.Handler;
import livelli.Livello;
import suoni.Suono;
/**
 * Crea l'oggetto StatoGioco.
 */
public class StatoGioco extends Stato {
	
	private Livello l;
	private boolean vittoria = false;
	private Graphics g;
	private boolean riproduzione = true;
	private Suono suono;
	
	/**
	 * Costruisce l'oggetto StatoGioco.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 */
	public StatoGioco(Handler h){
		//carica il livello quando si clicca su nuova partita
		super(h);
		// TODO in caso bisogna caricare un altro livello cambiare "livello1"
		l = new Livello(h, "livello1");
		h.setLivello(l);
		suono = h.getSuono();
	}
	
	/**
	 * Costruisce l'oggetto StatoGioco.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param file file da cui viene caricato il livello del gioco.
	 */
	public StatoGioco(Handler h, String file){
		super(h);
		
		l = new Livello(h, file);
		h.setLivello(l);
		suono = h.getSuono();
	}
	
	/**
	 * Aggiorna lo StatoGioco
	 */
	@Override
	public void aggiorna() {
		
		if(riproduzione){
			suono.riproduci(Suono.suoni.GIOCO);
			riproduzione = false;
		}
		
		if(h.getLivello().getSink().getSconfitta()){
			if(!h.getSuono().getMuto()){
				h.getSuono().getClipGioco().close();
			}
			h.getGioco().setStato(new StatoSconfitta(h, this.getUltimoScreen()));
		}
		
		if(!vittoria){
			if(!(h.getGioco().getPausa())){
				getInput();
				//controlla();
				l.aggiorna();
				if(vittoria){
					if(!h.getSuono().getMuto()){
						h.getSuono().getClipGioco().close();
					}
					h.getGioco().setStato(new StatoVittoria(h, this.getUltimoScreen(), h.getLivello().getSink().getTempo()));
				}
			}
		}
	}
	@Override
	public void disegna(Graphics g) {
		l.disegna(g);
	}
	
	/**
	 * Gestisce gli ascolatori da tastiera in StatoGioco. 
	 */
	private void getInput(){
		if(h.getGestioneInput().esc){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
						
			h.getGioco().setPausa(true);
			h.getGioco().setStato(new StatoPausa(h, this));
			
			//Se si entra in pausa la musica del gioco si ferma e le risorse dei clip vengono liberate.
			//Tale operazione viene eseguita in seguito alla chiamata di StatoPausa per evitare il
			//ritardo nel momento in cui si entra in pausa.
			if(!suono.getMuto()){
				suono.getClipGioco().stop();
				suono.ferma();
			}
		}
	}
	
	public Sink getSink(){
		return l.getSink();
	}

	/**
	 * Converte l'oggetto Graphics in BufferedImage
	 * in modo da poter essere stamapto su schermo.
	 * @return il BufferdImage della conversione
	 */
	public BufferedImage getUltimoScreen(){
		BufferedImage img = new BufferedImage(1200, 700, BufferedImage.TYPE_INT_RGB);
		g = img.createGraphics();
		disegna(g);
		g.dispose();
		return img;
	}
	
	/**
	 * Imposta lo stato di vittoria.
	 * @param vittoria boolean identificativo
	 */
	public void setVittoria(boolean vittoria){
		this.vittoria = vittoria;
	}
	
	public Suono getSuono(){
		return suono;
	}
}
