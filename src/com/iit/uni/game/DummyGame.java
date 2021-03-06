package com.iit.uni.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import com.iit.uni.engine.C2DGraphicsLayer;
import com.iit.uni.engine.C2DScene;
import com.iit.uni.engine.C2DSceneManager;
import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.IGameLogic;
import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.math.Vector2D;

public class DummyGame implements IGameLogic {

	private final Renderer renderer;
	private int direction = 0;

	// 2D GameObject items
	private GameObject2D gameItem;
	private GameObject2D gameItemMonster;
	// Global Scene manager
	public static C2DSceneManager sceneManager;

	private C2DScene scene;

	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		/**
		 * Creating an animated game object
		 */
		gameItemMonster = new GameObject2D();
		CSprite monster = new CSprite("textures/space",1,10,10);
		gameItemMonster.AddFrame(monster);
		gameItemMonster.SetPosition(200,100);
		
		
		gameItem = new GameObject2D();

		CSprite frameRunRight = new CSprite("textures/Run", 8, 200, 200);
		CSprite frameRunLeft = new CSprite("textures/Run_left", 8, 200, 200);
		CSprite idle = new CSprite("textures/Idle", 10, 200, 200);
		CSprite jumpRight = new CSprite("textures/Jump_right_", 10, 200, 200);
		CSprite jumpLeft = new CSprite("textures/Jump_left_", 10, 200, 200);

		gameItem.AddFrame(idle);
		gameItem.AddFrame(frameRunRight);
		gameItem.AddFrame(frameRunLeft);
		gameItem.AddFrame(jumpRight);
		gameItem.AddFrame(jumpLeft);

		gameItem.SetPosition(200, 504);

		sceneManager = new C2DSceneManager();
		scene = new C2DScene();

		// Create a background texture
		Texture2D background = new Texture2D();
		background.CreateTexture("textures/background/layer_sd_01.png");

		// Create a cloud layer
		Texture2D clouds = new Texture2D();
		clouds.CreateTexture("textures/background/layer_sd_02.png");

		// Create a mountain layer
		Texture2D mountains = new Texture2D();
		mountains.CreateTexture("textures/background/layer_sd_03.png");

		// Create a tree layer
		Texture2D trees = new Texture2D();
		trees.CreateTexture("textures/background/layer_sd_04.png");

		// Create a ground layer
		Texture2D ground = new Texture2D();
		ground.CreateTexture("textures/background/layer_sd_05.png");

		// Create graphics layer
		C2DGraphicsLayer layer0 = new C2DGraphicsLayer();
		layer0.AddTexture(background);

		// Create graphics layer
		C2DGraphicsLayer layer1 = new C2DGraphicsLayer();
		layer1.AddTexture(clouds);

		// Create graphics layer
		C2DGraphicsLayer layer2 = new C2DGraphicsLayer();
		layer2.AddTexture(mountains);

		C2DGraphicsLayer layer3 = new C2DGraphicsLayer();
		layer3.AddTexture(trees);

		C2DGraphicsLayer layer4 = new C2DGraphicsLayer();
		layer4.AddTexture(ground);

		C2DGraphicsLayer playerLayer = new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItem);
		
		C2DGraphicsLayer monsterLayer = new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItemMonster);
		
		// register layer at the scene
		scene.RegisterLayer(layer0);
		scene.RegisterLayer(layer1);
		scene.RegisterLayer(layer2);
		scene.RegisterLayer(layer3);
		scene.RegisterLayer(layer4);
		scene.RegisterLayer(playerLayer);
		scene.RegisterLayer(monsterLayer);

		// Register scene at the manager
		sceneManager.RegisterScene(scene);
	}

	@Override
	public void input(Window window) {

		if (window.isKeyPressed(GLFW_KEY_UP)) {

			if (direction == 1) {
				gameItem.SetCurrentFrame(3);
			} else {
				gameItem.SetCurrentFrame(4);
			}
			Vector2D pos = gameItem.GetPosition();
			pos.y -= 5;
			gameItem.SetPosition(pos);

		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			Vector2D pos = gameItem.GetPosition();
			pos.y += 5;
			gameItem.SetPosition(pos);

		} else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			direction = -1;
			gameItem.SetCurrentFrame(2);
			Vector2D pos = gameItem.GetPosition();
			pos.x -= 5;
			gameItem.SetPosition(pos);
		} else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			direction = 1;
			gameItem.SetCurrentFrame(1);
			Vector2D pos = gameItem.GetPosition();
			pos.x += 5;
			gameItem.SetPosition(pos);
		}
	}

	@Override
	public void update(float interval) {

	}

	@Override
	public void render(Window window) {
		renderer.render(window);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		gameItem.cleanUp();
	}
}
