package coder5560.gdxai;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends SteeringActor{

	public Enemy(TextureRegion region, boolean independentFacing) {
		super(region, independentFacing);
	}

	public Enemy(TextureRegion region) {
		super(region);
	}

}
