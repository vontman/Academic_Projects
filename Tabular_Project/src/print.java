import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;


public class print  {
  int counter=0;
	private JPanel contentPane;
private JFrame mine;
	
	boolean flag = true;
	 int length = 0;
	JLabel[][] grid;
	private JTextField txtMintermMinimization;
	public print( ) {
		mine= new JFrame();
		grid=new JLabel[100][100];
		for(int i=0;i<100;i++){
			for(int j=0;j<100;j++){
				grid[i][j] = new JLabel();
//				grid[i][j].setSize(200, 200);
				grid[i][j].setFont(new Font("Times New Roman", Font.ITALIC, 20));
			}
		}
	
		mine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		mine.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		mine.getContentPane().setLayout(new BorderLayout());
		
		txtMintermMinimization = new JTextField();
		txtMintermMinimization.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		txtMintermMinimization.setText("Minterm minimization");
		mine.getContentPane().add(txtMintermMinimization, BorderLayout.NORTH);
		txtMintermMinimization.setColumns(10);
		mine.setSize(500,500);

	
	}
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
  public void printFirst(MyLinkedList temp){
	  if (flag){
		  length=temp.size();
		  flag=false;
	  }
	  for(int i=0;i<temp.size();i++){
			MyLinkedList curr = (MyLinkedList)temp.get(i);
			String term="";
			for(int j=0;j<curr.size();j++){
				term+= Integer.toString((int)curr.get(j))+" ";
			}
			grid[counter][i] = new JLabel(term);
			grid[counter][i].setBackground(Color.black);
			
		}
	  for(int i=temp.size();i<length;i++){
			
			grid[counter][i].setText("");	
			grid[counter][i].setBackground(Color.black);
		}
	  counter++;
  }
  
   public void show(){
	   for(int i=0;i<counter;i++){
		   for(int j=0;j<length;j++){
//			   mine.getContentPane().add(grid[i][j]);
			   mine.getContentPane().add(grid[i][j]);
			   grid[i][j].setBounds(i*200,j*20+30,200,20);
		   }
	   }
	   mine.setVisible(true);
   }
	

}
