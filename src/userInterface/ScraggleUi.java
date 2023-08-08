/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Ilham Mukati
 */
public class ScraggleUi 
{
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem exit;
    private JMenuItem newGame;
    
    private JPanel scragglePanel;
    private JButton[][] diceButtons;
    
    private JPanel wordsPanel;
    private JScrollPane scrollPane;
    private JTextPane wordsArea;
    
    private JLabel timeLabel;
    private JButton shakeDice;
    
    private JPanel currentPanel;
    private JLabel currentLabel;
    private JButton currentSubmit;
    
    private JLabel scoreLabel;
    
    private final Game game;
    
    int score;
    Timer timer;
    int minutes;
    int seconds;
    ArrayList<String> foundWords;
    ResetGameListener resetGameListener;
    
        public ScraggleUi(Game g)
        {
            resetGameListener = new ResetGameListener();
            game = g;
            initComponents();
        }   
    
       
        private void initComponents()
       {
        foundWords = new ArrayList<>();
        
       
         frame = new JFrame("Scraggle");
         frame.setSize(new Dimension(600,500));
 
         
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         BorderLayout BorderLayout = new BorderLayout();
         
         
         
         menuBar = new JMenuBar();
         
         gameMenu = new JMenu("Scraggle");
         menuBar.add(gameMenu);
         
         newGame = new JMenuItem("New Game");
         exit = new JMenuItem("Exit");
         
         gameMenu.add(newGame);
         gameMenu.add(exit);
         
         frame.setJMenuBar(menuBar);
      
         scragglePanel = new JPanel();
         scragglePanel.setLayout(new GridLayout(4,4));
         scragglePanel.setMinimumSize(new Dimension(400,400));
         scragglePanel.setPreferredSize(new Dimension(400,400));
         scragglePanel.setBorder(BorderFactory.createTitledBorder("Scraggle Board"));
          
         diceButtons = new JButton[4][4];
         
         
          for(int row = 0; row < 4; ++row)
          {
              for(int col = 0; col < 4; ++col)
              {
                  URL imgURL;
                  imgURL = getClass().getResource(game.getGrid()[row][col].getImagePath());
                  ImageIcon icon = new ImageIcon(imgURL);
                  
                  diceButtons[row][col] = new JButton(icon);
                  diceButtons[row][col].putClientProperty("row", row);
                  diceButtons[row][col].putClientProperty("col", col);
                  diceButtons[row][col].putClientProperty("letter", game.getGrid()[row][col].getLetter());
                  
                  diceButtons[row][col].addActionListener(new TileListener()); 
                  diceButtons[row][col].addActionListener(new LetterListener());
                  
                  scragglePanel.add(diceButtons[row][col]);
              }
          }
          
          
      
      
         
         currentPanel = new JPanel();
         currentPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));
          
         currentLabel = new JLabel();
         currentLabel.setBorder(BorderFactory.createTitledBorder("Current Word"));
         currentLabel.setMinimumSize(new Dimension(200,100));
         currentLabel.setPreferredSize(new Dimension(200, 50));
         currentPanel.add(currentLabel);
         
         
         currentSubmit = new JButton("Submit");
         currentSubmit.setMinimumSize(new Dimension(200, 100));
         currentSubmit.setPreferredSize(new Dimension(200, 50));
         
         
         currentPanel.add(currentSubmit);
         
         scoreLabel = new JLabel();
         scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
         scoreLabel.setMinimumSize(new Dimension(100, 50));
         scoreLabel.setPreferredSize(new Dimension(100, 50));
         currentPanel.add(scoreLabel);
         
         wordsPanel = new JPanel();
         wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.PAGE_AXIS));
         wordsPanel.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
         wordsArea = new JTextPane();
         scrollPane = new JScrollPane(wordsArea);
         scrollPane.setPreferredSize(new Dimension(180,330));
         scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         wordsPanel.add(scrollPane);
         
         timeLabel = new JLabel();
         timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
         timeLabel.setBorder(BorderFactory.createTitledBorder("Time Left"));
         timeLabel.setFont(new Font("Serif", Font.PLAIN, 50));
         timeLabel.setText("3:00");
         timeLabel.setPreferredSize(new Dimension(475,100));
         timeLabel.setMinimumSize(new Dimension(475,100));
         timeLabel.setMaximumSize(new Dimension(475,100));
         wordsPanel.add(timeLabel);
         shakeDice = new JButton("Shake Dice");
         shakeDice.setPreferredSize(new Dimension(240, 100));
         shakeDice.setMinimumSize(new Dimension(240, 100));
         shakeDice.setMaximumSize(new Dimension(240,100));
         wordsPanel.add(shakeDice);
         
         
         
         
         exit.addActionListener(new ExitListener());
         newGame.addActionListener(resetGameListener);
         shakeDice.addActionListener(resetGameListener);
         currentSubmit.addActionListener(new SubmitListener());
         setupTimer();
        
         frame.add(scragglePanel, BorderLayout.WEST);
         frame.add(wordsPanel, BorderLayout.CENTER);
         frame.add(currentPanel, BorderLayout.SOUTH);
         frame.setVisible(true);
         
        
     }
        
     private void setupTimer()
     {
        timer = new Timer(1000, new TimerListener());
        minutes = 3;
        seconds = 0;
        timer.start();
     }

    
    private class TimerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
           if(seconds == 0 && minutes == 0)
           {
               timer.stop();
               JOptionPane.showMessageDialog(frame, "Time's Up!");
               
           }
           else
           {
               if(seconds == 0)
               {
                   seconds = 59;
                   minutes--;
               }
               else
               {
                   seconds--;
               }
               
           }
           
           if(seconds < 10)
           {
               String strSeconds = "0" + String.valueOf(seconds);
               timeLabel.setText(String.valueOf(minutes) + ":" + strSeconds);
               
           }
           else
           {
               timeLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
           }
        }

    }
    
    private class TileListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            
            for(int i = 0; i < 4; ++i)
                {
                     for(int j = 0; j < 4; ++j)
                    {
                         diceButtons[i][j].setEnabled(false);
                    }
                }
            
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton)ae.getSource();
                
                int row = (int)button.getClientProperty("row");
                int col = (int)button.getClientProperty("col");
                
                if(row == 0 && col == 0)
                {
                    diceButtons[row + 1][col].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                }
                else if (row == 3 && col == 3)
                {
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row - 1][col - 1].setEnabled(true);
                }
                else if(row == 3 && col == 0)
                {
                   diceButtons[row - 1][col].setEnabled(true);
                   diceButtons[row - 1][col + 1].setEnabled(true);
                   diceButtons[row][col + 1].setEnabled(true);
                }
                else if (row > 0 && col > 0 && row < 3 && col < 3)
                {
                    diceButtons[row - 1][col - 1].setEnabled(true);
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row - 1][col + 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row + 1][col - 1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                }
                else if(row == 0 && col < 3)
                {
                    diceButtons[row][col-1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                    diceButtons[row + 1][col - 1].setEnabled(true);
                }
                else if(row >= 1  && col == 3)
                {
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                    diceButtons[row + 1][col - 1].setEnabled(true);
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row - 1][col - 1].setEnabled(true);
                }
                 else if(row == 0 && col == 3)
                {
                    diceButtons[row + 1][col - 1].setEnabled(true);
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                }
                else if( row == 3 && col > 0 && col < 3)
                {
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row - 1][col - 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row - 1][col + 1].setEnabled(true);
                }
                else if( row < 3 && row > 0 && col == 0)
                {
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row - 1][col + 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                }
                
            }
           
        }

    }
    
    private class SubmitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            System.out.println(currentLabel.getText());
            int wordScore = game.getDictionary().search(currentLabel.getText().toLowerCase());
            
            if(foundWords.contains(currentLabel.getText().toLowerCase()))
            {
                JOptionPane.showMessageDialog(frame, "Word Already Found");
                
                for(int i = 0; i < 4; ++i)
                {
                     for(int j = 0; j < 4; ++j)
                    {
                         diceButtons[i][j].setEnabled(true);
                    }
                }
                
                currentLabel.setText("");
            }
            else if(wordScore > 0)
            {
                
                wordsArea.setText(wordsArea.getText() + currentLabel.getText() + "\n");
                foundWords.add(currentLabel.getText());
                score += wordScore;
                scoreLabel.setText(String.valueOf(score));
                
                
                
                for(int i = 0; i < 4; ++i)
                {
                     for(int j = 0; j < 4; ++j)
                    {
                         diceButtons[i][j].setEnabled(true);
                    }
                }
                
                currentLabel.setText("");
            }
            
            else
            {
                JOptionPane.showMessageDialog(frame, "Word Invalid");
                
                for(int i = 0; i < 4; ++i)
                {
                     for(int j = 0; j < 4; ++j)
                    {
                         diceButtons[i][j].setEnabled(true);
                    }
                }
                   currentLabel.setText("");
            }
           
        }

    }
    
    private class LetterListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
           if(ae.getSource() instanceof JButton)
           {
               JButton button = (JButton)ae.getSource();
               String letter = (String)button.getClientProperty("letter");
               currentLabel.setText(currentLabel.getText() + letter);
               
           }
        }

    }
    
    
    
    
    private class ResetGameListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
           score = 0;
           game.populateGrid();
           
           frame.remove(scragglePanel);
           
           scragglePanel.removeAll();
           
           scragglePanel = new JPanel();
           scragglePanel.setLayout(new GridLayout(4,4));
           scragglePanel.setMinimumSize(new Dimension(400,400));
           scragglePanel.setPreferredSize(new Dimension(400,400));
           scragglePanel.setBorder(BorderFactory.createTitledBorder("Scraggle Board"));
           
           for(int row = 0; row < 4; ++row)
          {
              for(int col = 0; col < 4; ++col)
              {
                  URL imgURL;
                  imgURL = getClass().getResource(game.getGrid()[row][col].getImagePath());
                  ImageIcon icon = new ImageIcon(imgURL);
                  
                  diceButtons[row][col] = new JButton(icon);
                  diceButtons[row][col].putClientProperty("row", row);
                  diceButtons[row][col].putClientProperty("col", col);
                  diceButtons[row][col].putClientProperty("letter", game.getGrid()[row][col].getLetter());
                  
                  diceButtons[row][col].addActionListener(new TileListener()); 
                  diceButtons[row][col].addActionListener(new LetterListener());
                  
                  scragglePanel.add(diceButtons[row][col]);
              }
          }
           
           
           
           scragglePanel.revalidate();
           scragglePanel.repaint();
           
           frame.add(scragglePanel, BorderLayout.WEST);
           
           wordsArea.setText("");
           scoreLabel.setText(String.valueOf(score));
           currentLabel.setText("");
           timeLabel.setText("3:00");
           
           foundWords.removeAll(foundWords);
           
           timer.stop();
           
           minutes = 3;
           
           seconds = 0;
           
           timer.start();
        }

    }
    
    private class ExitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
           int response = JOptionPane.showConfirmDialog(frame, "Would you like to exit?", "Exit?", JOptionPane.YES_NO_OPTION);
           
           if(response == JOptionPane.YES_OPTION)
           {
               System.exit(0);
           }
        }

    }
         
      
}

    
    

    




