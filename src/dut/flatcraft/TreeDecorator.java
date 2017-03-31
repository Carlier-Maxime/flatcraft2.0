package dut.flatcraft;

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
			if (x>0) {
				map.setAt(y+1, x-1, factory.createLeaves());
				map.setAt(y, x-1, factory.createLeaves());
			}
			map.setAt(y, x, factory.createLeaves());
			if (x+1<map.getWidth()) {
				map.setAt(y+1, x+1, factory.createLeaves());
				map.setAt(y, x+1, factory.createLeaves());
			}
		}
		return map;
	}

}
