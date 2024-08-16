package mino;

import java.awt.*;

public class Mino {

    public Block blocks[] = new Block[4];
    public Block tempBlocks[] = new Block[4];

    public void create(Color color){
        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(color);
            tempBlocks[i] = new Block(color);
        }
    }
    public void setXY(int x, int y){}
    public void updateXY(int direction){}
    public void update(){}

    public void draw(Graphics2D g2){
        int margin = 2;

        g2.setColor(blocks[0].color);
        for (int i = 0; i < 4; i++) {
            g2.fillRect(blocks[i].x+margin, blocks[i].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        }
    }
}
