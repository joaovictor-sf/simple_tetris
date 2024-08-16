package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Simple Tetris");// Cria uma janela com o título "Simple Tetris"
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Fecha a aplicação quando a janela é fechada
        window.setResizable(false);// Impede que a janela seja redimensionada pelo usuário

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();// Redimensiona a janela para que caiba o JPanel

        window.setLocationRelativeTo(null);// Quer dizer que a não especificamos a posição da janela, então ela será centralizada na tela
        window.setVisible(true);
        gamePanel.LaunchGame();
    }
}