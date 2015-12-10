package gfx;

import java.awt.image.BufferedImage;

public class Sprite {
	
	private BufferedImage sink;
	
	public Sprite(BufferedImage sink){
		this.sink = sink;
	}
	
	public BufferedImage prendiSprite(int x, int y, int larghezza, int altezza){
		return sink.getSubimage(x, y, larghezza, altezza);
	}
}
