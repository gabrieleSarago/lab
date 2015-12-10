package tiles;

import gfx.Risorse;

public class TileMuro extends Tile{

	public TileMuro(int id) {
		super(Risorse.muro, id);
	}
	
	@Override
	public boolean eSolido(){
		return true;
	}

}
