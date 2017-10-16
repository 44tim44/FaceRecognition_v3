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

    public void train(Integer[][] image, int desiredValue)
    {
        this.calcActivationValue(image);
        this.calcWeights(image, desiredValue);
    }

    public Double test(Integer[][] image)
    {
        this.calcActivationValue(image);
        return this.activationValue;
    }

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

    private double calcError(int desiredValue)
    {
        return desiredValue - activationValue;
    }

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

    public int getFaceExpression()
    {
        return faceExpression;
    }

    public Double getError() {
        return error;
    }
}
