import java.util.ArrayList;

/**
 * @author Timmy Eklund, id15ted@cs.umu.se
 * @author Alex Norrman, id14ann@cs.umu.se
 *
 * Node Class
 * A perceptron neural network node.
 */
@SuppressWarnings("package")
public class Node {

    private Double activationValue;
    private Double learningRate;
    private Double error = 0.0;
    private ArrayList<Double> weights = new ArrayList<>();
    private int faceExpression;
    private static final int function_Type = 0;

    /**
     * Constructor Node()
     * Initializes the node by creating weights.
     *
     * @param learningRate The learning rate to be used.
     * @param faceExpression The facial expression the node is going to learn to recognize.
     */
    public Node(Double learningRate, int faceExpression)
    {
        this.learningRate = learningRate;
        this.faceExpression = faceExpression;

        for(int i=0; i<400; i++) {
            this.weights.add(Math.random());
        }
        // Add a bias weight
        this.weights.add(1.0);
    }

    /**
     * Method train()
     * Trains an image to recognize an image as a facial expression.
     *
     * @param image An image as an integer-matrix consisting of pixel-values.
     * @param desiredValue The facial expression the node should recognize the image as.
     */
    public void train(Integer[][] image, int desiredValue)
    {
        this.calcActivationValue(image);
        this.calcWeights(image, desiredValue);
    }

    /**
     * Method test()
     * Analyze an image using the node.
     *
     * @param image An image as an integer-matrix consisting of pixel-values.
     * @return Returns the activation-value, ie the nodes result from analyzing an image.
     *         The higher a value, the more sure the node is of it's result.
     */
    public Double test(Integer[][] image)
    {
        this.calcActivationValue(image);
        return this.activationValue;
    }

    /**
     * Method calcActivationValue()
     * Calculates the nodes resulting activation-value from analyzing an image
     *
     * @param image An image as an integer-matrix consisting of pixel-values.
     */
    private void calcActivationValue(Integer[][] image)
    {
        double sum = 0.0;
        int i = 0;
        for (int y = 0; y < 20; y++)
        {
            for (int x = 0; x < 20; x++)
            {
                int value = image[x][y];
                sum += (double)value * weights.get(i);
                i++;
            }
        }
        // Add bias-weight
        sum += weights.get(400);

        switch (function_Type){
            case(0):
                // Activation function (Sigmoid)
                this.activationValue = (1 / (1 + Math.exp(-sum)));
                break;
            case(1):
                // Activation function (Step)
                this.activationValue = Math.signum(sum);
                break;
            case(2):
                // Activation function (Hyperbolic)
                this.activationValue = Math.tanh(sum);
                break;
        }

    }

    /**
     * Method calcError()
     * Calculates error-value
     *
     * @param desiredValue The facial expression the node should recognize the image as.
     * @return Returns the resulting error-value as a double.
     */
    private double calcError(int desiredValue)
    {
        return desiredValue - activationValue;
    }

    /**
     * Method calcWeights()
     * Calculates and updates the weights, based on the image and expected facial expression.
     *
     * @param image An image as an integer-matrix consisting of pixel-values.
     * @param desiredValue The facial expression the node should recognize the image as.
     */
    private void calcWeights(Integer[][] image, int desiredValue)
    {
        this.error = calcError(desiredValue);
        int i = 0;
        for (int y = 0; y < 20; y++)
        {
            for (int x = 0; x < 20; x++)
            {
                int value = image[x][y];
                double deltaWeight = learningRate * error * (double)value;
                double weight = (weights.get(i) + deltaWeight);
                weights.set(i,weight);
                i++;
            }
        }
    }

    /**
     * Method getFaceExpression()
     * Returns faceExpression variable.
     *
     * @return faceExpression variable.
     */
    public int getFaceExpression()
    {
        return faceExpression;
    }

    /**
     * Method getError()
     * Returns error variable.
     *
     * @return error variable.
     */
    public Double getError() {
        return error;
    }
}
