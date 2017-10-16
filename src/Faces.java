import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Timmy Eklund, id15ted@cs.umu.se
 * @author Alex Norrman, id14ann@cs.umu.se
 *
 * Faces Class
 * Main-method class, responsible for running facial recognition software, utilizing perceptrons.
 */
public class Faces {

    public static void main(String[] args) throws FileNotFoundException
    {
        //ArrayList<Image> trainingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/training-A.txt");
        //ArrayList<Image> trainingImagesWithFacit = loadFacit("/Users/timmy/IdeaProjects/FaceRecognition/src/facit-A.txt",trainingImages);
        //ArrayList<Image> testingImages = loadImages("/Users/timmy/IdeaProjects/FaceRecognition/src/test-B.txt");

        ArrayList<Image> trainingImages = loadImages(args[0]);
        ArrayList<Image> trainingImagesWithFacit = loadFacit(args[1],trainingImages);
        ArrayList<Image> testingImages = loadImages(args[2]);


        Network network = new Network();

        //Adds 2/3 of the training-images with facit to a traningset.
        @SuppressWarnings("unchecked")
        ArrayList<Image> trainingSet = (ArrayList<Image>) trainingImagesWithFacit.clone();
        for(int i = trainingSet.size()*2/3; i<trainingSet.size();i++)
        {
            trainingSet.remove(i);
        }
        //Adds the remaining 1/3 to an evaluationset.
        @SuppressWarnings("unchecked")
        ArrayList<Image> evaluationSet = (ArrayList<Image>) trainingImagesWithFacit.clone();
        for(int i = 0; i<evaluationSet.size()*2/3;i++)
        {
            evaluationSet.remove(i);
        }

        //Train and examine.
        int k = 0;
        do{
            network.train(trainingSet);
            network.examine(evaluationSet,false);

            k++;
        }
        while(k<100);

        //Final test.
        network.examine(testingImages,true);

    }

    /**
     * loadImages
     * Loads the images from a text-file. The irrelevant lines are not loaded.
     * The images are placed into an ArrayList.
     * @param filePath The path to the images text-file
     * @return The ArrayList, containing all the images
     * @throws FileNotFoundException
     */
    private static ArrayList<Image> loadImages(String filePath) throws FileNotFoundException
    {
        String temp;
        Scanner inFile = new Scanner(new File(filePath));

        ArrayList<Image> imageList = new ArrayList<>();

        while(inFile.hasNextLine())
        {
            temp = inFile.nextLine();

            //Does not load the lines starting with #.
            if(!temp.startsWith("#"))
            {
                //If the line starts with Image, saves the name
                //and starts to load the pixels into the matrix.
                if(temp.startsWith("Image"))
                {
                    String imageName = temp;
                    Integer[][] imagePixels = new Integer[20][20];
                    for (int y = 0; y < 20; y++)
                    {
                        for (int x = 0; x < 20; x++)
                        {
                            temp = inFile.next();
                            //Weakens the pixels that are less then 6.
                            if(Integer.parseInt(temp) <= 5)
                            {
                                imagePixels[x][y] = 0;
                            }
                            //Reinforces the pixels that are above 28.
                            else if(Integer.parseInt(temp) >= 29)
                            {
                                imagePixels[x][y] = 32;
                            }
                            //Otherwise, adds the given pixel.
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

    /**
     * loadFacit
     * Loads the facit from a text-file. The irrelevant lines are not loaded.
     * Adds the facit to each image in the ArrayList.
     * @param filePath The path to the facit text-file
     * @param imageList The ArrayList of the images
     * @return The ArrayList of the images, now containing the facit for the images.
     * @throws FileNotFoundException
     */
    private static ArrayList<Image> loadFacit(String filePath, ArrayList<Image> imageList) throws FileNotFoundException
    {
        String temp;
        Scanner inFile = new Scanner(new File(filePath));

        while(inFile.hasNextLine())
        {
            temp = inFile.nextLine();

            //Does not load the lines starting with #.
            if(!temp.startsWith("#"))
            {
                //If the line starts with Image, saves line to a temporary array.
                //Splits the array into two parts. Part 1 = the name, part 2 = the expression.
                if(temp.startsWith("Image"))
                {
                    String tempArr[] = temp.split("\\s+");
                    String imageName = tempArr[0];
                    String value = tempArr[1];
                    for(Image image : imageList)
                    {
                        //Compares the name in the ArrayList to the loaded facit.
                        //Adds the expression.
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
