package com.dragonmaster;

import org.jsfml.graphics.*;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    GridLayout layout = new GridLayout(0, 1);

    public Main() {
        JFrame frame = new JFrame("DMLauncher");
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        JLabel nameLabel = new JLabel("Dragon Master");
        JButton playButton = new JButton("Play");
        JButton creditsButton = new JButton("Credits");
        frame.setLayout(layout);
        frame.add(nameLabel);
        frame.add(playButton);
        frame.add(creditsButton);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int score = 0;

                RenderWindow window = new RenderWindow();
                window.create(new VideoMode(640, 480), "Dragon Master");

                Texture dtexture = new Texture();
                Texture mtexture = new Texture();
                Font textFont = new Font();

                try {
                    dtexture.loadFromFile(Paths.get("E://NikSFML//Dragon.png"));
                    mtexture.loadFromFile(Paths.get("E://NikSFML//Monster.png"));
                    textFont.loadFromFile(Paths.get("E://NikSFML//FreeMono.ttf"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Sprite dsprite = new Sprite(dtexture);
                Sprite msprite = new Sprite(mtexture);
                dsprite.setScale(5, 5);
                msprite.setScale(5, 5);

                window.setFramerateLimit(30);

                while (window.isOpen()) {
                    score += 1;
                    System.out.println(score);
                    window.clear(Color.RED);
                    window.draw(dsprite);
                    window.draw(msprite);
                    msprite.setPosition(500, dsprite.getPosition().y);
                    Text scoreText = new Text("Score: " + score, textFont, 24);
                    window.draw(scoreText);
                    window.display();

                    for(Event event : window.pollEvents()) {
                        switch (event.type) {
                            case KEY_PRESSED:
                                KeyEvent keyEvent = event.asKeyEvent();
                                switch (keyEvent.key) {
                                    case UP:
                                        dsprite.move(0, -5);
                                        break;
                                    case DOWN:
                                        dsprite.move(0, 5);
                                        break;
                                }
                        }

                        if(event.type == Event.Type.CLOSED) {
                            window.close();
                        }
                    }
                }
            }
        });
        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Game created by Nikitundrik");
            }
        });
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
