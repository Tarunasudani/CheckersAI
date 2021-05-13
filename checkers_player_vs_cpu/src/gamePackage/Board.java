package gamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel implements ActionListener {

    public int [][] config = new int[8][8];
    public int[][] cloneManual(int[][] node){
        int[][] arr = new int[8][8];
        for(int i=0;i<8;i++){
            arr[i] = node[i].clone();
        }
        return arr;
    }
    Timer tm = new Timer(1, (ActionListener) this);
    public void paint(Graphics g){
        super.paint(g);

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                g.drawRect(i*100,j*100,100,100);
                if((i+j)%2 == 0){
                    g.setColor(Color.RED);
                }
                else{
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i*100,j*100,100,100);


                if(config[i][j] == 1 ){
                    g.drawOval(i*100 + 20 ,j*100 + 20 , 60,40);
                    g.setColor(Color.BLUE);
                    g.fillOval(i*100 +20 ,j*100 + 20 , 60,40);
                }
                if(config[i][j] == 2){
                    g.drawOval(i*100 +20 ,j*100 + 20, 60,40);
                    g.setColor(Color.GRAY);
                    g.fillOval(i*100 +20 ,j*100 + 20, 60,40);
                }
            }
        }

    }

    public void performMove(int[][] node){

        this.config = cloneManual(node);
        tm.start();
    }
    @Override
    public void actionPerformed(ActionEvent e){

        repaint();

        Timer t = (Timer) e.getSource();
        t.stop();
    }
    Board(int [][] config){
        this.config = config;

    }
}
