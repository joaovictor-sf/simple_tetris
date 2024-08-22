package main;

import javax.swing.*;
import java.awt.*;

/**
 * GamePanel é a classe que gerencia o jogo, implementa o loop do jogo e chama o PlayManager para atualizar e desenhar o jogo.<br>
 * GamePanel é um JPanel que implementa a interface Runnable.<br>
 * GamePanel é responsável por inicializar o PlayManager e a música de fundo.<br>
 * @author joaovictor-sf
 */
public class GamePanel extends JPanel implements Runnable {
    /**
     * 1280 pixels de largura
     */
    public static final int WIDTH = 1280;
    /**
     * 720 pixels de altura
     */
    public static final int HEIGHT = 720;
    /**
     * 60 frames por segundo
     */
    final int FPS = 60;
    /**
     * Thread do jogo
     */
    Thread gameThread;
    /**
     * PlayManager do jogo
     */
    PlayManager playManager;
    /**
     * Música de fundo
     */
    public static Sound music = new Sound();
    /**
     * Efeito sonoro
     */
    public static Sound soundEffect = new Sound();

    /**
     * Construtor da classe GamePanel<br>
     * Inicializa o JPanel, adiciona o KeyHandler e configura o PlayManager<br>
     */
    public GamePanel() {
        // Configurações do JPanel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);// Desabilita o layout padrão do JPanel

        // Implementa o KeyHandler
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        // Inicializa o PlayManager
        playManager = new PlayManager();
    }

    /**
     * Inicia o jogo<br>
     * Inicializa a Thread do jogo e a música de fundo<br>
     */
    public void LaunchGame() {
        gameThread = new Thread(this);
        gameThread.start();

        music.play(0, true);
        music.loop();
    }

    /**
     * É o metodo que roda o jogo
     */
    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000.0/FPS;// Intervalo de tempo entre cada frame
        double delta = 0;// Armazena o tempo que falta para atualizar o jogo
        long lastTime = System.nanoTime();
        long currentTime;

        // Loop do jogo
        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Atualiza o jogo<br>
     * Atualiza o PlayManager se o jogo não estiver pausado e se o jogo não tiver acabado<br>
     */
    private void update() {
        if (!KeyHandler.pausePressed && !playManager.gameover) {
            playManager.update();
        }
    }

    /**
     * Desenha o jogo<br>
     * Desenha o PlayManager<br>
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        playManager.draw(g2);
    }
}
