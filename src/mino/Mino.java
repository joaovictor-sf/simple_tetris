package mino;

import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

public class Mino {

    public Block blocks[] = new Block[4];
    public Block tempBlocks[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // Cada mino tem 4 direções

    public void create(Color color){
        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(color);
            tempBlocks[i] = new Block(color);
        }
    }
    public void setXY(int x, int y){}
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    public void updateXY(int direction){
        this.direction = direction;
        for (int i = 0; i < 4; i++) {
            blocks[i].x = tempBlocks[i].x;
            blocks[i].y = tempBlocks[i].y;
        }
    }

    public void update(){
        // Move mino
        if (KeyHandler.upPressed){
            System.out.println("Up pressed");
            switch (direction){
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }
            KeyHandler.upPressed = false;
        }
        if (KeyHandler.downPressed){
            System.out.println("Down pressed");
            blocks[0].y += Block.SIZE;
            blocks[1].y += Block.SIZE;
            blocks[2].y += Block.SIZE;
            blocks[3].y += Block.SIZE;

            // Reseta o contador de autoDrop
            autoDropCounter = 0;

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed){
            System.out.println("Left pressed");
            blocks[0].x -= Block.SIZE;
            blocks[1].x -= Block.SIZE;
            blocks[2].x -= Block.SIZE;
            blocks[3].x -= Block.SIZE;

            KeyHandler.leftPressed = false;
        }
        if (KeyHandler.rightPressed){
            System.out.println("Right pressed");
            blocks[0].x += Block.SIZE;
            blocks[1].x += Block.SIZE;
            blocks[2].x += Block.SIZE;
            blocks[3].x += Block.SIZE;

            KeyHandler.rightPressed = false;
        }

        // Auto drop
        autoDropCounter++;
        if (autoDropCounter > PlayManager.dropInterval) {
            // Move o mino para baixo
            blocks[0].y += Block.SIZE;
            blocks[1].y += Block.SIZE;
            blocks[2].y += Block.SIZE;
            blocks[3].y += Block.SIZE;
            autoDropCounter = 0;
        }
    }

    public void draw(Graphics2D g2){
        int margin = 2;

        g2.setColor(blocks[0].color);
        for (int i = 0; i < 4; i++) {
            g2.fillRect(blocks[i].x+margin, blocks[i].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        }
    }
}
