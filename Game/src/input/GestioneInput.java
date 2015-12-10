package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GestioneInput implements KeyListener{

	private boolean[] tasti;
	public boolean up, down, left, right;

	public GestioneInput(){
		tasti = new boolean[256];
	}

	public void aggiorna(){
		up = tasti[KeyEvent.VK_UP];
		down = tasti[KeyEvent.VK_DOWN];
		right = tasti[KeyEvent.VK_RIGHT];
		left = tasti[KeyEvent.VK_LEFT];
	}

	@Override
	public void keyPressed(KeyEvent k) {
		tasti[k.getKeyCode()] = true;
		//System.out.println("premuto!");
	}

	@Override
	public void keyReleased(KeyEvent k) {
		tasti[k.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent k) {
		
	}

}
