package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;
import java.util.HashMap;
import java.util.Map;
public class Box extends Decor implements Movable {
    public Box(Game game, Position position) {
        super(game, position);
    }
    @Override
    public boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        if(nextPos.x()<0 || nextPos.y()<0 || game.grid().height()<=nextPos.y() || game.grid().width()<=nextPos.x()){
            return false;
        }
        GameObject next = game.grid().get(direction.nextPosition(getPosition()));
        return next == null;
    }
    @Override
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        game.getLevel().remove(getPosition());
        this.setPosition(nextPos);
        game.getLevel().set(nextPos,this);
    }
}