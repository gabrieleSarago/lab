package personaggio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Animazione;
import gfx.Risorse;
import gioco.Handler;

public class Sink extends Personaggio {
	
	public enum Movimento{ SOPRA, SOTTO, DESTRA, SINISTRA}
	
	private int tempo = 0;
	
	//Animazione
	private Animazione SinkSotto, SinkSopra, SinkSinistra, SinkDestra, vita;
	
	//AnimazioneFermo
	private Animazione SinkSottoFermo, SinkSinistraFermo, SinkDestraFermo;
	private BufferedImage SinkSopraFermo;
	private Movimento ultimoMovimento = Movimento.SOTTO;
	
	//per regolarizzare i movmenti
	int fps = 60;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;
	
	public Sink(Handler h, float x, float y, int tempo) {
		super(h, x, y, Personaggio.DEFAULT_LARGHEZZA_PERSONAGGIO, Personaggio.DEFAULT_ALTEZZA_PERSONAGGIO);
		this.tempo = tempo;
		
		bounds.x = 8;
		bounds.y = 30;
		bounds.width = 44;
		bounds.height = 32;
		
		//Animazioni
		SinkSotto = new Animazione(75, Risorse.sink_sotto);
		SinkSopra = new Animazione(75, Risorse.sink_sopra);
		SinkSinistra = new Animazione(75, Risorse.sink_sinistra);
		SinkDestra = new Animazione(75, Risorse.sink_destra);
		
		//AnimazioniFermo
		SinkSottoFermo = new Animazione(300, Risorse.sink_sotto_fermo);
		SinkSopraFermo = Risorse.sink_sopra_fermo;
		SinkSinistraFermo = new Animazione(300, Risorse.sink_sinistra_fermo);
		SinkDestraFermo = new Animazione(300, Risorse.sink_destra_fermo);
		
		//Animazione barra vita
		vita = new Animazione(75, Risorse.tempo);
	}

	
	@Override
	public void aggiorna() {
		//gestione pausa
		if(!h.getGioco().getPausa()){
			//Animazioni
			SinkSotto.aggiorna();
			SinkSopra.aggiorna();
			SinkSinistra.aggiorna();
			SinkDestra.aggiorna();
			SinkSottoFermo.aggiorna();
			SinkSinistraFermo.aggiorna();
			SinkDestraFermo.aggiorna();
			//Movement
			getInput();
			muovi();
			h.getCameraGioco().centra(this);
			ora = System.nanoTime();
			delta +=(ora - ultimoTempo) / tempoDiAggiornamento;
			timer += ora - ultimoTempo;
			ultimoTempo = ora;
			if(delta >= 50){
				tempo--;
				if(tempo%10 == 0)
					vita.aggiorna();
				delta -= 50;
			}
		}
		else{
			ultimoTempo = System.nanoTime();
		}
	}
	
	private void getInput() {
		dx = 0;
		dy = 0;
		
		if(h.getGestioneInput().up)
			dy = -velocita;
		if(h.getGestioneInput().down)
			dy = velocita;
		if(h.getGestioneInput().left)
			dx = -velocita;
		if(h.getGestioneInput().right)
			dx = velocita;
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(getFrameAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		g.setColor(Color.black);
		g.setFont(new Font ("Arial", Font.BOLD,15));
		//g.fillRect((int)(x + bounds.x - h.getCameraGioco().getxOffset()),
				//(int)(y + bounds.y - h.getCameraGioco().getyOffset()),
				//bounds.width, bounds.height);
		//g.fillRect(0, 0, tempo, 10);
		//g.drawString(tempo+"", tempo+2, 11);
		g.drawImage(vita.getFrameCorrente(), 70, 40, null);
	}
	private BufferedImage getFrameAnimazioneCorrente(){
		if (dx < 0){
			ultimoMovimento = Movimento.SINISTRA;
			return SinkSinistra.getFrameCorrente();
		}else if(dx > 0){
			ultimoMovimento = Movimento.DESTRA;
			return SinkDestra.getFrameCorrente();
		}else if (dy < 0){
			ultimoMovimento = Movimento.SOPRA;
			return SinkSopra.getFrameCorrente();
		}else if(dy >0){
			ultimoMovimento = Movimento.SOTTO;
			return SinkSotto.getFrameCorrente();
		}else{
			if(ultimoMovimento == Movimento.SINISTRA){
				return SinkSinistraFermo.getFrameCorrente();
			}else if(ultimoMovimento == Movimento.DESTRA){
				return SinkDestraFermo.getFrameCorrente();
			}else if(ultimoMovimento == Movimento.SOTTO){
				return SinkSottoFermo.getFrameCorrente();
			}else{
				return SinkSopraFermo;
			}
			
		}
	}


	public int getTempo() {
		return tempo;
	}


	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
}
