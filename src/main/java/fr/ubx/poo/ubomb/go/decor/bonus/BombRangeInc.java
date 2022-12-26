package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;

public class BombRangeInc extends Bonus implements Takeable {
    public BombRangeInc(Position position) {
        super(position);
    }
    @Override
    public void explode() {}

    @Override
    public void takenBy(Player player) {
        remove();
    }
}
