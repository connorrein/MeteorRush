package impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import gameEngine.Entity;
import gameEngine.Game;
import gameEngine.ResourceLoader;
import gameEngine.Scene;
import gameEngine.SceneObject;
import gameEngine.Vector2;
import impl.Main;
import impl.scenes.GameScene;

public class Marauder extends Entity implements DamagableEntity {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;
    private static final int MAX_HEALTH = 8;
    private static final double SPEED = 200.0;
    private static final int SCORE_VALUE = 300;
    private static final double HEALTH_DROP_CHANCE = 0.25;
    private static final double BASE_CONTACT_DAMAGE = 2.0;
    private static final int LARGE_ORB_WIDTH = 50;
    private static final int LARGE_ORB_HEIGHT = 50;
    private static final double LARGE_ORB_SPEED = 500;
    private static final double LARGE_ORB_LIFETIME = 0.7;
    private static final double LARGE_ORB_COOLDOWN = 3.0;
    private static final double BASE_LARGE_ORB_DAMAGE = 3.0;
    private static final int SMALL_ORB_WIDTH = 30;
    private static final int SMALL_ORB_HEIGHT = 30;
    private static final int SMALL_ORB_SPEED = 600;
    private static final double BASE_SMALL_ORB_DAMAGE = 1.0;

    private static final Image SPRITE_1 = ResourceLoader.loadImage("res/images/entities/marauder/Marauder1.png")
	    .getScaledInstance(WIDTH, HEIGHT, 0);
    private static final Image SPRITE_2 = ResourceLoader.loadImage("res/images/entities/marauder/Marauder2.png")
	    .getScaledInstance(WIDTH, HEIGHT, 0);
    private static final Image LARGE_ORB = ResourceLoader.loadImage("res/images/entities/marauder/MarauderOrb.png")
	    .getScaledInstance(LARGE_ORB_WIDTH, LARGE_ORB_HEIGHT, 0);
    private static final Image SMALL_ORB = ResourceLoader.loadImage("res/images/entities/marauder/MarauderOrb.png")
	    .getScaledInstance(SMALL_ORB_WIDTH, SMALL_ORB_HEIGHT, 0);

    private double health;
    private double nextFireTime;

    public Marauder(Vector2 position) {
	super(position, new Vector2(WIDTH, HEIGHT));
	health = MAX_HEALTH;
	nextFireTime = Game.getInstance().getTime() + 2;
    }

    @Override
    public void tick() {
	Vector2 position = getPosition();
	position.add(-SPEED * Game.getInstance().getDeltaTime(), 0);
	setPosition(position);
	double currentTime = Game.getInstance().getTime();
	if (currentTime > nextFireTime) {
	    fireOrb();
	    nextFireTime = currentTime + LARGE_ORB_COOLDOWN;
	}
    }

    private void fireOrb() {
	Game.getInstance().getOpenScene().addObject(new LargeOrb(getPosition()));
    }

    @Override
    public void render(Graphics g) {
	Vector2 position = getPosition();
	Image sprite;
	double time = Game.getInstance().getTime();
	if (time % 0.3 < 0.15) {
	    sprite = SPRITE_1;
	} else {
	    sprite = SPRITE_2;
	}
	g.drawImage(sprite, (int) (position.getX() - WIDTH / 2.0), (int) (position.getY() - HEIGHT / 2.0), null);
    }

    @Override
    public void damage(double amount) {
	health -= amount;
	if (health <= 0) {
	    destroy();
	}
    }

    private void destroy() {
	GameScene scene = (GameScene) Game.getInstance().getOpenScene();
	scene.removeObject(this);
	scene.addScore(SCORE_VALUE);
	Explosion explosion = new Explosion(getPosition(), 300, 0.4);
	scene.addObject(explosion);
	if (Math.random() < HEALTH_DROP_CHANCE) {
	    scene.addObject(new HealthDrop(getPosition()));
	}
    }

    @Override
    public void onCollisionEnter(Entity other) {
	if (other instanceof PlayerShip) {
	    ((PlayerShip) other).damage(Main.difficulty.getModifier() * BASE_CONTACT_DAMAGE);
	    destroy();
	}
    }

    @Override
    public void onCollisionExit(Entity other) {
    }

    private static class LargeOrb extends Projectile {
	private double deathTime;

	public LargeOrb(Vector2 position) {
	    super(position, new Vector2(LARGE_ORB_WIDTH, LARGE_ORB_HEIGHT), new Vector2(-LARGE_ORB_SPEED, 0));
	    deathTime = Game.getInstance().getTime() + LARGE_ORB_LIFETIME;
	}

