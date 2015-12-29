package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gfx.Animazione;
import gfx.Risorse;
import gioco.Handler;
import pannelli.Sfondo;

public class StatoPausa extends Stato{

	private Sfondo s;
	
	private int sceltaCorrente = 0;
	
	private Color coloreTitolo;
	private Font fontTitolo;
	private Font font;
	
	int fps = 60;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;
	
	private StatoGioco precedente;
	private Animazione sinkDestra;

	public StatoPausa(Handler h, StatoGioco precedente) {
		super(h);
		this.precedente = precedente;
		
		s = new Sfondo("res/img/pausa.png", h);
		
		s.setVector(-0.3, 0);
		coloreTitolo = Color.BLACK;
		fontTitolo = new Font("Arial", Font.BOLD, 40);
		font = new Font("Arial", Font.BOLD, 30);
		sinkDestra = new Animazione(100, Risorse.sink_destra);
	}

	@Override
	public void aggiorna() {
		sinkDestra.aggiorna();
		precedente.getSink().aggiorna();
		s.aggiorna();
		ora = System.nanoTime();
		delta +=(ora - ultimoTempo) / tempoDiAggiornamento;
		timer += ora - ultimoTempo;
		ultimoTempo = ora;
		if(delta >= 6){
			getInput();
			delta -= 6;
		}
	}

	@Override
	public void disegna(Graphics g) {
		//disegna sfondo
		s.disegna(g);
				
		//disegna titolo
		g.setColor(coloreTitolo);
		g.setFont(fontTitolo);
		g.drawString("PAUSA", 420, 100);
		
		//disegna menu
		g.setFont(font);
		for(int i = 0; i<Risorse.voci_pausa.length; i++){
			if(i == sceltaCorrente){
				g.drawImage(Risorse.voci_pausa[i], 400, 170 + i*60, null);
			}
			else
				g.drawImage(Risorse.voci_pausa_off[i], 400, 170 + i*60, null);
		}
	}
	
	private void seleziona(){
		h.getGioco().setPausa(false);
		if(sceltaCorrente == 0){
			h.getGioco().setStato(precedente);
		}
		if(sceltaCorrente == 1){
			h.getGioco().setStato(new StatoGioco(h));
		}
		if(sceltaCorrente == 2){
			h.getLivello().salva("res/livelli/livelloS.txt", (int)precedente.getSink().getX(), (int) precedente.getSink().getY(), precedente.getSink().getTempo());
		}
		if(sceltaCorrente == 3)
			h.getGioco().setStato(new StatoMenu(h));
	}
	
	private void getInput(){
		if(h.getGestioneInput().up){
			sceltaCorrente--;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente<0){
				sceltaCorrente = Risorse.voci_pausa.length-1;			}
		}
		if(h.getGestioneInput().down){
			sceltaCorrente++;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente > Risorse.voci_pausa.length-1)
				sceltaCorrente = 0;
		}
		if(h.getGestioneInput().enter){
			seleziona();
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		}
	}

}
