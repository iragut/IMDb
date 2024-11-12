package org.example.interfaces;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class VideoPlayer {
    private final String path;
    private MediaPlayer mediaPlayer;
    private Media media;

    public VideoPlayer(String path) {
        this.path = path;
    }

    public void play() {
        JFrame frame = new JFrame("Trailer");
        frame.setIconImage(new ImageIcon("src/main/java/org/example/img/imdb.png").getImage());
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JFXPanel fxPanel = new JFXPanel();
        media = new Media(new File(this.path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        JButton playPauseButton = new JButton("Play/Pause");
        playPauseButton.setFocusable(false);
        playPauseButton.setBackground(Color.DARK_GRAY);
        playPauseButton.setForeground(Color.WHITE);
        playPauseButton.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        playPauseButton.setFont(new Font("Impact Regular", Font.BOLD, 18));
        playPauseButton.addActionListener(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
                mediaPlayer.pause();
            else
                mediaPlayer.play();
        });

        JButton stopButton = new JButton("Close");
        stopButton.setFocusable(false);
        stopButton.setBackground(Color.DARK_GRAY);
        stopButton.setForeground(Color.WHITE);
        stopButton.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        stopButton.setFont(new Font("Impact Regular", Font.BOLD, 18));
        stopButton.addActionListener(e -> {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            fxPanel.setScene(null);
            frame.setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(800);
        mediaView.setFitHeight(600);
        Scene scene = new Scene(new Group(mediaView));

        fxPanel.setScene(scene);
        mediaPlayer.play();

        buttonPanel.add(playPauseButton);
        buttonPanel.add(stopButton);

        frame.add(fxPanel);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

}
