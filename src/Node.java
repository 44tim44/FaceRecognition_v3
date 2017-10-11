import java.util.ArrayList;

/**
 * Created by timmy on 2017-10-11.
 */
public class Node {

    private Double activationValue;
    private Double learningRate;
    private Double error = 0.0;
    private ArrayList<Double> weights = new ArrayList();
    private int faceExpression;

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

    public void calcActivationValue(Integer[][] image)
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

        // Activation function (Sigmoid)
            this.activationValue = (1 / (1 + Math.exp(-sum)));
        // Activation function (Step)
            //this.activationValue = Math.signum(sum);
        // Activation function (Hyperbolic)
            //this.activationValue = Math.tanh(sum);
    }

    public double calcError(int desiredValue)
    {
        return desiredValue - activationValue;
    }

    public void calcWeights(Integer[][] image, int desiredValue)
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
