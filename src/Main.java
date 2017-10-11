import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by timmy on 2017-10-05.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException
    {
        double totalPercent = 0;
        //for(int i = 0; i<100; i++) {
            HashMap<String, Integer[][]> trainingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/training-A.txt");
            HashMap<String, Integer> trainingFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-A.txt");
            HashMap<String, Integer[][]> testingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/test-B.txt");
            HashMap<String, Integer> testingFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-B.txt");

            Network network = new Network();


            List<String> list = new ArrayList<>(trainingImages.keySet());
            int size = list.size();

            Map<String, Integer[][]> linkedMap = new LinkedHashMap<>();
            list.forEach(k->linkedMap.put(k, trainingImages.get(k)));

            HashMap<String, Integer[][]> trainingSet = new HashMap<>();
            HashMap<String, Integer[][]> evaluationSet = new HashMap<>();
            int l = 0;
            for(Map.Entry<String, Integer[][]> entry : linkedMap.entrySet()) {
                String key = entry.getKey();
                Integer[][] image = entry.getValue();

                if(l < size*2/3)
                {
                    trainingSet.put(key, image);
                }
                else
                {
                    evaluationSet.put(key, image);
                }

                l++;
            }

            double minorTotalPercent = 0;
            double percent = 0;
            for(int k = 0; k<400; k++) {
                network.train(trainingSet, trainingFacit);
                percent = network.examine(evaluationSet,trainingFacit);
                minorTotalPercent += percent;
            }
            System.out.println("Average amount correct = " + minorTotalPercent/4 + "%");

            totalPercent += network.examine(testingImages, testingFacit);
        //}
        //System.out.println("Average amount correct = " + totalPercent + "%");

        //Integer[][] image1 = trainingImages.get("Image1");
        //printImage(image1);

    }

    public static HashMap<String,Integer[][]> loadImages(String filePath) throws FileNotFoundException
    {
        //Scanner inFile1 = new Scanner(new File()).useDelimiter("\\n");
        Scanner inFile1 = new Scanner(new File(filePath)).useDelimiter("\\n");

        HashMap<String, Integer[][]> imageHashMap = new HashMap();

        //SkipIntro
        String temp;
        boolean done = false;
        while(!done)
        {
            temp = inFile1.next();
            if(!temp.startsWith("#"))
            {
                done = true;
            }
        }

        inFile1.useDelimiter("\\s");

        while (inFile1.hasNext())
        {

            String imageName = inFile1.next();
            if (!imageName.isEmpty())
            {
                //System.out.println(imageName);
                Integer[][] image = new Integer[20][20];
                for (int y = 0; y < 20; y++)
                {
                    for (int x = 0; x < 20; x++)
                    {
                        int value = inFile1.nextInt();
                        image[x][y] = value;
                    }
                }
                imageHashMap.put(imageName, image);
            }
        }

        return imageHashMap;
    }

    public static HashMap<String,Integer> loadFacit(String filePath) throws FileNotFoundException
    {
        //Scanner inFile1 = new Scanner(new File()).useDelimiter("\\n");
        Scanner inFile = new Scanner(new File(filePath)).useDelimiter("\\n");

        HashMap<String, Integer> facitHashMap = new HashMap();

        String temp;
        boolean done = false;
        while(!done)
        {
            temp = inFile.next();
            if(!temp.startsWith("#"))
            {
                done = true;
            }
        }

        inFile.useDelimiter("\\s");

        while (inFile.hasNext())
        {
            String imageName = inFile.next();
            if (!imageName.isEmpty())
            {
                int value = inFile.nextInt();
                facitHashMap.put(imageName, value);
            }
        }
        return facitHashMap;
    }


    public static void printImage(Integer[][] image)
    {
        for (int y = 0; y < 20; y++)
        {
            for (int x = 0; x < 20; x++) {
                int value = image[x][y];
                System.out.print(value + " ");
                if(value < 10)
                {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }
}
