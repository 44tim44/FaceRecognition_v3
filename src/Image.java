/**
 * @author Timmy Eklund, id15ted@cs.umu.se
 * @author Alex Norrman, id14ann@cs.umu.se
 *
 * Image Class
 * An image created as an matrix.
 */
public class Image
{
    private String name;
    private Integer[][] image;
    private int expression;

    /**
     * Constructor Image()
     * Initializes Image.
     *
     * @param name Name of the Image
     * @param image The Integer-matrix of pixel-values that make up the actual image.
     */
    public Image(String name, Integer[][] image)
    {
        this.name = name;
        this.image = image;
    }

    /**
     * Method getName()
     * Returns variable name.
     *
     * @return Returns the name of the image
     */
    public String getName()
    {
        return name;
    }

    /**
     * Method getImage()
     * Returns variable image.
     *
     * @return Returns the image-matrix
     */
    public Integer[][] getImage()
    {
        return image;
    }

    /**
     * Method getExpression()
     * Returns variable expression.
     *
     * @return Returns an int that represents the correct expression of the image
     */
    public int getExpression() {
        return expression;
    }

    /**
     * Method setExpression()
     * Sets the variable expression to a new facial expression.
     *
     * @param expression Sets an int that represents the correct expression of the image
     */
    public void setExpression(int expression) {
        this.expression = expression;
    }
}
