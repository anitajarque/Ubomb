package fr.ubx.poo.ubomb.game;
import fr.ubx.poo.ubomb.go.decor.Princess;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.*;
import fr.ubx.poo.ubomb.launcher.Entity;
import fr.ubx.poo.ubomb.launcher.MapLevel;
import javafx.geometry.Pos;
import java.util.*;

public class Level implements Grid {
    private final int width;
    private final int height;
    private final MapLevel entities;
    private final Game game;
    private final Map<Position, Decor> elements = new HashMap<>();
    public Level(Game game,MapLevel entities) {
        this.entities = entities;
        this.width = entities.width();
        this.height = entities.height();
        this.game = game;
        System.out.println("Level");
        System.out.println(this.width);
        System.out.println(this.height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                Position position = new Position(i, j);
                Entity entity = entities.get(j, i);
//                System.out.println("get " + i + " " + j);
//                System.out.println(entity.toString());
                switch (entity) {
                    case Stone:
                        elements.put(position, new Stone(position));
                        break;
                    case Tree:
                        elements.put(position, new Tree(position));
                        break;
                    case Key:
                        elements.put(position, new Key(position));
                        break;
                    case Heart:
                        elements.put(position, new Heart(position));
                        break;
                    case Box:
                        elements.put(position, new Box(this.game, position));
                        break;
                    case Princess:
                        elements.put(position, new Princess(position));
                        break;
                    case BombRangeInc:
                        elements.put(position, new BombRangeInc(position));
                        break;
                    case BombRangeDec:
                        elements.put(position, new BombRangeDec(position));
                        break;
                    case BombNumberInc:
                        elements.put(position, new BombNumberInc(position));
                        break;
                    case BombNumberDec:
                        elements.put(position, new BombNumberDec(position));
                        break;
                    case DoorPrevOpened:
                        elements.put(position, new Door(this.game, position, -1, true));
                        break;
                    case DoorNextOpened:
                        elements.put(position, new Door(this.game, position, 1, true));
                        break;
                    case DoorNextClosed:
                        elements.put(position, new Door(this.game, position, 1, false));
                        break;
                    case Empty: break;
                    default:
                        System.out.println(entity.toString());
                        //                       throw new RuntimeException("EntityCode " + entity.name() + " not processed");
                }
            }
    }
    @Override
    public int width() {
        return this.width;
    }
    @Override
    public int height() {
        return this.height;
    }
    public Decor get(Position position) {
        return elements.get(position);
    }
    @Override
    public void remove(Position position) {
        elements.remove(position);
    }
    public Collection<Decor> values() {
        return elements.values();
    }

    @Override
    public boolean inside(Position position) {
        return true;
    }
    @Override
    public void set(Position position, Decor decor) {
        if (!inside(position))
            throw new IllegalArgumentException("Illegal Position");
        if (decor != null)
            elements.put(position, decor);
    }

    public Map<Position, Decor> getElements() {
        return this.elements;
    }
    public void  addDecor(Decor decor){
        elements.put(decor.getPosition(), decor);
    }
}