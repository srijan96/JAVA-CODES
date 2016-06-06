import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
class Point
{
	public int x;
	public int y;
	Point(int x,int y){ this.x = x;this.y = y;	}
}
class Edge
{
	public int s;
	public int e;
	Edge(int x,int y){	s = x;e = y;	}
}
public class GraphGenNDraw extends JPanel
{
	private static int numNodes = 10;
	private static int numEdges;
	private static int maxDegree = 4;
	private static Edge edges[];
	public static void main(String args[])
	{
		generate();
		JFrame f = new JFrame();
		f.setSize(400, 450);
		f.add(new GraphGenNDraw());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	private static void generate()
	{
		int degree[] = new int[numNodes];
		int sum = 0;
		for(int i=0;i<numNodes;i++)
		{
			int d = rand();
			sum += d;
			degree[i] = d;
		}
		if(sum%2 != 0)
		{
			sum++;
			degree[rand()]++;
		}
		edges = new Edge[sum/2];
		numEdges = sum/2;
		for(int i=0;i<numNodes;i++)
			System.out.println("Degree["+i+"] = "+degree[i]);
		int e = 0;
		while(sum > 0)
		{
			int a = (int) (numNodes * Math.random());
			int b = (int) (numNodes * Math.random());			
			if(a==numNodes || b==numNodes)	continue;
			if(degree[a]==0 || degree[b]==0) continue;
			degree[a]--;
			degree[b]--;
			System.out.println(a+"-->"+b);
			sum -= 2;
			edges[e] = new Edge(a,b);
			e++;
		}
	}
	private static int rand()
	{
		double cycleLength = 0;
		int i;
		for(i=0;i<maxDegree;i++)	cycleLength += pdf(i);
		double arc = Math.random() * cycleLength;
		double sum = pdf(-1);
		for(i=0;i<maxDegree && sum < arc;i++)	sum += pdf(i);
		if(i>=maxDegree)	i = maxDegree - 1;
		return i;
	}
	private static double pdf(int x)
	{
		double mu = (maxDegree-1)/2;
		double sigma = mu/2;
		double t = -1 * (x-mu)*(x-mu)/(sigma*sigma);
		return Math.exp(t);
	}
	private static int n = numNodes;
	private static int r = 120;
	private static Point points[] = new Point[n];
	public void paint(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		int diameter = 10;
		int x=0,y=0;
		g2d.setPaint(new Color(10,200,150));
		for(int i=0;i<n;i++)
		{
			x = (int) (r * Math.cos(Math.toRadians(i*360/n)));
			y = (int) (r * Math.sin(Math.toRadians(i*360/n)));	
			x = x + 200;
			y =  200 - y;
			g2d.fillOval(x ,y , diameter,diameter);	
			points[i] = new Point(x,y);
		}
		for(int i=0;i<numEdges;i++)
			g2d.drawLine(points[edges[i].s].x,points[edges[i].s].y,points[edges[i].e].x,points[edges[i].e].y);
	}
}
