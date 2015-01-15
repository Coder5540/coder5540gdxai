package engine.module.view;

import utils.listener.OnComplete;

import com.badlogic.gdx.math.Rectangle;
import com.coder5560.game.enums.Constants;

import engine.element.Engine;
import engine.module.screens.AbstractGameScreen;
import engine.module.screens.GameCore;
import engine.module.view.imp.ViewCollisionAvoid;
import engine.module.view.imp.ViewSteering;
import engine.module.view.imp.HomeView;

public class VController extends Controller {

	public VController(GameCore _GameCore, Engine _Engine,
			AbstractGameScreen _Screen) {
		super(_GameCore, _Engine, _Screen);
	}

	@Override
	public IController buildController() {
		// HomeView viewTest = new HomeView(ViewName.DEFAULT, this,
		// ViewName.HOME_VIEW, new Rectangle(0, 0, Constants.WIDTH_SCREEN,
		// Constants.HEIGHT_SCREEN));
		// addView(viewTest);
		// viewTest.show(getEngine(), 1f, new OnComplete() {
		// @Override
		// public void onComplete(Object data) {
		// }
		// });

		ViewSteering viewTest = new ViewSteering(ViewName.DEFAULT, this,
				ViewName.HOME_VIEW, new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN));
		addView(viewTest);
		viewTest.show(getEngine(), 1f, new OnComplete() {
			@Override
			public void onComplete(Object data) {
			}
		});

		// ViewCollisionAvoid viewTest = new
		// ViewCollisionAvoid(ViewName.DEFAULT,
		// this, ViewName.HOME_VIEW, new Rectangle(0, 0,
		// Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN));
		// addView(viewTest);
		// viewTest.show(getEngine(), 1f, new OnComplete() {
		// @Override
		// public void onComplete(Object data) {
		// }
		// });

		return this;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public IController onBack() {
		return super.onBack();
	}
}
