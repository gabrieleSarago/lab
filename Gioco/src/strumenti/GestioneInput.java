package strumenti;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Oggetto che serve a gestire l'input del gioco.
 */
public class GestioneInput implements KeyListener {
  
  public boolean up, down, left, right, enter, esc;
  private KeyEvent c;
  
  /**
   * Costruisce l'oggetto GestioneInput.
   */
  public GestioneInput(){
    up = false; down = false; left = false; right = false; enter = false; esc = false;
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
  }
  
  @Override
  public void keyPressed(KeyEvent k) {
    switch(k.getKeyCode()){
    case KeyEvent.VK_ENTER: enter = true; break;
    case KeyEvent.VK_ESCAPE: esc = true; break;
    case KeyEvent.VK_UP: up = true; break;
    case KeyEvent.VK_DOWN: down = true; break;
    case KeyEvent.VK_LEFT: left = true; break;
    case KeyEvent.VK_RIGHT: right = true; break;
    }
    c = k;
  }
  
  public KeyEvent getKeyEvent(){return c;}

  @Override
  public void keyReleased(KeyEvent k) {
    switch(k.getKeyCode()){
    case KeyEvent.VK_ENTER: enter = false; break;
    case KeyEvent.VK_ESCAPE: esc = false; break;
    case KeyEvent.VK_UP: up = false; break;
    case KeyEvent.VK_DOWN: down = false; break;
    case KeyEvent.VK_LEFT: left = false; break;
    case KeyEvent.VK_RIGHT: right = false; break;
    }
  }

  @Override
  public void keyTyped(KeyEvent k) {
    
  }
}