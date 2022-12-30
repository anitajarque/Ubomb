package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.Walkable;
import fr.ubx.poo.ubomb.go.decor.Decor;

public class Monster extends Decor{

    private Direction direction;
    private boolean moveRequested = false;

    public Monster(Game game, Position position) {
        super(game, position);
        this.direction = Direction.DOWN;
    }

    public Monster(Position position) {
        super(position);
        this.direction = Direction.DOWN;
    }

    public Direction getDirection() {
        return Direction.UP;
    }

}