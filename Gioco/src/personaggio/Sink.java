package personaggio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.TimeUnit;

import gfx.Animazione;
import gfx.Risorse;
import gioco.Handler;

public class Sink extends Personaggio {
	
	public enum Movimento{ SOPRA, SOTTO, DESTRA, SINISTRA}
	
	private int tempo = 0;
	private int maxTempo;
	private boolean sconfitta = false;
	
	//Animazione
	private Animazione SinkSotto, SinkSopra, SinkSinistra, SinkDestra;
	
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
	
	public Sink(){} // necessario per esternalizzazione
	
	public Sink(Handler h, float x, float y, int tempo) {
		super(h, x, y, Personaggio.DEFAULT_LARGHEZZA_PERSONAGGIO, Personaggio.DEFAULT_ALTEZZA_PERSONAGGIO);
		this.tempo = tempo;
		
		maxTempo = tempo;
		
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
		
	}

	
	@Override
	public void aggiorna() {
		
		//gestione pausa
		if(!h.getGioco().getPausa()){
			
			//Miglioramenti aggiornamenti animazioni.
			 if(dx<0)
				 SinkSinistra.aggiorna();
			 else if(dx > 0)
				 SinkDestra.aggiorna();
			 else if(dy < 0)
				 SinkSopra.aggiorna();
			 else if(dy > 0)
				 SinkSotto.aggiorna();
			 else{
				 switch(ultimoMovimento){
				 case SOTTO: SinkSottoFermo.aggiorna();break;
				 case SINISTRA: SinkSinistraFermo.aggiorna(); break;
				 case DESTRA: SinkDestraFermo.aggiorna();break;
				 case SOPRA: break;
				 }
			}
			//se si prende una caramella e il tempo � > 95 supera i 100 secondi.
			if(tempo > maxTempo)
				tempo = maxTempo;
			
			if(tempo<0){
				sconfitta = true;
			}
			
			//Movimento
			getInput();
			muovi();
			h.getCameraGioco().centra(this);
			ora = System.nanoTime();
			delta +=(ora - ultimoTempo) / tempoDiAggiornamento;
			timer += ora - ultimoTempo;
			ultimoTempo = ora;
			
			if(timer > TimeUnit.SECONDS.toNanos(1)){
				tempo--;
				timer = 0;
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
		//Disegna la barra del tempo.
		g.setColor(Color.black);
		g.setFont(new Font ("Arial", Font.BOLD,15));
		g.drawString(tempo+"", 180, 52);
		g.fillRect(68, 38, 104 , 14);
		g.setColor(Color.red);
		g.fillRect(70, 40, 100 , 10);
		g.setColor(Color.green);
		if(tempo>maxTempo)
			g.fillRect(70, 40, 100, 10);
		else
			g.fillRect(70, 40, (int)((float)tempo/(float)maxTempo*100), 10);
		
		//Rettangolo collisioni
		//g.setColor(Color.RED);
		//g.fillRect((int)(x + bounds.x +5 - h.getCameraGioco().getxOffset()),
  				//(int)(y + bounds.y - h.getCameraGioco().getyOffset()),
				//bounds.width -15, bounds.height);
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
	
	public boolean getSconfitta(){
		return sconfitta;
	}
	
	//per il processo di esternalizzazione
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
		tempo=in.readInt();
		
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
			
		}


		@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeInt(tempo);
	}
	
}
