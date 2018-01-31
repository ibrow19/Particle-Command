package rect;

/// A rectangle.
public class Rect {

    /// x coordinate of rectangle's top left corner.
    public float x;    

    /// y coordinate of rectangle's top left corner.
    public float y;    


    /// Width of the rectangle.
    public float width;    

    /// Height of the rectangle.
    public float height;    

    /// Initialise properties of rectangle.
    public Rect(float x, float y, float width, float height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    /// Copy the rectangle.
    /// \return a copy of the rectangle.
    public Rect copy() {

        return new Rect(x, y, width, height);
    
    }

}
