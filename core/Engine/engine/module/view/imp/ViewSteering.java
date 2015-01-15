package engine.module.view.imp;

import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import utils.listener.OnCompleteListener;
import coder5560.gdxai.SteeringActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
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

import engine.module.view.IController;
import engine.module.view.IViewElement;
import engine.module.view.ViewElement;
import engine.module.view.ViewName;

public class ViewSteering extends ViewElement {
	Texture textureball;
	SteeringActor target, character;

	public ViewSteering(ViewName viewParentName, IController controller,
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
		textureball = new Texture(Gdx.files.internal(dir));

		character = new SteeringActor(new TextureRegion(textureball), false);
		character.setColor(Color.RED);
		character.setSize(20, 20);
		character.setOrigin(Align.center);

		target = new SteeringActor(new TextureRegion(textureball));
		target.setColor(Color.BLUE);
		target.setSize(20, 20);
		target.setOrigin(Align.center);

		character.setPosition(MathUtils.random(100, 700),
				MathUtils.random(40, 400), Align.center);
		target.setPosition(getWidth() / 2, getHeight() / 2);

		addActor(character);
		addActor(target);

		buildListener();
		setupAI();
		return this;
	}

	public void buildListener() {
		setDrawChildren(true);
		setTouchable(Touchable.enabled);
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
		// Set character's limiter
		character.setMaxLinearSpeed(200);
		character.setMaxLinearAcceleration(600);

		final Arrive<Vector2> arriveSB = new Arrive<Vector2>(character, target) //
				.setTimeToTarget(0.1f) //
				.setArrivalTolerance(0.001f) //
				.setDecelerationRadius(20);
		character.setSteeringBehavior(arriveSB);

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
