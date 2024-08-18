package mino;

import java.awt.*;

public class Mino_Square extends Mino{
    public Mino_Square() {
        create(Color.yellow);
    }

    @Override
    public void setXY(int x, int y){
        // # #
        // # #

        blocks[0].x = x; blocks[0].y = y;
        blocks[1].x = blocks[0].x + Block.SIZE; blocks[1].y = blocks[0].y;
        blocks[2].x = blocks[0].x; blocks[2].y = blocks[0].y + Block.SIZE;
        blocks[3].x = blocks[0].x + Block.SIZE; blocks[3].y = blocks[0].y + Block.SIZE;
    }
}