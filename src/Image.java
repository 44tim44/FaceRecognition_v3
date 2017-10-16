/**
 * @author Timmy Eklund, id15ted@cs.umu.se
 * @author Alex Norrman, id14ann@cs.umu.se
 */
public class Image
{
    private String name;
    private Integer[][] image;
    private int expression;

    public Image(String name, Integer[][] image)
    {
        this.name = name;
        this.image = image;
    }

    /**
     * @return Returns the name of the image
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Returns the image-matrix
     */
    public Integer[][] getImage()
    {
        return image;
    }

    /**
     * @return Returns an int that represents the correct expression of the image
     */
    public int getExpression() {
        return expression;
    }

    /**
     * @param expression Sets an in that represents the correct expression of the image
     */
    public void setExpression(int expression) {
        this.expression = expression;
    }
}
