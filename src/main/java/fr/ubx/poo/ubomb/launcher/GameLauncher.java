package fr.ubx.poo.ubomb.launcher;
import fr.ubx.poo.ubomb.game.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Properties;
import static javafx.beans.property.IntegerProperty.integerProperty;

public class GameLauncher {
    public static Game load() {
        boolean compresion;
        int x, y, bombBagCapacity, playerLives, playerInvisibulityTime, mosterVelocity, monsterInvisibilyTime, numLevels;
        String[] pos;
        Position position;
        ArrayList<MapLevel> levels = new ArrayList<>();
        String level;
        try {
            String file = "world/sample.properties";
            Properties config = new Properties();
            ;
            Reader in = new FileReader(file);
            config.load(in);
            System.out.println("File loaded");
            numLevels = Integer.valueOf(config.getProperty("level", "1"));
            compresion = Boolean.getBoolean(config.getProperty("compresion", "false"));
            bombBagCapacity = Integer.valueOf(config.getProperty("bomBagCapacity", "3"));
            playerLives = Integer.valueOf(config.getProperty("playerLives", "5"));
            playerInvisibulityTime = Integer.valueOf(config.getProperty("playerInvisibilityTime", "4000"));
            mosterVelocity = Integer.valueOf(config.getProperty("mosterVelocity", "5"));
            monsterInvisibilyTime = Integer.valueOf(config.getProperty("mosterInvisibilityTime", "1000"));
            pos = config.getProperty("player").split("x");
            position = new Position(Integer.valueOf(pos[0]), Integer.valueOf(pos[1]));
            for(int i=0; i<numLevels; i++){
                String stringLevel = config.getProperty("level" + Integer.toString(i+1));
                levels.add(new Level(new MapLevel(stringLevel)));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Configuration configuration = new Configuration(position, bombBagCapacity, playerLives, playerInvisibulityTime, mosterVelocity, monsterInvisibilyTime);
        //Configuration configuration = new Configuration(new Position(0, 0), 3, 5, 4000, 5, 1000);
        return new Game(configuration, levels);
    }
}