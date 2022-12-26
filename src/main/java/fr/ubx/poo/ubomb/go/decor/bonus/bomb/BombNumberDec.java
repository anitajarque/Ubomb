package fr.ubx.poo.ubomb.go.decor.bonus.bomb;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.Takeable;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class BombNumberDec extends Bomb {
    public BombNumberDec(Position position) {
        super(position);
    }

    @Override
    public void takenBy(Player player) {
        if(player.getNumBombs()> 0){
            super.takenBy(player);
        }
    }
}