package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Movable;

public class Box extends Decor implements Movable {
    public Box(Game game, Position position) {
        super(game, position);
    }

    public Box(Position position) {
        super(position);
    }

    @Override
    public boolean canMove(Direction direction) {
        return true;
    }

    @Override
    public void doMove(Direction direction) { //como hacer que box no se ponga a null (game=null)
        Position nextPos = direction.nextPosition(getPosition());
        game.grid().remove(getPosition());
        game.grid().set(nextPos, this);
        setPosition(nextPos);
    }
}
