/*
Name: Winnie Wan
BB ID: wwan5

Name: Xiaoxiang "Steven" Liu
BB ID: xliu102

HW: Lab#8
Reference was used from http://forum.codecall.net/topic/54232-making-scatter-plot-graphs-in-java/ Code by CC Lurker
Reference was also used from textbook & lectures
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.Graphics;
import java.util.*;

public class HashPlot {

	public static int hash(int a, int b, int m, int n) {
		return (int)((a * n + b) % m);
	}
	
	public static void main(String[] args) throws IOException{
		ArrayList<Integer> input = new ArrayList<Integer>();
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		//Process terminal input 
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		int m = Integer.parseInt(args[2]);
		BufferedReader reader = new BufferedReader(new FileReader(args[3])); 
		
		//Process file
		String st;
		while ((st = reader.readLine()) != null) {
			String temp = st;
			for(String str : temp.split(", ")) {
				input.add(Integer.parseInt(str));
			}
		}
		reader.close();
		for(Integer i : input) {
			output.add(hash(a, b, m, i));
		}
		
		//Create file
		File file = new File("output_sequence.txt");
		try {
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < output.size(); i++) {
					writer.write(output.get(i) + " ");
			}
		    writer.close();
		} catch (IOException e) {
		   System.out.println(e.getMessage());
		}
		
		System.out.println("Size is " + input.size());
		System.out.println("Alpha is " + (double) input.size() / m);
		while((double)input.size() / m  > 0.75 && m * 2 <= 130) {
			m = m * 2;
			System.out.println("Alpha reaches 0.75. Rehashed by using m = " + m + ".");
			output.clear();
			for(Integer i : input) {
				output.add(hash(a, b, m, i));
			}
		}
		
		Scatterplot plot = new Scatterplot(input, output, "Equation: ("+args[0]+"k"+" + "+args[1]+") mod "+args[2]);
	}
	public static class Scatterplot extends JFrame {

		private List points = new ArrayList();

		public Scatterplot(ArrayList<Integer> nums, ArrayList<Integer> hash, String title) {
			super("Scatterplot");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			for(int a = 0; a < nums.size(); a++)
			{
				points.add(new Point2D.Float(nums.get(a), hash.get(a)));
			}


			JPanel panel = new JPanel() {
				public void paintComponent(Graphics g) {
					for(Iterator i=points.iterator(); i.hasNext(); ) {
						Point2D.Float pt = (Point2D.Float)i.next();
						g.drawString("*", (int)(pt.x)+40,
								(int)(-pt.y+getHeight())- 40);
					}
					int width = getWidth();
					int height = getHeight();
					setVisible(true);
					g.drawLine(0, height - 40, width, height-40);
					g.drawLine(40, height-200, 40, height);

					//y-axis labels
					for (int a = 1; a < 5; a++)
					{
						String temp = 20*a + "--";
						g.drawString(temp, 20, height - (36 + 20*(a)));
					}
					for (int a = 5; a < 8; a++)
					{
						String temp = 20*a + "--";
						g.drawString(temp, 11, height - (36 + 20*(a)));
					}

					//x-axis labels
					for (int a = 1; a < 21; a++)
					{
						g.drawString("|", 40 + 50*a, height - 30);
						int x = 50*a;
						String temp = x + " ";
						g.drawString(temp, 30 + 50*a, height - 18);
					}
					g.drawString(title, 400, 60);

				}
			};

			setContentPane(panel);
			//last two numbers below change the initial size of the graph.
			setBounds(20, 20, 1100, 500);
			setVisible(true);
		}
	}
}