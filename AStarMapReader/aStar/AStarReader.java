package aStar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AStarReader {
	//Initialize map variables
	static int mapHeight, mapWidth;
	static int largeMap [][],startX=0,startY=0,goalX,goalY;

	public static void main(String[] args) throws IOException {
		//Reading properties from constants file
		Constants con= new Constants();
		PathFinder pf = new PathFinder();
		con.getConstants();
		mapHeight=con.mapHt;
		mapWidth = con.mapWd;
		goalX=mapWidth-1;
		goalY=mapHeight-1;
		readMap(args[0]);//Reading the map
		List<Node> bestPath = pf.findPath(startX,startY,goalX,goalY,largeMap);
		printBestPath(bestPath);//Printing the path
	}
	/*
	 * Reads the text file and generates map
	 */
	public static void readMap(String filename){

		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("Error : Input file does not exist !!" + '\n' +"location :" + filename);
			return;
		}
		if (!(file.isFile() && file.canRead())) {
			System.out.println(file.getName() + " Erro : File cannot be read !!");
			return;
		}
		try {
			largeMap = new int [mapHeight][mapWidth];
			FileInputStream fis = new FileInputStream(file);
			char curPos;
			int row=0,col=0;
			while (fis.available() > 0) {
				curPos = (char) fis.read();
				if (curPos == '\n'){
					++row;
					col=0;
				}
				switch (curPos)
				{
				case '@':
					largeMap[row][col] = 8;
					startX = row;
					startY = col;
					break;
				case 'X':
					largeMap[row][col] = 9;
					goalX = row;
					goalY = col;
					break;
				case '.':
					largeMap[row][col] = 1; 
					break;
				case '*':
					largeMap[row][col] = 2; 
					break;
				case '^':
					largeMap[row][col] = 3; 
					break;
				case '~':
					largeMap[row][col] = 0; 
					break;
				default:
					continue;
				}
				++col;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/*
	 * Prints the map with Astar path indicated by "#" and creates an output text file with the Best path
	 */
	public static void printBestPath(List<Node> bestPath) throws IOException{

		BufferedWriter output = null;

		boolean nodePass = false;
		try {
			File file = new File("bestPathOutput.txt");
			output = new BufferedWriter(new FileWriter(file));
			for(int row=0;row<=goalY;row++){
				for(int col=0;col<=goalX;col++){
					nodePass = checkNode(bestPath,row,col);
					if(nodePass){
						//System.out.print('#');
						output.write("#");
					}
					else
					{
						switch (largeMap[row][col])
						{
						case 8:
							//System.out.print('#');
							output.write("#");
							break;
						case 9:
							//System.out.print('X');
							output.write("X");
							break;
						case 1:
							//System.out.print('.');
							output.write(".");
							break;
						case 2:
							//System.out.print('*');
							output.write("*"); 
							break;
						case 3:
							//System.out.print('^');
							output.write("^"); 
							break;
						case 0:
							//System.out.print('~');
							output.write("~"); 
							break;
						default:
							continue;
						}//switch
					}
				}//col
				//System.out.println();
				output.write("\n");
			}//row
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			output.close();
		}
	}//printBestPath

	/*
	 * Checks  if  the node belongs to best path 
	 */
	private static boolean checkNode(List<Node> bestPath, int row, int col) {
		for(Node n : bestPath){
			if(row == n.x && col == n.y)
				return true;
		}
		return false;
	}//checkNode
}

