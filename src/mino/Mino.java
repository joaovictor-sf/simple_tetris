package mino;

import main.KeyHandler;
import main.PlayManager;
import main.GamePanel;

import java.awt.*;

/**
 * Cada mino representa uma das 7 peças do jogo.<br>
 * Cada peça é composta por 4 blocos.<br>
 * Cada mino tem 4 direções.<br>
 * @author joaovictor-sf
 */
public class Mino {

    /**
     * Os blocos que compõem o mino.
     */
    public Block blocks[] = new Block[4];
    /**
     * Blocos temporários para checar colisões, antes de mover ou rotacionar o mino.
     */
    public Block tempBlocks[] = new Block[4];
    /**
     * Contador para o auto drop.
     */
    int autoDropCounter = 0;
    /**
     * Direção do mino.
     * @default 1
     */
    public int direction = 1; // Cada mino tem 4 direções
    /**
     * Representa se a peça colidiu.
     */
    boolean leftCollision, rightCollision, bottomCollision;
    /**
     * Representa se a peça está ativa. As peças são as peças controladas pelo jogador e que estão caindo.
     */
    public boolean active = true;
    /**
     * Representa se a peça está sendo desativada. As peças são desativadas quando colidem com o chão.
     */
    public boolean deactivating;
    /**
     * Contador para o delay de desativação.
     */
    int deactivateCounter = 0;

    /**
     * Cria um mino com a cor especificada.
     * @param color Cor do mino.
     */
    public void create(Color color){
        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(color);
            tempBlocks[i] = new Block(color);
        }
    }
    /**
     * Define a posição do mino.
     * @param x Posição x.
     * @param y Posição y.
     */
    public void setXY(int x, int y){}

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    /**
     * Checa colisões de movimento.
     */
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

    /**
     * Checa colisões de rotação.
     * Se houver colisão, o mino não rotaciona.
     */
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

    /**
     * Atualiza a posição do mino.
     * @param direction Direção do mino.
     */
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

    /**
     * Checa colisões com os blocos estáticos.
     */
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

    /**
     * Realiza as atualizações de movimento do mino, como movimento, rotação e desativação.
     */
    public void update(){

        if (deactivating) deactivatingDelay();

        // Move mino
        if (KeyHandler.upPressed){
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
            GamePanel.soundEffect.play(3, false);
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
            if (!deactivating) GamePanel.soundEffect.play(4, false);
            deactivating = true;
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

    /**
     * Delay para desativar o mino.
     */
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

    /**
     * Desenha o mino.
     * @param g2 Gráficos 2D.
     */
    public void draw(Graphics2D g2){
        int margin = 2;

        g2.setColor(blocks[0].color);
        for (int i = 0; i < 4; i++) {
            g2.fillRect(blocks[i].x+margin, blocks[i].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        }
    }
}
