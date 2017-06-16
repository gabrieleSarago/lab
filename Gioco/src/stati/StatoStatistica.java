package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import gioco.Handler;
import gioco.Handler.Statistiche;
import grafica.Risorse;
import suoni.Suono;

public class StatoStatistica extends Stato{

	private Handler h;
	
	private Suono suono;
	
	private String [] stat;
	private int [] statistiche;
	
	public StatoStatistica(Handler h, Suono suono) {
		super(h);
		this.h=h;
		String lingua = h.getLingua().getLingua();
		stat = Handler.Statistiche.getNomi(lingua);
		
		this.suono = suono;
	}
	
	@Override
	public void aggiorna() {
		getInput();
		
	}

	@Override
	public void disegna(Graphics g) {
		//Sfondo
		g.drawImage(Risorse.sfondo_popup, -15,-15, null);

		Font customFont = null;
		try {
		    //Crea un font da file
			InputStream is = this.getClass().getResourceAsStream("/res/classifiche/old_game.ttf");
		    customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(200f);
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
		
		//nome utente
		g.setColor(Color.blue);
		g.drawString(Risorse.utenteCorrente, 200, 115);
		
		//scritte bianche
		g.setColor(Color.white);
		try {
		    //Crea un font da file
			InputStream is = this.getClass().getResourceAsStream("/res/classifiche/old_game.ttf");
		    customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    //registra il font
		    ge.registerFont(customFont);
		    g.setFont(customFont);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		statistiche = h.getStatistiche();
		
		for(int i = 0; i < stat.length; i++){
			g.drawString(stat[i], 200, 200 + 30*i);
			g.setColor(Color.BLUE);
			g.setFont(num);
			g.drawString(""+statistiche[i], 800, 200+30*i);
			g.setColor(Color.white);
			g.setFont(customFont);
		}
	}
	
	/**
	 * Gestisce gli ascoltatori da tastiera.
	 */
	private void getInput() {
		if(h.getGestioneInput().esc || h.getGestioneInput().enter){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.CONFERMA);
			h.getGioco().setStato(new StatoMenu(h, suono));
		}
	}

}
