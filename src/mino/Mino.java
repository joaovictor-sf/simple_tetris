package mino;

import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

public class Mino {

    public Block blocks[] = new Block[4];
    public Block tempBlocks[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // Cada mino tem 4 direções
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;


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

    public void checkMovementCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        // Checa colisão com a parede esquerda
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].x == PlayManager.left_x) {
                leftCollision = true;
            }
        }

        // Checa colisão com a parede direita
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
            }
        }

        // Checa colisão com o chão
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }
    public void checkRotationCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        // Checa colisão com a parede esquerda
        for (int i = 0; i < blocks.length; i++) {
            if (tempBlocks[i].x < PlayManager.left_x) {
                leftCollision = true;
            }
        }

        // Checa colisão com a parede direita
        for (int i = 0; i < blocks.length; i++) {
            if (tempBlocks[i].x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
            }
        }

        // Checa colisão com o chão
        for (int i = 0; i < blocks.length; i++) {
            if (tempBlocks[i].y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void updateXY(int direction){
        checkRotationCollision();

        if (!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            for (int i = 0; i < 4; i++) {
                blocks[i].x = tempBlocks[i].x;
                blocks[i].y = tempBlocks[i].y;
            }
        }
    }

    private void checkStaticBlockCollision(){
        for (int i = 0; i < PlayManager.staticBloks.size(); i++) {
            int targetX = PlayManager.staticBloks.get(i).x;
            int targetY = PlayManager.staticBloks.get(i).y;

            //checando em baixo
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j].x == targetX && blocks[j].y + Block.SIZE == targetY) {
                    bottomCollision = true;
                }
            }

            //checando na esquerda
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j].x - Block.SIZE == targetX && blocks[j].y == targetY) {
                    leftCollision = true;
                }
            }

            //checando na direita
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j].x + Block.SIZE == targetX && blocks[j].y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    public void update(){

        if (deactivating) deactivatingDelay();

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

        checkMovementCollision();

        if (KeyHandler.downPressed){

            // Checa colisão com o chão
            if (!bottomCollision) {
                blocks[0].y += Block.SIZE;
                blocks[1].y += Block.SIZE;
                blocks[2].y += Block.SIZE;
                blocks[3].y += Block.SIZE;

                // Reseta o contador de autoDrop
                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed){

            // Checa colisão com a parede esquerda
            if (!leftCollision) {
                blocks[0].x -= Block.SIZE;
                blocks[1].x -= Block.SIZE;
                blocks[2].x -= Block.SIZE;
                blocks[3].x -= Block.SIZE;
            }

            KeyHandler.leftPressed = false;
        }
        if (KeyHandler.rightPressed){

            // Checa colisão com a parede direita
            if (!rightCollision) {
                blocks[0].x += Block.SIZE;
                blocks[1].x += Block.SIZE;
                blocks[2].x += Block.SIZE;
                blocks[3].x += Block.SIZE;
            }

            KeyHandler.rightPressed = false;
        }

        // Auto drop
        if (bottomCollision){
            deactivating = true;
            //active = false;
        }else {
            autoDropCounter++;
            if (autoDropCounter == PlayManager.dropInterval) {
                blocks[0].y += Block.SIZE;
                blocks[1].y += Block.SIZE;
                blocks[2].y += Block.SIZE;
                blocks[3].y += Block.SIZE;

                autoDropCounter = 0;
            }
        }
    }

    private void deactivatingDelay() {
        deactivateCounter++;

        // Apos 45 frames, fique estatico
        if (deactivateCounter == 45) {
            deactivateCounter = 0;

            checkMovementCollision(); // Checa se o mino ainda esta no chão

            if (bottomCollision) {
                active = false;
            }

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
