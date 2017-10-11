import java.util.*;

/**
 * Created by timmy on 2017-10-11.
 */
public class Network
{
    public static final int HAPPY = 1;
    public static final int SAD = 2;
    public static final int MISCHIEVOUS = 3;
    public static final int MAD = 4;

    private ArrayList<Node> nodes = new ArrayList<>();
    private final Double learningRate = 0.010;

    private double summedError = 0;
    private double meanError = 0;
    private int counter = 0;

    public Network()
    {
        nodes.add(new Node(learningRate, HAPPY));
        nodes.add(new Node(learningRate, SAD));
        nodes.add(new Node(learningRate, MISCHIEVOUS));
        nodes.add(new Node(learningRate, MAD));
    }

    public void train(HashMap<String, Integer[][]> images, HashMap<String, Integer> facit) {
        do {
            int expectedExpression;

            List<String> list = new ArrayList<>(images.keySet());
            Collections.shuffle(list);

            Map<String, Integer[][]> shuffleMap = new LinkedHashMap<>();
            list.forEach(k->shuffleMap.put(k, images.get(k)));

            for(Map.Entry<String, Integer[][]> entry : shuffleMap.entrySet()) {
                String key = entry.getKey();
                Integer[][] image = entry.getValue();

                for (Node node : nodes) {
                    counter++;
                    if(node.getFaceExpression() == facit.get(key)){
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

    public double examine(HashMap<String, Integer[][]> images, HashMap<String, Integer>  facit, boolean print) {

        int amountCorrect = 0;
        int amount = 0;
        for(Map.Entry<String, Integer[][]> entry : images.entrySet()) {
            String key = entry.getKey();
            Integer[][] image = entry.getValue();

            HashMap<Double, Integer> values = new HashMap<>();

            for (Node node : nodes) {
                values.put(node.test(image), node.getFaceExpression());
            }

            double maxKey = Collections.max(values.keySet());
            int expression = values.get(maxKey);
            if(expression == facit.get(key)){
                amountCorrect++;
            }
            amount++;
            String expressionString = "undefined";
            switch (expression){
                case(HAPPY):
                    expressionString = "Happy";
                    break;
                case (SAD):
                    expressionString = "Sad";
                    break;
                case(MISCHIEVOUS):
                    expressionString = "Mischievous";
                    break;
                case(MAD):
                    expressionString = "Mad";
                    break;
            }
            //System.out.println(key + " " + expressionString);
            if(print) System.out.println(key + " " + expression);
        }
        //System.out.println();
        //System.out.println("Amount correct = " + ((double)amountCorrect / (double)amount)*100 + "%");
        return ((double)amountCorrect / (double)amount);
    }

}
