package mino;

import java.awt.*;

public class Mino_L1 extends Mino{
    public Mino_L1() {
        create(Color.orange);
    }

    @Override
    public void setXY(int x, int y){
        //  #
        //  # <- esse é o block[0]
        //  # #
        blocks[0].x = x;   blocks[0].y = y;
        blocks[1].x = blocks[0].x;   blocks[1].y = blocks[0].y - Block.SIZE;
        blocks[2].x = blocks[0].x;   blocks[2].y = blocks[0].y + Block.SIZE;
        blocks[3].x = blocks[0].x + Block.SIZE;   blocks[3].y = blocks[0].y + Block.SIZE;
    }

    @Override
    public void getDirection1() {
        //     #
        // # # #
    }

    @Override
    public void getDirection2() {
        super.getDirection2();
    }

    @Override
    public void getDirection3() {

    }

    @Override
    public void getDirection4() {

    }
}
