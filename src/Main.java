import java.io.File;
import java.util.*;

/**
 * Created by timmy on 2017-10-05.
 */
public class Main {

    public static void main(String[] args) {

        Scanner inFile1 = new Scanner(new File("KeyWestTemp.txt")).useDelimiter("\\s");
        //Scanner inFile2 = new Scanner(new File(args[0])).useDelimiter("\\s");

        //List<Integer[][]> imageList = new ArrayList<>();
        HashMap<String, Integer[][]> imageHashMap = new HashMap();

        while (inFile1.hasNext()) {
            String imageName = inFile1.next();
            Integer[][] image = new Integer[20][20];
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    int value = inFile1.nextInt();
                    image[x][y] = value;
                }
            }
            //imageList.add(image);
            imageHashMap.put(imageName,image);
        }
    }
}
