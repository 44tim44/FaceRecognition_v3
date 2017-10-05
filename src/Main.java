import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by timmy on 2017-10-05.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException{

        Scanner inFile1 = new Scanner(new File("/Users/timmy/IdeaProjects/FaceRecognition/src/training-A.txt")).useDelimiter("\\n");
        //Scanner inFile2 = new Scanner(new File(args[0])).useDelimiter("\\s");

        //List<Integer[][]> imageList = new ArrayList<>();
        HashMap<String, Integer[][]> imageHashMap = new HashMap();

        //SkipIntro
        String temp;
        boolean done = false;
        while(!done)
        {
            temp = inFile1.next();
            //System.out.println(temp);
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
                        //System.out.println(inFile1.hasNextInt());
                        //System.out.println(inFile1.next());
                        int value = inFile1.nextInt();
                        image[x][y] = value;
                        //System.out.println(value);
                    }
                }
                //imageList.add(image);
                imageHashMap.put(imageName, image);
            }
        }

        Integer[][] bildTest = imageHashMap.get("Image1");
        for (int y = 0; y < 20; y++)
        {
            for (int x = 0; x < 20; x++) {
                int value = bildTest[x][y];
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
