package others;

import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by MOSHIOUR RAHMAN(Tushar) on 3/30/2017.
 * UNDER QUARKS (An Initiative of TWIINED STUDIO)
 * www.twiined.com
 *
 *
 */
public class SingleEmojiPack {

    public int number_of_emoji;
    public String name;
    public ArrayList<Image> emojis_image;
    public ArrayList<String> emojis_code;


    public SingleEmojiPack(String pack_name){

        emojis_image = new ArrayList<>();
        emojis_code = new ArrayList<>();

        String pack_path = EmojiDir.emoji_path + pack_name + "/";

        String jsonData = readFile(pack_path + "meta_info.json");
        JSONObject jobj = new JSONObject(jsonData);
        JSONArray jarr = new JSONArray(jobj.getJSONArray("emojis").toString());

        // How much number of emoji are in the pack
        number_of_emoji = jarr.length();
        name = jobj.getString("pack_name");


        // Putting emojis image(imageview which will be used in emoji grid) and code to arraylist
        for(int i = 0; i < jarr.length(); i++) {
            JSONObject j = jarr.getJSONObject(i);

            String image_url = new File(pack_path).toURI().toString() + j.getString("filename");

            emojis_image.add(new Image(image_url));
            emojis_code.add(j.getString("code"));

        }

    }



    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
