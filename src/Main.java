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
            ArrayList<Image> trainingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/training-A.txt");
            ArrayList<Image> trainingImagesWithFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-A.txt",trainingImages);
            ArrayList<Image> testingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/test-B.txt");
            ArrayList<Image> testingImagesWithFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-B.txt",testingImages);

            Network network = new Network();

            ArrayList<Image> trainingSet = (ArrayList<Image>) trainingImagesWithFacit.clone();
            for(int i = trainingSet.size()*2/3; i<trainingSet.size();i++)
            {
                trainingSet.remove(i);
            }

            ArrayList<Image> evaluationSet = (ArrayList<Image>) trainingImagesWithFacit.clone();
            for(int i = 0; i<evaluationSet.size()*2/3;i++)
            {
                evaluationSet.remove(i);
            }

            double minorTotalPercent = 0;
            double percent = 0;
            int k = 0;
            do{
                network.train(trainingSet);
                percent = network.examine(evaluationSet,false);
                minorTotalPercent += percent;
                k++;
            }
            while(k<100);
            //System.out.println("Average amount correct = " + minorTotalPercent/k*100 + "%");

        double amountCorrect = network.examine(testingImagesWithFacit,true);
        totalPercent += amountCorrect;
        //System.out.println("Amount correct final exam = " + amountCorrect*100 + "%");
        //}
        //System.out.println("Average amount correct 100 final exams = " + totalPercent + "%");

        //Integer[][] image1 = trainingImages.get("Image1");
        //printImage(image1);

    }

    public static ArrayList<Image> loadImages(String filePath) throws FileNotFoundException
    {
        String temp;
        Scanner inFile = new Scanner(new File(filePath));

        ArrayList<Image> imageList = new ArrayList<>();

        while(inFile.hasNextLine())
        {
            temp = inFile.nextLine();

            if(!temp.startsWith("#"))
            {
                if(temp.startsWith("Image"))
                {
                    String imageName = temp;
                    Integer[][] imagePixels = new Integer[20][20];
                    for (int y = 0; y < 20; y++)
                    {
                        for (int x = 0; x < 20; x++)
                        {
                            temp = inFile.next();
                            if(Integer.parseInt(temp) <= 5)
                            {
                                imagePixels[x][y] = 0;
                            }
                            else if(Integer.parseInt(temp) >= 29)
                            {
                                imagePixels[x][y] = 32;
                            }
                            else
                            {
                                imagePixels[x][y] = Integer.parseInt(temp);
                            }
                        }
                    }
                    Image image = new Image(imageName,imagePixels);
                    imageList.add(image);
                }
            }
        }
        inFile.close();

        return imageList;
    }

    public static ArrayList<Image> loadFacit(String filePath, ArrayList<Image> imageList) throws FileNotFoundException
    {
        String temp;
        Scanner inFile = new Scanner(new File(filePath));

        while(inFile.hasNextLine())
        {
            temp = inFile.nextLine();

            if(!temp.startsWith("#"))
            {
                if(temp.startsWith("Image"))
                {
                    String tempArr[] = temp.split("\\s+");
                    String imageName = tempArr[0];
                    String value = tempArr[1];
                    for(Image image : imageList)
                    {
                        if(image.getName().equals(imageName))
                        {
                            System.out.println("Facit exp: " + value);
                            image.setExpression(Integer.parseInt(value));
                        }
                    }
                }
            }
        }
        inFile.close();

        return imageList;
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
