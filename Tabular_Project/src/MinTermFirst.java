import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MinTermFirst extends JPanel implements ActionListener{
	static JButton submit = new JButton("Minimize");
	static JTextField inField = new JTextField();
	static JTextField inVars = new JTextField();
	static print show;
	public static int countONes(int x){
		int count =0;
		while(x>0){
			if(x % 2 > 0)count++;
			x/=2;
		}
		return count;
	}
	public static MyLinkedList FirstStep(MyLinkedList temp){
		show = new print();
		MyLinkedList tempOrg = new DLinkedList();
		for(int i=0;i<temp.size();i++){
			tempOrg.add( new DLinkedList() );
			((MyLinkedList)tempOrg.get(i)).add(((MyLinkedList)temp.get(i)).get(0));
		}
		boolean done[] = new boolean[10000];
		boolean flag = true;
		Map<Long,Boolean> stored = new HashMap<Long,Boolean>();
		while(true){
			show.printFirst(temp);
			flag = false;
			int tempSize = temp.size();
			for(int i=0;i<tempSize-1;i++){
				for(int j=0;j<tempSize;j++){
					if(i == j)continue;
					MyLinkedList curr = (MyLinkedList)temp.get(i);
					MyLinkedList later = (MyLinkedList)temp.get(j);
					int rmask = (int)curr.get(0);
					int bmask = (int)later.get(0);
					if(rmask >= bmask)continue;
					boolean skip = false;
					if(curr.size() != later.size())continue;
					for(int k=1;k<later.size();k++){
						if(curr.get(k) != later.get(k)){
							skip = true;
							break;
						}
					}
					if(skip)continue;
					if( countONes(bmask) - countONes(rmask) != 1 )continue;
					if( Math.abs(countONes(Math.abs(rmask-bmask))) != 1)continue;
					
					flag = true;
					MyLinkedList tList = new DLinkedList();
					MyLinkedList oList = new DLinkedList();
					for(int k=0;k<curr.size();k++){
						tList.add(curr.get(k));
					}
					for(int k=0;k<((MyLinkedList)tempOrg.get(i)).size();k++){
						oList.add(((MyLinkedList)tempOrg.get(i)).get(k));
					}
					for(int k=0;k<((MyLinkedList)tempOrg.get(j)).size();k++){
						oList.add(((MyLinkedList)tempOrg.get(j)).get(k));
					}
					tList.add(Math.abs(bmask-rmask));
					long tempHash = tList.hashIt();
					if(!stored.containsKey(tempHash)){
						temp.add(tList);
						tempOrg.add(oList);
						stored.put(tempHash, true);
					}
					done[j] = true;
					done[i]  = true;
					
				}
			}
			for(int i=0;i<tempSize;i++){
				if(!done[i]){
					temp.add(temp.get(i));
					tempOrg.add(tempOrg.get(i));
				}
			}
			for(int i=0;i<tempSize;i++){
				temp.remove(0);
				tempOrg.remove(0);
			}
			if(tempSize == temp.size())break;
			done = new boolean[1000];
//			for(int i=0;i<tempOrg.size();i++){
//				for(int j=0;j<((MyLinkedList)tempOrg.get(i)).size();j++){
//					try{
//					System.out.print(" "+(int)((MyLinkedList)tempOrg.get(i)).get(j));
//					}catch(Exception ex){
//						System.out.println(ex);
//					}
//				}
//				System.out.println();
//			}
			
		}
		show.show();
		return SecondStep(tempOrg,temp);
//		return temp;
	}	
	public static MyLinkedList SecondStep(MyLinkedList temp,MyLinkedList org){
		int n =0 ;
		int[] countIt = new int[100000];
		boolean[] skip = new boolean[100000];
		MyLinkedList result = new DLinkedList();
		for(int i=0;i<temp.size();i++){
//			System.out.println(expression((MyLinkedList)temp.get(i), 4));
			for(int j=0;j<((MyLinkedList)temp.get(i)).size();j++){
//				System.out.print(" "+((MyLinkedList)temp.get(i)).get(j));
				countIt[(int)((MyLinkedList)temp.get(i)).get(j)]++;
			}
//			System.out.println();
		}
		for(int i=0;i<temp.size();i++){
			for(int j=0;j<((MyLinkedList)temp.get(i)).size();j++){
				if(countIt[((int)((MyLinkedList)temp.get(i)).get(j))] == 1){
					result.add((MyLinkedList)org.get(i));
					for(int k=0;k<((MyLinkedList)temp.get(i)).size();k++){
						countIt[((int)((MyLinkedList)temp.get(i)).get(k))] = 0;
					}
					System.out.println(expression((MyLinkedList)org.get(i), 5)+" + is Prime");
					skip[i] = true;
					break;
				}
					
			}
		}
		boolean operating = true;
		while(operating){
			operating = false;
			int maxScore = 0;
			int chosen = -1;
			for(int i=0;i<temp.size();i++){
				if(skip[i])continue;
				boolean flag = false;
				int score = 0;
				for(int j=0;j<((MyLinkedList)temp.get(i)).size();j++){
					if(countIt[((int)((MyLinkedList)temp.get(i)).get(j))] > 0){
						score ++;
						flag = true;
						operating = true;
					}
				}
				
				if(!flag){
					skip[i] = true;
				}
				if(score > 0 && score > maxScore){
					maxScore = score ;
					chosen = i;
				}
				else if(score > 0 && score > maxScore && ((MyLinkedList)temp.get(i)).size() < ((MyLinkedList)temp.get(chosen)).size()){
					maxScore = score ;
					chosen = i;
				}

			}
			if(chosen == -1)break;
			result.add((MyLinkedList)org.get(chosen));
			for(int i=0;i<((MyLinkedList)temp.get(chosen)).size();i++){
				countIt[((int)((MyLinkedList)temp.get(chosen)).get(i))] = 0;
//				System.out.print(" "+(int)((MyLinkedList)temp.get(chosen)).get(i));
			}
//			System.out.println(" +"+chosen);
			skip[chosen] = true;
		}
		
		return result;
	}
	
	static public String expression(MyLinkedList exp, int bits){
		boolean[] skip = new boolean[100];
		int number = (int)exp.get(0);
		String s = "";
		for(int i=0;i<100;i++)
			skip[i]=false;
		
		for(int i=1;i<exp.size();i++)
			skip[(int)exp.get(i)] = true;
		
		
		for(int i=0;i<bits ; i++){
			if(skip[1<<(bits-i-1)])continue;
			s+=(char)('A'+i);
			if((number&(1<<bits-i-1))==(1<<bits-i-1) )s+="";
			else s+="'";
		}
		if(s == "")s="1";
		return s;
	}
	
	public static void main(String[] args) {

		JFrame gui = new JFrame();
		submit.setBounds(425,500,150,30);
		inField.setBounds(100, 100, 800, 40);
		inVars.setBounds(375, 70, 250, 30);
		submit.addActionListener(new MinTermFirst());
		gui.pack();
		gui.setTitle("Minterms Minimizer");
		gui.setSize(1000, 600);
		gui.add(inField);
		gui.add(inVars);
		gui.add(submit);
		gui.setLayout(new BorderLayout());
		gui.setResizable(true);
		gui.setVisible(true);
//		Scanner in = new Scanner(System.in);
//		System.out.print("Enter the number of variables : ");
//		vars = in.nextInt();
//		System.out.print("Enter the minterms : ");
//		String inTerms = in.next();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submit){
			int vars;
			vars = Integer.parseInt(inVars.getText());
			MyLinkedList input = new DLinkedList();
			String inTerms = inField.getText();
			String[] tempS = inTerms.split(",");
			for(int i=0;i<tempS.length;i++){
				try{
					int temp = Integer.parseInt(tempS[i]);
					MyLinkedList tempL = new DLinkedList();
					tempL.add(temp);
					input.add(tempL);
				}catch(Exception ex){}
			}
			MyLinkedList hamada = FirstStep(input);
//			String defTemp = "";
//			for(int i=0;i<vars;i++){
//				defTemp += (char)('A'+i)+" ";
//			}
			for(int i=0;i<hamada.size();i++){
//				String temp = defTemp;
//				int tx = (int)((MyLinkedList)hamada.get(i)).get(0);
//				for(int k=0;k<vars;k++){
//					if((tx & (1<<k)) == 0){
//						temp = temp.substring(0, k*2+1) + "'"+temp.substring(k*2+2);
//					}
//				}
//				System.out.print(tx+" ");
//				for(int j=1;j<((MyLinkedList)hamada.get(i)).size();j++){
//					tx =  (int)((MyLinkedList)hamada.get(i)).get(j);
//					System.out.print(tx+" ");
//					int ty = 0;
//					for(int k=1;k<=vars;k++){
//						if((tx & (1<<(k-1))) != 0){
//							temp = temp.substring(0, k*2)+"  "+(k*2+2 < temp.length()?temp.substring(k*2+2):"");
//							break;
//						}
//					}
//				}
//				System.out.println();
				System.out.println(expression((MyLinkedList)hamada.get(i), vars));
			}
//			MyLinkedList lol = new DLinkedList();
//			lol.add(0);
//			lol.add(1);
//			lol.add(2);
//			System.out.println(expression(lol, 3));

		}
	}

}
/*
 * Enter the number of variables : 5
Enter the minterms : 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63

 0,1,2,5,6,7,8,9,10,14 => f= b'c' + cd' + a'bd
 0,3,5,6,9,10,11,14,15 => y = (x̄3x̄2x̄1x̄0) ∨ (x̄2x1x0) ∨ (x̄3x2x̄1x0) ∨ (x2x1x̄0) ∨ (x3x̄2x0) ∨ (x3x1)
 1,2,3,4,5,7,9,10,11,13,15 => y = (x0), (x̄2x1), (x̄3x2x̄1)
 2,3,7,9,11,13,14 => y = (x̄3x̄2x1) ∨ (x̄3x1x0) ∨ (x3x̄1x0) ∨ (x3x2x1x̄0) ∨ (x̄2x1x0)
 
 1,3,4,5,6 => y = (x̄2x0) ∨ (x2x̄0) ∨ (x̄1x0) => (x̄2x0), (x2x̄0)
 0,1,2,4,5,7,8,9,10,13,14,15,16,18,19,22,24,25,26,28,29,30,31 => y = (x̄2x̄0) ∨ (x̄4x̄3x̄1) ∨ (x̄4x2x0) ∨ (x4x̄3x̄2x1) ∨ (x4x1x̄0) ∨ (x3x̄1x0) ∨ (x3x2x1) ∨ (x4x3x̄1)
 */
