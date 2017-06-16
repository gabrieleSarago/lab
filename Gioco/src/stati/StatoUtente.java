package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import classifica.Classifica;
import classifica.Nominativo;
import finestra.FinestraUscita;
import finestra.FinestraUtente;
import gioco.Handler;
import grafica.Risorse;
import suoni.Suono;
/**
 * Crea l'oggetto StatoClassifica.
 */
public class StatoUtente extends Stato{
	
	private Handler h;
	
	private Suono suono;
	
	private Classifica c = new Classifica();
	
	private File f = new File(Risorse.CLASSIFICA);
	
	private boolean primoAvvio = true;
	
	//per regolarizzare gli aggiornamenti
	private int fps = 55;
	double tempoDiAggiornamento=1000000000/fps;
	double delta=0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer=0;
	
	/**
	 * Crea l'oggetto StatoClassifica.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param suono suono di sottofondo per lo StatoClassifica
	 */
	public StatoUtente(Handler h, Suono suono) {
		super(h);
		this.h=h;
		this.suono = suono;
	}
	/**
	 * Aggiorna gli ascoltatori da tastiera in StatoClassifica.
	 */
	@Override
	public void aggiorna(){
		getInput();
	}
	
	private void caricaClassifica(){
		try {
			if(!f.exists()){
				f.createNewFile();
			}
			c.carica(Risorse.CLASSIFICA);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(Risorse.sfondo_popup, -15,-15, null);
		if(primoAvvio){
			caricaClassifica();
			primoAvvio = false;
		}
		g.setColor(Color.white);
		Font customFont = null;
		Font customFontWlc = null;
		Font num = null;
		try {
		    //Crea un font da file
			InputStream is = this.getClass().getResourceAsStream("/res/classifiche/old_game.ttf");
		    customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
		    //customFontWlc = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(50f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    //registra il font
		    ge.registerFont(customFont);
		    g.setFont(customFont);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		try {
		    //Crea un font da file
			InputStream is = this.getClass().getResourceAsStream("/res/classifiche/ka1.ttf");
		    customFontWlc = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    //registra il font
		    ge.registerFont(customFontWlc);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
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
			g.drawString(i+".", 400, 97+40*i);
		}

		int j = 140,cont=0; //cont serve per non stampare pi� di 3 nominativi
		for(Nominativo n: c){
			g.setColor(Color.BLUE);
			g.setFont(num);
			g.drawString(n.getPunteggio(), 800, j);
			g.setColor(Color.white);
			g.setFont(customFont);
			g.drawString(n.getNome(),500,j);
			j+=40;
			cont++;
			if(cont>=10)break;
		}
		
		String lingua = h.getLingua().getLingua();
		String str =  "";
		String wlc = "";
		switch(lingua){
		case "ITALIANO": str = "Premi INVIO per iniziare"; wlc = "Benvenuto!"; break;
		case "DEUTSCH" : str = "Druecke ENTER zum starten"; wlc = "Willkommen!"; break;
		default: str = "Press ENTER to start"; wlc = "Welcome!"; break;
		}
		
		g.setFont(customFontWlc);
		g.setColor(Color.WHITE);
		g.drawString(wlc, h.getLarghezza()/2 - 28*(wlc.length()/2), 50);
		g.drawString(str, h.getLarghezza()/2 - 28*(str.length()/2), 650);
	}
	/**
	 * Aziona le scelte a dispozione dell'utente.
	 */
	private void seleziona(){
		new FinestraUtente(h,suono).setVisible(true);
	}
	/**
	 * Gestisce gli ascoltatori da tastiera.
	 */
	private void getInput() {
		
			if(h.getGestioneInput().enter){
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
				seleziona();
				suono.riproduci(Suono.suoni.CONFERMA);
			}
			if(h.getGestioneInput().esc){
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
				suono.riproduci(Suono.suoni.CONFERMA);
				new FinestraUscita(h);
			}
	}

}
