package mino;

import java.awt.*;

/**
 * Mino_Z2 representa a peça Z2 e suas possíveis rotações.<br>
 * A peça Z2 é representada da seguinte forma:<br> <br>
 *   #<br>
 * # #<br>
 * #
 */
public class Mino_Z2 extends Mino{
    public Mino_Z2() {
        create(Color.green);
    }

    @Override
    public void setXY(int x, int y){
        // #
        // # #
        //   #

        blocks[0].x = x; blocks[0].y = y;
        blocks[1].x = blocks[0].x; blocks[1].y = blocks[0].y + Block.SIZE;
        blocks[2].x = blocks[0].x - Block.SIZE; blocks[2].y = blocks[0].y;
        blocks[3].x = blocks[0].x - Block.SIZE; blocks[3].y = blocks[0].y - Block.SIZE;
    }

    @Override
    public void getDirection1() {
        // #
        // # #
        //   #

        tempBlocks[0].x = blocks[0].x;
        tempBlocks[0].y = blocks[0].y;
        tempBlocks[1].x = blocks[0].x;
        tempBlocks[1].y = blocks[0].y + Block.SIZE;
        tempBlocks[2].x = blocks[0].x - Block.SIZE;
        tempBlocks[2].y = blocks[0].y;
        tempBlocks[3].x = blocks[0].x - Block.SIZE;
        tempBlocks[3].y = blocks[0].y - Block.SIZE;

        updateXY(1);
    }

    @Override
    public void getDirection2() {
        //   # #
        // # #

        tempBlocks[0].x = blocks[0].x; tempBlocks[0].y = blocks[0].y;
        tempBlocks[1].x = blocks[0].x - Block.SIZE; tempBlocks[1].y = blocks[0].y;
        tempBlocks[2].x = blocks[0].x; tempBlocks[2].y = blocks[0].y - Block.SIZE;
        tempBlocks[3].x = blocks[0].x + Block.SIZE; tempBlocks[3].y = blocks[0].y - Block.SIZE;

        updateXY(2);
    }

    @Override
    public void getDirection3() {
        getDirection1();
    }

    @Override
    public void getDirection4() {
        getDirection2();
    }
}
