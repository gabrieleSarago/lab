package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import classifica.Classifica;
import classifica.Nominativo;
import finestra.FinestraAzzeraClassifica;
import finestra.FinestraNessunPunteggio;
import gfx.Risorse;
import gfx.Suono;
import gioco.Handler;
import lingue.Lingua;
import pannelli.Sfondo;

public class StatoClassifica extends Stato{
	
	private Handler h;
	private Sfondo s;
	private Suono suono;
	private Classifica c = new Classifica();
	
	private int sceltaCorrente=0;
	
	private Lingua lingua;
	private String [] opzioni = {"AZZERA CLASSIFICA","TORNA AL MENU"};
	
	
	//per regolarizzare gli aggiornamenti
	private int fps=60;
	double tempoDiAggiornamento=1000000000/fps;
	double delta=0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer=0;
	

	public StatoClassifica(Handler h) {
		super(h);
		this.h=h;
		s = new Sfondo("res/img/sfondi/menu.png",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		suono = h.getSuono();
		try {
			c.carica("res/classifiche/classificaPunteggio.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void aggiorna(){
		s.aggiorna();
		//ottimizzazione
		ora=System.nanoTime();
		delta += (ora-ultimoTempo)/tempoDiAggiornamento;
		timer += (ora-ultimoTempo);
		ultimoTempo = ora;
		if(delta>=6){
			getInput();
			delta-=6;
		}
	}

	@Override
	public void disegna(Graphics g) {
		s.disegna(g);
		BufferedImage voce;
		for(int i=0;i<opzioni.length;i++){
			if(i==sceltaCorrente)
				voce = Risorse.voci_classifica[i];
			else
				voce = Risorse.voci_classifica_off[i];
			g.drawImage(voce, 450,500+i*50,null);
		}
		g.setColor(Color.black);
		Font myFont = new Font("Areal",1,40);
		g.setFont(myFont);
		g.drawString("1.", 400, 250);
		g.drawString("2.",400,350);
		g.drawString("3.", 400, 450);
		int j = 250,cont=0; //cont serve per non stampare più di 3 nominativi
		for(Nominativo n: c){
			g.drawString(n.getPunteggio(), 500, j);
			g.drawString(n.getNome(),600,j);
			j+=100;cont++;
			if(cont>=3)break;
		}
	}
	
	private void seleziona(){
		if(sceltaCorrente==0){
			if(c.size()!=0){
				new FinestraAzzeraClassifica(h);
			}
			else new FinestraNessunPunteggio(h);
		}
		else h.getGioco().setStato(new StatoMenu(h));
	}
	
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
				seleziona();
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
				suono.riproduci(Suono.suoni.CONFERMA);
			}
			if(h.getGestioneInput().esc){
				suono.riproduci(Suono.suoni.CONFERMA);
				h.getGioco().setStato(new StatoMenu(h));
			}
	}

}