	@Override
	public void initialize() {
	    super.initialize();
	    ResourceLoader.loadAudioClip("res/audio/MarauderLargeOrbFire.wav").start();
	}

	@Override
	public void tick() {
	    super.tick();
	    if (Game.getInstance().getTime() > deathTime) {
		blowUp();
	    }
	}

	private void blowUp() {
	    ResourceLoader.loadAudioClip("res/audio/MarauderLargeOrbBlowUp.wav").start();
	    Scene scene = Game.getInstance().getOpenScene();
	    scene.removeObject(this);
	    Vector2 smallOrbVelocity = Vector2.fromAngle(2.0 * Math.PI / 3.0, SMALL_ORB_SPEED);
	    for (int i = 0; i < 5; i++) {
		scene.addObject(new SmallOrb(getPosition(), smallOrbVelocity));
		smallOrbVelocity.rotate(Math.PI / 6.0);
	    }
	}

	@Override
	public void onCollisionEnter(Entity other) {
	    if (other instanceof PlayerShip) {
		((PlayerShip) other).damage(BASE_LARGE_ORB_DAMAGE);
		Scene scene = Game.getInstance().getOpenScene();
		scene.removeObject(this);
		scene.addObject(new OrbSpark(getPosition()));
	    } else if (other instanceof PlayerShip.Laser) {
		Scene scene = Game.getInstance().getOpenScene();
		scene.removeObject(other);
		scene.addObject(new OrbSpark(getPosition()));
		blowUp();
	    }
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	@Override
	public void render(Graphics g) {
	    Vector2 position = getPosition();
	    int x = (int) (position.getX() - LARGE_ORB_WIDTH / 2.0);
	    int y = (int) (position.getY() - LARGE_ORB_HEIGHT / 2.0);
	    g.drawImage(LARGE_ORB, x, y, null);
	}
    }

    private static class SmallOrb extends Projectile {
	public SmallOrb(Vector2 position, Vector2 velocity) {
	    super(position, new Vector2(SMALL_ORB_WIDTH, SMALL_ORB_HEIGHT), velocity);
	}

	@Override
	public void initialize() {
	    super.initialize();
	}

	@Override
	public void onCollisionEnter(Entity other) {
	    if (other instanceof PlayerShip) {
		((PlayerShip) other).damage(BASE_SMALL_ORB_DAMAGE * Main.difficulty.getModifier());
		Scene scene = Game.getInstance().getOpenScene();
		scene.removeObject(this);
		scene.addObject(new OrbSpark(getPosition().add(SMALL_ORB_WIDTH / 2.0, 0)));
	    } else if (other instanceof PlayerShip.Laser) {
		Scene scene = Game.getInstance().getOpenScene();
		scene.removeObject(this);
		scene.addObject(new OrbSpark(getPosition().add(SMALL_ORB_WIDTH / 2.0, 0)));
	    }
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	@Override
	public void render(Graphics g) {
	    Vector2 position = getPosition();
	    g.drawImage(SMALL_ORB, (int) (position.getX() - SMALL_ORB_WIDTH / 2.0),
		    (int) (position.getY() - SMALL_ORB_HEIGHT / 2.0), null);
	}

	@Override
	public void dispose() {
	    super.dispose();
	}
    }

    private static class OrbSpark extends SceneObject {
	private static final double DURATION = 0.1;
	private static final int LASER_SPARK_WIDTH = 50;
	private static final int LASER_SPARK_HEIGHT = 50;
	private static final Image LASER_SPARK = ResourceLoader
		.loadImage("res/images/entities/marauder/MarauderOrbSpark.png")
		.getScaledInstance(LASER_SPARK_WIDTH, LASER_SPARK_HEIGHT, 0);

	private Vector2 position;
	private double deathTime;

	private OrbSpark(Vector2 position) {
	    this.position = position;
	    deathTime = Game.getInstance().getTime() + DURATION;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void tick() {
	    if (Game.getInstance().getTime() > deathTime) {
		Game.getInstance().getOpenScene().removeObject(this);
	    }
	}

	@Override
	public void render(Graphics g) {
	    int x = (int) (position.getX() - LASER_SPARK_WIDTH / 2.0);
	    int y = (int) (position.getY() - LASER_SPARK_HEIGHT / 2.0);
	    g.drawImage(LASER_SPARK, x, y, null);
	}

	@Override
	public void dispose() {
	}
    }
}
