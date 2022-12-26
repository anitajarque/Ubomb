package fr.ubx.poo.ubomb.go.decor.bonus.bombRange;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class BombRange extends Bonus implements Takeable {
    public BombRange(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        player.take(this);
        remove();
    }

}
