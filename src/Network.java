import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Timmy Eklund, id15ted@cs.umu.se
 * @author Alex Norrman, id14ann@cs.umu.se
 *
 * Network Class
 * Responsible for handling the neural network consisting of four nodes.
 * Manages training of the nodes, and utilizing the nodes to analyze images.
 */
public class Network
{
    public static final int HAPPY = 1;
    public static final int SAD = 2;
    public static final int MISCHIEVOUS = 3;
    public static final int MAD = 4;

    private ArrayList<Node> nodes = new ArrayList<>();
    private static final Double learningRate = 0.010;

    private double summedError = 0;
    private int counter = 0;

    /**
     * Constructor Network()
     * Initializes the four types of nodes.
     */
    public Network()
    {
        nodes.add(new Node(learningRate, HAPPY));
        nodes.add(new Node(learningRate, SAD));
        nodes.add(new Node(learningRate, MISCHIEVOUS));
        nodes.add(new Node(learningRate, MAD));
    }


    /**
     * Method train()
     * Trains the nodes using the images supplied in imageList.
     * @param imageList ArrayList of images to be used for training.
     */
    public void train(ArrayList<Image> imageList) {
        double meanError;
        do {
            int expectedExpression;
            Collections.shuffle(imageList);
            for(Image imageData : imageList)
            {
                Integer[][] image = imageData.getImage();

                for (Node node : nodes)
                {
                    counter++;
                    if(node.getFaceExpression() == imageData.getExpression())
                    {
                        expectedExpression = 1;
                    }
                    else
                    {
                        expectedExpression = 0;
                    }
                    node.train(image, expectedExpression);
                    summedError += Math.pow(node.getError(), 2);
                }
            }
            meanError = summedError / counter;
        }
        while (meanError > 0.01);
    }

    /**
     * Method examine().
     * Tests images in imageList, using the trained nodes, and returns an output for each image.
     * @param imageList ArrayList containing the images supplied
     * @param print Boolean for whether the results should be printed in Standard.out.
     */
    public void examine(ArrayList<Image> imageList, boolean print) throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintWriter writer = new PrintWriter("results.txt", "UTF-8");

        for(Image imageData : imageList)
        {
            Integer[][] image = imageData.getImage();
            HashMap<Double, Integer> values = new HashMap<>();

            for (Node node : nodes)
            {
                values.put(node.test(image), node.getFaceExpression());
            }

            double maxKey = Collections.max(values.keySet());
            int expression = values.get(maxKey);

            if(print)
            {
                System.out.println(imageData.getName() + " " + expression);
                writer.println(imageData.getName() + " " + expression);
            }
        }
        writer.close();
    }
}
