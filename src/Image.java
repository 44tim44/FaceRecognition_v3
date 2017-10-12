/**
 * Created by timmy on 2017-10-12.
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

    public String getName()
    {
        return name;
    }

    public Integer[][] getImage()
    {
        return image;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }
}
