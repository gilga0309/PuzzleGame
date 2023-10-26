import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

//퍼즐 게임
public class PuzzleGame {
	public static void main(String[] args) throws Exception {
		new Puzzle();
	}
}

class Puzzle{
	JFrame frm = new JFrame("Puzzle");
	JButton[][] btn = new JButton[5][5];
	int[][] puzzle = new int[5][5];
	int value=0;
	int result = 1;
	
	Calendar start = Calendar.getInstance();//time check
	
	public Puzzle() throws Exception{
		frm.setLayout(new GridLayout(5,5,5,5));
		frm.setBounds(100, 100, 300, 300);
  	 
		boolean startend = true;
		Set set = new LinkedHashSet();
      
		for(int i=0; set.size()<25; i++){
			set.add((int)(Math.random()*25)+1+"");
		}
		Iterator it = set.iterator();
		  
		for(int i=0; i<puzzle.length; i++){
			for(int j=0; j<puzzle.length; j++){
				puzzle[i][j]=Integer.parseInt((String) it.next());
				
				if(puzzle[i][j] == 25)
					btn[i][j]= new JButton("");
				else {
					btn[i][j]= new JButton(String.valueOf(puzzle[i][j]));
				}
				btn[i][j].setUI(new StyledButtonUI());
				frm.add(btn[i][j]);
		  	}
		}
		event();
		frm.setVisible(true);
	}
	
	public void inputFrame(){
		for(int i=0; i<btn.length; i++) {
			for(int j=0; j<btn.length; j++) {
				if(puzzle[i][j] == 25)
					btn[i][j].setText("");
				else {
					btn[i][j].setText(String.valueOf(puzzle[i][j]));
				}
			}
		}
	}
	
	public int value() {
		for(int i=0; i<btn.length; i++) {
			for(int j=0; j<btn.length; j++) {
				btn[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						try {
						value = Integer.valueOf(e.getActionCommand());
						}catch(Exception valuee) {}
					}
				});
			}
		}
		return value;
	}
	
	public void event() {
		
		for(int i=0; i<btn.length; i++) {
			for(int j=0; j<btn.length; j++) {
				btn[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						try {
							value = Integer.valueOf(e.getActionCommand());
							cal();
							inputFrame();
						}catch(Exception ae) {}
					}
				});
			}
		}
	}
	
	public void cal() {
		result=1;
		loop1 : for (int i = 0; i < puzzle.length; i ++) {
			for (int j = 0; j < puzzle.length; j ++) {
		          if (j < 4 && 25 == puzzle[i][j]) {
		              if (value() == puzzle[i][j + 1]) {
		                  puzzle[i][j + 1] = 25;
		                  puzzle[i][j] = value();
		                  break loop1;
		              }
		          }
		          if (j > 0 && 25 == puzzle[i][j]) {
		              if (value() == puzzle[i][j - 1]) {
		                  puzzle[i][j - 1] = 25;
		                  puzzle[i][j] = value();
		                  break loop1;
		              }
		          }
		          if (i < 4 && 25 == puzzle[i][j]) {
		              if (value() == puzzle[i + 1][j]) {
		                  puzzle[i + 1][j] = 25;
		                  puzzle[i][j] = value();
		                  break loop1;
		              }
		          }
		          if (i > 0 && 25 == puzzle[i][j]) {
		              if (value() == puzzle[i - 1][j]) {
		                  puzzle[i - 1][j] = 25;
		                  puzzle[i][j] = value();
		                  break loop1;
		              }
		          }
		          }
			}
		for (int i = 0, num = 1; i < puzzle.length; i ++) {
		      for (int j = 0; j < puzzle.length; j ++, num ++) {
		          if (puzzle[i][j] == num) 
		              result ++;
		      }
		  }
		
		if (result >= 25) {
			Calendar humantime = Calendar.getInstance();
			JOptionPane.showMessageDialog( frm, "퍼즐 완성\n"+"경과시간 : "+Math.abs(start.getTimeInMillis()-humantime.getTimeInMillis())/1000+"초");
			System.exit(result);
		}
	}
}

class StyledButtonUI extends BasicButtonUI {

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        //button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }
    
    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Impact", Font.PLAIN, 18));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(0x2dce98));
        g.fillRoundRect(0, yOffset, size.width, size.height, 50, 50);
    }
}