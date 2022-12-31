package fr.ubx.poo.ubomb.go.decor.bonus.bombNumber;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;

public class BombNumberDec extends BombNumber {
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