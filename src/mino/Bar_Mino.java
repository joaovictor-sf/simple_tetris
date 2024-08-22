package mino;

import java.awt.*;

/**
 * Classe que cria a peça de formato de barra. <br> <br>
 * # # # #
 */
public class Bar_Mino extends Mino{
    public Bar_Mino() {
        create(Color.cyan);
    }

    @Override
    public void setXY(int x, int y){
        // # # # #

        blocks[0].x = x; blocks[0].y = y;
        blocks[1].x = blocks[0].x - Block.SIZE; blocks[1].y = blocks[0].y;
        blocks[2].x = blocks[0].x + Block.SIZE; blocks[2].y = blocks[0].y;
        blocks[3].x = blocks[0].x + Block.SIZE * 2; blocks[3].y = blocks[0].y;
    }

    @Override
    public void getDirection1() {
        // # # # #

        tempBlocks[0].x = blocks[0].x; tempBlocks[0].y = blocks[0].y;
        tempBlocks[1].x = blocks[0].x - Block.SIZE; tempBlocks[1].y = blocks[0].y;
        tempBlocks[2].x = blocks[0].x + Block.SIZE; tempBlocks[2].y = blocks[0].y;
        tempBlocks[3].x = blocks[0].x + Block.SIZE * 2; tempBlocks[3].y = blocks[0].y;

        updateXY(1);
    }

    @Override
    public void getDirection2() {
        // #
        // #
        // #
        // #

        tempBlocks[0].x = blocks[0].x; tempBlocks[0].y = blocks[0].y;
        tempBlocks[1].x = blocks[0].x; tempBlocks[1].y = blocks[0].y - Block.SIZE;
        tempBlocks[2].x = blocks[0].x; tempBlocks[2].y = blocks[0].y + Block.SIZE;
        tempBlocks[3].x = blocks[0].x; tempBlocks[3].y = blocks[0].y + Block.SIZE * 2;

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