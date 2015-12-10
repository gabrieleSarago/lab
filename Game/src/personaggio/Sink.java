package personaggio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Animation;
import gfx.Risorse;
import gioco.Handler;

public class Sink extends Personaggio{
	
	//Animazioni
	private Animation down;
	private Animation up;
	private Animation right;
	private Animation left;
	
	public Sink(Handler h, float x, float y) {
		super(h, x, y, Personaggio.DEFAULT_LARGHEZZA_PERSONAGGIO, Personaggio.DEFAULT_ALTEZZA_PERSONAGGIO);
		
		bounds.x = 16;
		bounds.y = 32;
		bounds.width = 32;
		bounds.height = 32;
		
		//Animazioni
		down = new Animation(100, Risorse.giocatore_down);
		up = new Animation(100, Risorse.giocatore_up);
		right = new Animation(100, Risorse.giocatore_right);
		left = new Animation(100, Risorse.giocatore_left);

	}

	@Override
	public void aggiorna() {
		//Animazioni
		down.aggiorna();
		up.aggiorna();
		right.aggiorna();
		left.aggiorna();
		//Movimento
		getInput();
		muovi();
		h.getCameraGioco().centra(this);
	}
	
	private void getInput(){
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
		g.drawImage(getAnimazioneFrameCorrente(), (int)(x - h.getCameraGioco().getxOffset()), (int)(y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
		/*g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - h.getCameraGioco().getxOffset()),
					(int) (y + bounds.y - h.getCameraGioco().getyOffset()),
					bounds.width, bounds.height);*/
	}
	
	private BufferedImage getAnimazioneFrameCorrente(){
		if(dx <0){
			return left.getFrameCorrente();
		}else if(dx > 0){
			return right.getFrameCorrente();
		}else if(dy < 0){
			return up.getFrameCorrente();
		}else{
			return down.getFrameCorrente();
		}
	}

}
