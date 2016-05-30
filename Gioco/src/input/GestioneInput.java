package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Oggetto che serve a gestire l'input del gioco.
 */
public class GestioneInput implements KeyListener {
	
	private boolean[] tasti;
	public boolean up, down, left, right, enter, esc;
	private KeyEvent c;
	
	/**
	 * Costruisce l'oggetto GestioneInput.
	 */
	public GestioneInput(){
		tasti = new boolean[6];
	}
	
	/**
	 * Aggiorna gli eventi da tastiera.
	 */
	public void aggiorna(){
		up = tasti[KeyEvent.VK_UP-35];
		down = tasti[KeyEvent.VK_DOWN-35];
		left = tasti[KeyEvent.VK_LEFT-35];
		right = tasti[KeyEvent.VK_RIGHT-35];
		enter = tasti[KeyEvent.VK_ENTER-10];
		esc = tasti[KeyEvent.VK_ESCAPE-26];
	}
	
	/**
	 * Vengono "disabilitati" i listener della tastiera.
	 */
	public void azzera(){
		up = false;
		down = false;
		left = false;
		right = false;
		enter = false;
		esc = false;
		
		for(int i = 0; i< tasti.length; i++){
			tasti[i] = false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		
		switch(k.getKeyCode()){
		case KeyEvent.VK_ENTER: tasti[KeyEvent.VK_ENTER-10] = true; break;
		case KeyEvent.VK_ESCAPE: tasti[KeyEvent.VK_ESCAPE-26] = true; break;
		case KeyEvent.VK_UP: tasti[KeyEvent.VK_UP-35] = true; break;
		case KeyEvent.VK_DOWN: tasti[KeyEvent.VK_DOWN-35] = true; break;
		case KeyEvent.VK_LEFT: tasti[KeyEvent.VK_LEFT-35] = true; break;
		case KeyEvent.VK_RIGHT: tasti[KeyEvent.VK_RIGHT-35] = true; break;
		}
		c = k;
	}
	
	public KeyEvent getKeyEvent(){return c;}

	@Override
	public void keyReleased(KeyEvent k) {
		switch(k.getKeyCode()){
		case KeyEvent.VK_ENTER: tasti[KeyEvent.VK_ENTER-10] = false; break;
		case KeyEvent.VK_ESCAPE: tasti[KeyEvent.VK_ESCAPE-26] = false; break;
		case KeyEvent.VK_UP: tasti[KeyEvent.VK_UP-35] = false; break;
		case KeyEvent.VK_DOWN: tasti[KeyEvent.VK_DOWN-35] = false; break;
		case KeyEvent.VK_LEFT: tasti[KeyEvent.VK_LEFT-35] = false; break;
		case KeyEvent.VK_RIGHT: tasti[KeyEvent.VK_RIGHT-35] = false; break;
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		
	}
	
}
