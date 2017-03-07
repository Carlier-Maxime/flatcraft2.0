package dut.flatcraft;

import java.util.Random;

public class TreeDecorator implements MapGenerator {

	private int nbTree;
	private int maxHeight;
	private MapGenerator decorated;

	public TreeDecorator(MapGenerator decorated, int nbTree, int maxHeight){
		this.decorated = decorated;
		this.nbTree = nbTree;
		this.maxHeight = maxHeight;
	}
	
	@Override
	public GameMap generate(int width, int height, CellFactory factory) {
		GameMap map = decorated.generate(width, height, factory);
		for (int i = 0;i<nbTree;i++) {
			int x = RAND.nextInt(width);
			int y = height/2-1;
			int treeHeight = RAND.nextInt(maxHeight)+1;
			for (int j=0;j<treeHeight;j++) {
				map.setAt(y--, x, factory.createTree());
			}
		}
		return map;
	}

}
