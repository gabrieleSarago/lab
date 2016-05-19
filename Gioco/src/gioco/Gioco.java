package gioco;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import finestra.Finestra;
import gfx.*;
import input.GestioneInput;
import lingue.Lingua;
import stati.*;

public class Gioco implements Runnable {
	
	private Finestra f;
	private int larghezza,altezza;
	public String titolo;
	
	//gestione stati
	private boolean inCorso = false;
	private boolean inPausa = false;

	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//lingua
	private Lingua lingua;
	private String linea;
	
	//Suoni
	private Suono suono;
	
	//Stati
	private static Stato stato;
	//Input
	private GestioneInput gi;
	 
	//Camera
	private CameraGioco cg;
	
	//Handler
	private Handler h;
	
	public Gioco(String titolo, int larghezza,int altezza){
		this.larghezza = larghezza;
		this.altezza = altezza;
		this.titolo = titolo;
		gi = new GestioneInput();
	}
	
	private void inizializza(){
		
		Risorse.inizializza();
		
		h = new Handler(this);
		f = new Finestra(titolo, larghezza, altezza, h);
		f.getFrame().addKeyListener(gi);
		f.getFrame().setIconImage(CaricatoreImmagini.caricaImmagine("res/img/Sprite/icon_link.png"));
		
		suono = new Suono();
		
		lingua = new Lingua();
		linea = lingua.getLingua();
		
		if(linea == null){
			lingua.setLingua("ENGLISH");
			Risorse.inizializzaENG();
		}
		
		else{
			switch(linea){
			case "ITALIANO" : Risorse.inizializzaITA(); break;
			case "DEUTSCH" : Risorse.inizializzaDEU(); break;
			default : Risorse.inizializzaENG(); break;
			}
		}
		
		cg = new CameraGioco(h, 0, 0);
		
		suono.carica();
		
		if(linea == null)
			stato = new StatoOpzioni(h, suono);
		else
			stato = new StatoMenu(h);
		
		Stato.setStato(stato);

	}
	
	public void setStato(Stato stati){
		stato = stati;
	}
	
	public Stato getStato(){return stato;}
	
	public void setPausa(boolean pausa){
		inPausa = pausa;
	}
	
	public boolean getPausa(){return inPausa;}
	
	private void aggiorna(){
		
		gi.aggiorna();
		
		if ( Stato.getStato() != null){
			Stato.getStato().aggiorna();
			Stato.setStato(stato);
		}
	}
	
	private void disegna(){
		bs = f.getCanvas().getBufferStrategy();
		if(bs == null){
			f.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//cancella il precedente
		g.clearRect(0, 0, larghezza, altezza);
		//inzia a disegnare qua!
		
		
		if ( Stato.getStato() != null)
			Stato.getStato().disegna(g);
		
		// finisce a disegnare qua!
		bs.show();
		g.dispose();
	}
	
	public void run(){
		
		inizializza();
		
		int fps = 60;
		double tempoDiAggiornamento = 1000000000 / fps;
		double tempoDiAggiornamentoMenu = 1000000000 / 55;
		double delta = 0;
		long ora;
		long ultimoTempo = System.nanoTime();
		double ultimoCiclo = 0;

		//long timer = 0;
		//int ticks = 0;
		
		while(inCorso){
			ora = System.nanoTime();
			//frame dinamico
			if(h.getGioco().getStato() instanceof StatoGioco)
				ultimoCiclo = (ora - ultimoTempo) / tempoDiAggiornamento;
			else
				ultimoCiclo = (ora - ultimoTempo) / tempoDiAggiornamentoMenu;
			delta+=ultimoCiclo;
			//timer += ora - ultimoTempo;
			ultimoTempo = ora;
			if(delta >= 1){
				aggiorna();
				disegna();
				//ticks++;
				delta--;
				
				//Se l'errore accumulato si avvicina a 1 viene ribassato
				//alla quantità ultimoCiclo. Tutto ciò serve a evitare due
				//aggiornamenti consecutivi del gioco.
				
				if(delta+ultimoCiclo >= 1)
					 delta = ultimoCiclo;
				
			}
			//if (timer >= 1000000000){
				//System.out.println("Ticks: "+ ticks);
				//ticks = 0;
				//timer = 0;
			//}
		}
		
		stop();
		
	}
	
	public GestioneInput getGestioneInput(){
		return gi;
	}
	
	public Thread getThread(){return thread;}
	
	public CameraGioco getCameraGioco(){
		return cg;
	}
	
	public int getLarghezza() {
		return larghezza;
	}
	
	public int getAltezza() {
		return altezza;
	}

	public synchronized void start(){
		if(inCorso)
			return;
		inCorso = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!inCorso)
			return;
		inCorso = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public JFrame getFrame(){
		return f.getFrame();
	}
	
	public Lingua getLingua(){return lingua;}
	
	public Suono getSuono(){
		return suono;
	}
}
