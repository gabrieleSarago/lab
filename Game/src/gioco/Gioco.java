package gioco;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import finestra.Finestra;
import gfx.CameraGioco;
import gfx.Risorse;
import input.GestioneInput;
import stati.Stato;
import stati.StatoClassifica;
import stati.StatoGioco;
import stati.StatoMenu;
import stati.StatoOpzioni;

public class Gioco implements Runnable{
	
	private Finestra f;
	private int altezza, larghezza;
	private String titolo;
	
	private boolean inCorso = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;
	
	//stati
	private Stato statoGioco;
	private Stato statoMenu;
	private Stato statoClassifica;
	private Stato statoOpzioni;

	//input
	private GestioneInput gi;
	
	//Camera
	private CameraGioco cg;
	
	//Handler
	private Handler h;
	
	public Gioco(String titolo, int larghezza, int altezza){
		this.titolo = titolo;
		this.altezza = altezza;
		this.larghezza = larghezza;
	}
	
	private void inizializza(){
		f = new Finestra(titolo, larghezza, altezza);
		gi = new GestioneInput();
		f.getFrame().addKeyListener(gi);
		Risorse.inizializza();
		h = new Handler(this);

		cg = new CameraGioco(h,0, 0);
		
		statoGioco = new StatoGioco(h);
		statoMenu = new StatoMenu(h);
		statoClassifica = new StatoClassifica(h);
		statoOpzioni = new StatoOpzioni(h);
		Stato.setStato(statoGioco);
	}
	
	public GestioneInput getGestioneInput(){
		return gi;
	}
	
	public CameraGioco getCameraGioco(){
		return cg;
	}
	
	public int getAltezza() {
		return altezza;
	}

	public int getLarghezza() {
		return larghezza;
	}
	
	public synchronized void start(){
		if(inCorso) return;
		inCorso = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!inCorso) return;
		inCorso = false;
		try {
			thread.join();
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	private void disegna(){
		bs = f.getCanvas().getBufferStrategy();
		if(bs == null){
			f.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, larghezza, altezza);
		
		if(Stato.getStato() != null){
			Stato.getStato().disegna(g);
		}
		
		bs.show();
		g.dispose();
	
	}
	
	private void aggiorna(){
		gi.aggiorna();
		
		if(Stato.getStato()!= null){
			Stato.getStato().aggiorna();
		}
	}
	
	@Override
	public void run() {
		inizializza();
		
		int fps = 60;
		
		double tempoDiAggiornamento = 1000000000/fps;
		double delta = 0;
		long ora;
		long ultimoTempo = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(inCorso){
			ora = System.nanoTime();
			delta += (ora-ultimoTempo)/tempoDiAggiornamento;
			timer += ora - ultimoTempo;
			ultimoTempo = ora;
			if(delta > 1){
				aggiorna();
				disegna();
				ticks++;
				delta--;
			}
			if(timer > 1000000000){
				//System.out.println("ticks "+ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
}
