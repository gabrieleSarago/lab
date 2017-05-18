package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gioco.Handler;
import gioco.Handler.Statistiche;
import grafica.Risorse;
import suoni.Suono;

public class StatoStatistica extends Stato{

	private Handler h;
	
	private Suono suono;
	
	private Statistiche [] stat = Handler.Statistiche.values();
	private File f = new File(Risorse.STATISTICA);
	
	public StatoStatistica(Handler h, Suono suono) {
		super(h);
		this.h=h;
		
		this.suono = suono;
	}
	
	

	@Override
	public void aggiorna() {
		getInput();
		
	}

	@Override
	public void disegna(Graphics g) {
		//sfondo nero
		g.setColor(Color.black);
		g.fillRect(0, 0, h.getLarghezza(), h.getAltezza());
		
		BufferedImage voce = Risorse.voci_classifica[1];
			
		//Per impostare le voci centrate
		g.drawImage(voce, h.getLarghezza()/2 - voce.getWidth()/2, 550, null);
		
		//scritte bianche
		g.setColor(Color.white);
		Font customFont;
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
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String statisticaCorrente = br.readLine();
			int i = 0;
			while(statisticaCorrente != null){
				g.drawString(stat[i].toString() + "\t" + statisticaCorrente, 200, 200 + 30*i);
				i++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
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
