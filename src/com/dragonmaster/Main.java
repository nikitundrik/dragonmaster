package com.dragonmaster;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.system.Clock;
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
                Texture ltexture = new Texture();
                Texture btexture = new Texture();
                Font textFont = new Font();
                SoundBuffer buffer = new SoundBuffer();
                Sound mnoise = new Sound();

                try {
                    dtexture.loadFromFile(Paths.get("E://JSFMLProjects//DragonMasterLauncher//Dragon.png"));
                    mtexture.loadFromFile(Paths.get("E://JSFMLProjects//DragonMasterLauncher//Monster1.png"));
                    ltexture.loadFromFile(Paths.get("E://JSFMLProjects//DragonMasterLauncher//Laser.png"));
                    btexture.loadFromFile(Paths.get("E://JSFMLProjects//DragonMasterLauncher//Bullet.png"));
                    textFont.loadFromFile(Paths.get("E://JSFMLProjects//DragonMasterLauncher//FreeMono.ttf"));
                    buffer.loadFromFile(Paths.get("E://JSFMLProjects//DragonMasterLauncher//Noise.wav"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Sprite dsprite = new Sprite(dtexture);
                Sprite msprite = new Sprite(mtexture);
                Sprite lsprite = new Sprite(ltexture);
                Sprite bsprite = new Sprite(btexture);
                mnoise.setBuffer(buffer);
                dsprite.setScale(5, 5);
                msprite.setScale(5, 5);

                Clock spawnALaserClock = new Clock();

                window.setFramerateLimit(30);

                while (window.isOpen()) {
                    score += 1;
                    System.out.println(score);
                    window.clear(Color.RED);
                    window.draw(dsprite);
                    window.draw(msprite);
                    msprite.setPosition(500, dsprite.getPosition().y);
                    Text scoreText = new Text("Score: " + score, textFont, 24);
                    if(spawnALaserClock.getElapsedTime().asSeconds() >= 1) {
                        bsprite.setPosition(500, msprite.getPosition().y);
                        mnoise.play();
                        spawnALaserClock.restart();
                    }
                    window.draw(scoreText);
                    window.draw(bsprite);
                    bsprite.move(-50, 0);
                    if(dsprite.getPosition().y >= 350) {
                        dsprite.setPosition(0, 350);
                    }
                    if(dsprite.getPosition().y <= -10) {
                        dsprite.setPosition(0, -10);
                    }
                    if(dsprite.getPosition().y == bsprite.getPosition().y) {
                        if(dsprite.getPosition().x == bsprite.getPosition().x) {
                            window.close();
                        }
                    }
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
                JOptionPane.showMessageDialog(null, "Game created by Nikitundrik\nFont: FreeMono (You can get it at FreeFont: https://www.gnu.org/software/freefont/)\nGame made for Weekly Game Jam (Theme: Tame a Dragon)\nLicense: GNU GPL");
            }
        });
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
