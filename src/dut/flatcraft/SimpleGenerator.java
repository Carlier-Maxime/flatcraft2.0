package dut.flatcraft;

public class SimpleGenerator implements MapGenerator {

	@Override
	public GameMap generate(int width, int heigh,CellFactory factory) {
		SimpleGameMap map = new SimpleGameMap(width, heigh);
		int halfHeigh = heigh / 2;
		for (int i = 0; i < halfHeigh; i++) {
			for (int j = 0; j < width; j++) {
				map.setAt(i, j,factory.createSky());
			}
		}
		for (int j = 0; j < width; j++) {
			map.setAt(halfHeigh,j,factory.createGrass());

		}
		for (int i = halfHeigh + 1; i < heigh; i++) {
			for (int j = 0; j < width; j++) {
				map.setAt(i, j,factory.createSoil());
			}
		}
		return map;
	}
	
}
