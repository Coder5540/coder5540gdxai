package coder5560.gdxai;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends SteeringActor {

	public Player(TextureRegion region, boolean independentFacing) {
		super(region, independentFacing);
	}

	public Player(TextureRegion region) {
		super(region);
	}

}
