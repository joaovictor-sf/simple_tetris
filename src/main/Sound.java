package main;

import javax.sound.sampled.*;
import java.net.URL;

/**
 * Sound é a classe que gerencia os efeitos sonoros do jogo.
 * @author joaovictor-sf
 */
public class Sound {

    /**
     * Clip da música de fundo
     */
    Clip musicClip;
    /**
     * Array de URLs dos efeitos sonoros
     */
    URL[] url = new URL[10];
    public Sound(){
        url[0] = getClass().getResource("/res/music.wav");
        url[1] = getClass().getResource("/res/delete line.wav");
        url[2] = getClass().getResource("/res/gameover.wav");
        url[3] = getClass().getResource("/res/rotation.wav");
        url[4] = getClass().getResource("/res/touch floor.wav");
    }

    /**
     * Toca um efeito sonoro
     * @param i Índice do efeito sonoro
     * @param music Se é música de fundo
     */
    public void play(int i, boolean music){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music){
                musicClip = clip;
            }

            clip.open(audioInputStream);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP){
                    clip.close();
                }
            });
            audioInputStream.close();
            clip.start();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Toca a música de fundo em loop
     */
    public void loop(){
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Para a música de fundo
     */
    public void stop(){
        musicClip.stop(); // Para a música
        musicClip.close(); // Fecha o clip para liberar memória
    }
}
