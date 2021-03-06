package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import classifica.Classifica;
import classifica.Nominativo;
import finestra.FinestraAzzeraClassifica;
import finestra.FinestraNessunPunteggio;
import gioco.Handler;
import grafica.Risorse;
import suoni.Suono;
/**
 * Crea l'oggetto StatoClassifica.
 */
public class StatoClassifica extends Stato{
	
	private Handler h;
	
	private Suono suono;
	
	private Classifica c;
	
	private int sceltaCorrente=0;
	
	private String [] opzioni = {"AZZERA CLASSIFICA","TORNA AL MENU"};
	
	/**
	 * Crea l'oggetto StatoClassifica.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param suono suono di sottofondo per lo StatoClassifica
	 */
	public StatoClassifica(Handler h, Suono suono) {
		super(h);
		this.h=h;
		this.suono = suono;
		c = h.getGioco().getClassifica();
	}
	/**
	 * Aggiorna gli ascoltatori da tastiera in StatoClassifica.
	 */
	@Override
	public void aggiorna(){
		//ottimizzazione
		/*ora=System.nanoTime();
		delta += (ora-ultimoTempo)/tempoDiAggiornamento;
		timer += (ora-ultimoTempo);
		ultimoTempo = ora;
		if(delta>=6){*/
			getInput();
			/*delta-=6;
		}*/
	}

	@Override
	public void disegna(Graphics g) {
		
		g.drawImage(Risorse.sfondo_popup,-15,-15, null);
		BufferedImage voce;
		for(int i=0;i<opzioni.length;i++){
			if(i==sceltaCorrente)
				voce = Risorse.voci_classifica[i];
			else
				voce = Risorse.voci_classifica_off[i];
			
			//Per impostare le voci centrate
			g.drawImage(voce, h.getLarghezza()/2 - voce.getWidth()/2, 500+i*50, null);
		}
		g.setColor(Color.white);
		Font customFont = null;
		try {
		    //Crea un font da file
			InputStream is = this.getClass().getResourceAsStream("/res/classifiche/old_game.ttf");
		    customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(30f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    //registra il font
		    ge.registerFont(customFont);
		    g.setFont(customFont);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		Font num = null;
		
		try {
		    //Crea un font da file
			InputStream is = this.getClass().getResourceAsStream("/res/classifiche/ka1.ttf");
		    num = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    //registra il font
		    ge.registerFont(num);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		g.setFont(num);
		for(int i = 1; i <= 10; i++ ){
			g.drawString(i+".", 400, 50+40*i);
		}

		int j = 90,cont=0; //cont serve per non stampare pi� di 3 nominativi
		for(Nominativo n: c){
			g.setFont(num);
			g.setColor(Color.BLUE);
			g.drawString(n.getPunteggio(), 800, j);
			g.setFont(customFont);
			g.setColor(Color.white);
			g.drawString(n.getNome(),500,j);
			j+=40;
			cont++;
			if(cont>=10)break;
		}
	}
	/**
	 * Aziona le scelte a dispozione dell'utente.
	 */
	private void seleziona(){
		if(sceltaCorrente==0){
			if(c.size()!=0){
				new FinestraAzzeraClassifica(h);
			}
			else new FinestraNessunPunteggio(h);
		}
		else{
			h.getGioco().setStato(new StatoMenu(h, suono));
		}
	}
	/**
	 * Gestisce gli ascoltatori da tastiera.
	 */
	private void getInput() {
		
			if(h.getGestioneInput().up){
				sceltaCorrente--;
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
				if(sceltaCorrente == -1)
					sceltaCorrente = opzioni.length -1;
				suono.riproduci(Suono.suoni.SCORRI);
			}
			if(h.getGestioneInput().down){
				sceltaCorrente++;
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
				if(sceltaCorrente == opzioni.length)
					sceltaCorrente = 0;
				suono.riproduci(Suono.suoni.SCORRI);
			}
			if(h.getGestioneInput().enter){
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
				seleziona();
				suono.riproduci(Suono.suoni.CONFERMA);
			}
			if(h.getGestioneInput().esc){
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
				suono.riproduci(Suono.suoni.CONFERMA);
				h.getGioco().setStato(new StatoMenu(h, suono));
			}
	}

}
