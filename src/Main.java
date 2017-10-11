import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by timmy on 2017-10-05.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException
    {
        HashMap<String, Integer[][]> trainingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/training-A.txt");
        HashMap<String, Integer> trainingFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-A.txt");
        HashMap<String, Integer[][]> testingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/test-B.txt");
        HashMap<String, Integer> testingFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-B.txt");



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
