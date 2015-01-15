package engine.module.view.imp;

import utils.listener.OnCompleteListener;
import coder5560.gdxai.SteeringActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.ai.steer.limiters.LinearAccelerationLimiter;
import com.badlogic.gdx.ai.steer.proximities.RadiusProximity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import engine.module.view.IController;
import engine.module.view.IViewElement;
import engine.module.view.ViewElement;
import engine.module.view.ViewName;

public class ViewCollisionAvoid extends ViewElement {
	Array<SteeringActor> characters;
	RadiusProximity<Vector2> char0Proximity;
	Array<RadiusProximity<Vector2>> proximities;
	SteeringActor target;

	public ViewCollisionAvoid(ViewName viewParentName, IController controller,
			ViewName viewName, Rectangle bound) {
		super(viewParentName, controller, viewName, bound);
	}

	@Override
	public IViewElement show(Stage stage, float duration,
			OnCompleteListener listener) {
		super.show(stage, duration, listener);
		buildComponent();
		return this;
	}

	@Override
	public IViewElement buildComponent() {
		String dir = "data/ball.png";
		TextureRegion region = new TextureRegion(new Texture(
				Gdx.files.internal(dir)));

		target = new SteeringActor(region);
		target.setColor(Color.GREEN);
		target.setSize(20, 20);
		target.setOrigin(Align.center);
		target.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		addActor(target);

		characters = new Array<SteeringActor>();
		proximities = new Array<RadiusProximity<Vector2>>();

		int max = 40;
		for (int i = 0; i < max; i++) {
			final SteeringActor character = new SteeringActor(region, false);
			character.setSize(20, 20);
			if (i > max / 2) {
				character.setMaxLinearSpeed(100);
				character.setMaxLinearAcceleration(200);
				character.setColor(Color.BLUE);

				final Arrive<Vector2> arriveSB = new Arrive<Vector2>(character,
						target) //
						.setTimeToTarget(0.1f) //
						.setArrivalTolerance(0.001f) //
						.setDecelerationRadius(20);
				character.setSteeringBehavior(arriveSB);

			} else {
				character.setMaxLinearSpeed(0);
				character.setMaxLinearAcceleration(0);
				character.setColor(Color.RED);
			}

			RadiusProximity<Vector2> proximity = new RadiusProximity<Vector2>(
					character, characters, character.getBoundingRadius() * 4);
			proximities.add(proximity);
			if (i == 0)
				char0Proximity = proximity;
			CollisionAvoidance<Vector2> collisionAvoidanceSB = new CollisionAvoidance<Vector2>(
					character, proximity);

			Wander<Vector2> wanderSB = new Wander<Vector2>(character) //
					// Don't use Face internally because independent facing is
					// off
					.setFaceEnabled(false) //
					// We don't need a limiter supporting angular components
					// because Face is not used
					// No need to call setAlignTolerance, setDecelerationRadius
					// and setTimeToTarget for the same reason
					.setLimiter(new LinearAccelerationLimiter(40)) //
					.setWanderOffset(60) //
					.setWanderOrientation(10) //
					.setWanderRadius(40) //
					.setWanderRate(MathUtils.PI / 5);

			PrioritySteering<Vector2> prioritySteeringSB = new PrioritySteering<Vector2>(
					character, 0.0001f);
			prioritySteeringSB.add(collisionAvoidanceSB);
			prioritySteeringSB.add(wanderSB);
			character.setSteeringBehavior(prioritySteeringSB);
			setRandomNonOverlappingPosition(character, characters, 20);

			addActor(character);
			characters.add(character);
		}

		buildListener();
		setupAI();
		return this;
	}

	protected void setRandomNonOverlappingPosition(SteeringActor character,
			Array<SteeringActor> others, float minDistanceFromBoundary) {
		int maxTries = Math.max(100, others.size * others.size);
		SET_NEW_POS: while (--maxTries >= 0) {
			character.setPosition(MathUtils.random(getWidth()),
					MathUtils.random(getHeight()), Align.center);
			character.getPosition().set(character.getX(Align.center),
					character.getY(Align.center));
			for (int i = 0; i < others.size; i++) {
				SteeringActor other = (SteeringActor) others.get(i);
				if (character.getPosition().dst(other.getPosition()) <= character
						.getBoundingRadius()
						+ other.getBoundingRadius()
						+ minDistanceFromBoundary)
					continue SET_NEW_POS;
			}
			return;
		}
		throw new GdxRuntimeException("Probable infinite loop detected");
	}

	public void buildListener() {
		setDrawChildren(true);
		setTouchable(Touchable.enabled);
		clearListeners();
		clearListeners();
		addListener(new ActorGestureListener() {

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				target.setPosition(x, y, Align.center);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				target.setPosition(x, y, Align.center);
			}
		});
	}

	public void setupAI() {

	}

	@Override
	public IViewElement hide(float duration, OnCompleteListener listener) {
		super.hide(duration, listener);
		return this;
	}

	@Override
	public void back() {
		super.back();
	}
}
