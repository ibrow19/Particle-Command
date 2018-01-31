package scene;

import gameobject.TextureObject;

/// Game context holding objects that make up the scene.
public class Context {

    public int wave;
    public int score;
    public ForceManager fManager;
    public TextureObject crosshair;
    public ParticleManager pManager;
    public CityManager cManager;
    public MissileManager mManager;

}
