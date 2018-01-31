package scene;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.function.Supplier;
import state.SceneState;
import state.StartState;
import texture.TextureManager;
import texture.Texture;
import gameobject.Missile;
import gameobject.BlackHole;
import gameobject.Turret;
import gameobject.TextureObject;
import gameobject.Particle;
import force.BasicGravity;
import force.Drag;
import random.Randomiser;
import rect.Rect;

/// Game scene. Manages and renders the game world.
public class Scene {

    /// Container for objects that make up the scene.
    private final Context mContext;

    /// Current state for delegation of input handling, updates and rendering.
    private SceneState mState;

    /// Initialise scene.
    /// \param tManager central storage of textures that can be used in the game.
    public Scene(TextureManager tManager) {

        mContext = new Context();
        mContext.wave = 1;
        mContext.score = 0;

        // Initialise managers.
        initFManager();
        initCrosshair(tManager);
        initCManager(tManager);
        initPManager(tManager);
        initMManager(tManager);
        
        mState = new StartState(mContext);

    }

    /// Update the scene.
    /// \param delta time since the last update.
    public void update(float delta) {

        // Delegate updating to current state.
        mState = mState.update(delta);

    }

    /// Render the scene.
    /// \param core Processing core to use for rendering.
    public void render(PApplet core) {

        // Delegate rendering to current state.
        mState.render(core);

    }

    /// Handle left mouse button clicked.
    /// \param position coordinates of mouse click.
    public void handleLeftClick(PVector position) {

        // Delegate left click handling to current state.
        mState = mState.handleLeftClick(position);

    }

    /// Handle right mouse button clicked.
    /// \param position coordinates of mouse click.
    public void handleRightClick(PVector position) {

        // Delegate right click handling to current state.
        mState = mState.handleRightClick(position);

    }

    /// Handle space button pressed.
    public void handleSpace() {

        // Delegate space pressed handling to current state.
        mState = mState.handleSpace();

    }

    /// Initialise force manager with initial forces.
    private void initFManager() {

        mContext.fManager = new ForceManager();

        // Gravity and drag constants.
        float gravityAcceleration = 3f;
        float k1 = 0.001f;
        float k2 = 0.0006f;

        // Initalise with global drag and gravity forces that are
        // always present in game.
        mContext.fManager.addForce(new BasicGravity(gravityAcceleration));
        mContext.fManager.addForce(new Drag(k1, k2));

    }

    /// Initialise cross hair.
    /// \param tManager source of textures to use.
    private void initCrosshair(TextureManager tManager) {

        Texture texture = tManager.getTexture("crosshair");
        mContext.crosshair = new TextureObject(texture);

    }

    /// Initialise city manager.
    /// \param tManager source of textures to use.
    public void initCManager(TextureManager tManager) {

        // Texture to use for cities and time for city destruction animation to last.
        Texture texture = tManager.getTexture("city");
        float destructionDuration = 0.5f;

        mContext.cManager = new CityManager(texture, destructionDuration);
    
    }

    /// Initialise particle manager.
    /// \param tManager source of textures to use.
    private void initPManager(TextureManager tManager) {

        // Texture to use for particles.
        Texture texture = tManager.getTexture("particle");

        // Particle count and spawn rate.
        int baseCount = 15;
        float baseInterval = 1f;
        float countMultiplier = 1.2f;
        float intervalMultiplier = 0.9f;

        // Particle explode animation duration.
        float explodeDuration = 0.3f;

        // Speed range for particle.
        float minSpeed = 200f;
        float maxSpeed = 300f;

        // Mass range and associated scaling.
        float baseMass = 10f;
        float extraMass = 10f;
        float baseScale = 0.70f;
        float extraScale = 0.40f;

        // Bounds particles must stay within.
        Rect bounds = new Rect(0f, -300f, 1000f, 1075f);

        // Spawn area for particles.
        Rect spawn = new Rect(150f, -120f, 700f, 10f);

        // Target area to fire particles towards.
        Rect target = new Rect(0f, 600f, 1000f, 350f);

        // When generating particles, use random initial velocity pointing from
        // random point in spawn area to random point in target area.
        Supplier<Particle> particleFactory = 
            () -> {
                float size = Randomiser.randomFloat(0f, 1f);
                float mass = baseMass + (size * extraMass);
                float scale = baseScale + (size * extraScale);
                float speed = Randomiser.randomFloat(minSpeed, maxSpeed);
                PVector start = Randomiser.randomPoint(spawn);
                PVector end = Randomiser.randomPoint(target);
                PVector velocity = end.sub(start);
                velocity.setMag(speed);
                Particle particle = new Particle(texture, mass, velocity, explodeDuration);
                particle.translate(start);
                particle.scale(scale, scale);
                return particle;
            };

        // Initialise manager with particle factory and spawning properties..
        mContext.pManager = new ParticleManager(particleFactory,
                                                baseCount,
                                                baseInterval,
                                                countMultiplier,
                                                intervalMultiplier,
                                                bounds);

    }

    /// Initialise particle manager.
    /// \param tManager source of textures to use.
    private void initMManager(TextureManager tManager) {

        // Get textures for turret, missiles and black holes.
        Texture turretTexture = tManager.getTexture("turret");
        Texture missileTexture = tManager.getTexture("missile");
        Texture holeTexture = tManager.getTexture("hole");

        // Initial missile count.
        int baseMissiles = 15;

        // projectile speed towards target.
        float flightSpeed = 1000f;

        // Time it take a turret to fire a missile.
        float fireDuration = 0.5f;

        // Time it takes missile to explode.
        float explodeDuration = 0.5f;

        // Force applied by a missile upon explosion.
        float explodeForce = 1500f;

        // Black hole explosion duration.
        float holeDuration = 1f;

        // Degrees black hole rotates every second.
        float holeRotateRate = 120f;

        // Mass and gravitational constant to use for black hole's gravity.
        float holeMass = 50000f;
        float holeGravity = 100f;

        // Point to fire missiles from.
        PVector spawn = new PVector(500f, 900f);
            
        // Factory for turrets.
        Supplier<Turret> turretFactory = () -> new Turret(turretTexture,
                                                          fireDuration);

        // Factory for missiles.
        Supplier<Missile> missileFactory = () -> new Missile(missileTexture,
                                                             mContext.fManager,
                                                             flightSpeed, 
                                                             explodeDuration, 
                                                             explodeForce);

        // Factory for black holes.
        Supplier<BlackHole> holeFactory = () -> new BlackHole(holeTexture,
                                                              mContext.fManager,
                                                              flightSpeed, 
                                                              holeDuration, 
                                                              holeRotateRate,
                                                              holeMass,
                                                              holeGravity);

        // Initise manager with factories and missile spawning properties.
        mContext.mManager = new MissileManager(turretFactory,
                                               missileFactory,
                                               holeFactory,
                                               baseMissiles,
                                               spawn);

    }

}
