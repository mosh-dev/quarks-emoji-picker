package others;

import java.io.File;

/**
 * Created by MOSHIOUR RAHMAN on 3/30/2017.
 * UNDER QUARKS (An Initiative of TWIINED STUDIO)
 * www.twiined.com
 *
 *
 */
public class EmojiDir {

    public static String emoji_path;
    public static String[] emoji_pack_list;

    public EmojiDir(){
        emoji_path = "mojis/";
        emoji_pack_list = new File(emoji_path).list();
    }

}
