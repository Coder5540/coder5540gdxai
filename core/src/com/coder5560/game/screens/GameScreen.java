package com.coder5560.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import engine.module.screens.AbstractGameScreen;
import engine.module.screens.GameCore;

public class GameScreen extends AbstractGameScreen {
	Image					flash;
	public GameScreen(GameCore game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void update(float delta) {
	}


}
