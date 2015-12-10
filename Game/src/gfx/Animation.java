package gfx;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int velocita, indice;
	private long ultimoTempo, timer;
	private BufferedImage[] frames;
	
	public Animation(int velocita, BufferedImage[] frames){
		this.velocita = velocita;
		this.frames = frames;
		indice = 0;
		timer = 0;
		ultimoTempo = System.currentTimeMillis();
	}
	
	public void aggiorna(){
		timer += System.currentTimeMillis() - ultimoTempo;
		ultimoTempo = System.currentTimeMillis();
		
		if(timer > velocita){
			indice++;
			timer = 0;
			if(indice >= frames.length)
				indice = 0;
		}
	}
	
	public BufferedImage getFrameCorrente(){
		return frames[indice];
	}
}
