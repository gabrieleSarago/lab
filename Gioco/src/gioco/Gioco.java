package gioco;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import finestra.Finestra;
import gfx.*;
import input.GestioneInput;
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
		f = new Finestra(titolo, larghezza, altezza);
		f.getFrame().addKeyListener(gi);
		Risorse.inizializza();
		
		h = new Handler(this);
		cg = new CameraGioco(h, 0, 0);
		
		stato= new StatoMenu(h);
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
		//long timer = 0;
		//int ticks = 0;
		
		while(inCorso){
			ora = System.nanoTime();
			//frame dinamico
			if(h.getGioco().getStato() instanceof StatoGioco)
				delta +=(ora - ultimoTempo) / tempoDiAggiornamento;
			else
				delta +=(ora - ultimoTempo) / tempoDiAggiornamentoMenu;
			//timer += ora - ultimoTempo;
			ultimoTempo = ora;
			if(delta >= 1){
				aggiorna();
				disegna();
				//ticks++;
				delta--;
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
	
}
