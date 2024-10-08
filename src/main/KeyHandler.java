package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe responsável por gerenciar os eventos de teclado do jogo.<br>
 * KeyHandler implementa a interface KeyListener.<br>
 * @author joaovictor-sf
 */
public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;

        if (code == KeyEvent.VK_SPACE)
            if (!pausePressed) {
                pausePressed = true;
                GamePanel.music.stop();
            } else {
                pausePressed = false;
                GamePanel.music.play(0, true);
                GamePanel.music.loop();
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
