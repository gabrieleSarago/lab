package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GestioneInput implements KeyListener {
	
	private boolean[] tasti;
	public boolean up, down, left, right, enter, esc;
	
	public GestioneInput(){
		tasti = new boolean[256];
	}
	
	public void aggiorna(){
		up = tasti[KeyEvent.VK_UP];
		down = tasti[KeyEvent.VK_DOWN];
		left = tasti[KeyEvent.VK_LEFT];
		right = tasti[KeyEvent.VK_RIGHT];
		enter = tasti[KeyEvent.VK_ENTER];
		esc = tasti[KeyEvent.VK_ESCAPE];
	}

	@Override
	public void keyPressed(KeyEvent k) {
		tasti[k.getKeyCode()] = true;
		//System.out.println("Pressed!");
	}

	@Override
	public void keyReleased(KeyEvent k) {
		tasti[k.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent k) {
		
	}
	
}
