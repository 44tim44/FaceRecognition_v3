import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Main-method class, responsible for running facial recognition software, utilizing perceptrons.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException
    {
        ArrayList<Image> trainingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/training-A.txt");
        ArrayList<Image> trainingImagesWithFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-A.txt",trainingImages);
        ArrayList<Image> testingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/test-B.txt");
        ArrayList<Image> testingImagesWithFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-B.txt",testingImages);

        Network network = new Network();

        @SuppressWarnings("unchecked")
        ArrayList<Image> trainingSet = (ArrayList<Image>) trainingImagesWithFacit.clone();
        for(int i = trainingSet.size()*2/3; i<trainingSet.size();i++)
        {
            trainingSet.remove(i);
        }

        @SuppressWarnings("unchecked")
        ArrayList<Image> evaluationSet = (ArrayList<Image>) trainingImagesWithFacit.clone();
        for(int i = 0; i<evaluationSet.size()*2/3;i++)
        {
            evaluationSet.remove(i);
        }

        int k = 0;
        do{
            network.train(trainingSet);
            network.examine(evaluationSet,false);

            k++;
        }
        while(k<100);

        network.examine(testingImagesWithFacit,true);

    }

    private static ArrayList<Image> loadImages(String filePath) throws FileNotFoundException
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

    private static ArrayList<Image> loadFacit(String filePath, ArrayList<Image> imageList) throws FileNotFoundException
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
}
