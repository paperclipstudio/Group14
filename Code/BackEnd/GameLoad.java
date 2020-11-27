package BackEnd;

import java.io.File;

public class GameLoad {

    public static void loader (String fileName) {
        File gameLoadFile = new File("SaveData\\GameSave\\" + fileName);
    }

}
