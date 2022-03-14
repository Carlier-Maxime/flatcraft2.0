package flatcraft;

import java.util.Random;

/**
 * A simple and basic way to implement the CellFactory.
 */
public class ResourceCellFactory implements CellFactory {

	public static final Random RAND = new Random();

	@Override
	public Cell createSky() {
		if (RAND.nextInt(10) < 1) {
			return new EmptyCell(MineUtils.getImage("cloud"));
		}
		return new EmptyCell(MineUtils.getImage("sky"));
	}

	@Override
	public Cell createEmpty() {
		return new EmptyCell(MineUtils.getImage("air"));
	}

	@Override
	public Cell createGrass(boolean empty) {
		if (RAND.nextInt(10) < 1) {
			if (empty) return createSky();
			return createCell("junglegrass");
		}
		if (RAND.nextInt(10) < 2) {
			if (empty) return new EmptyCell(MineUtils.getImage("grass",true));
			return createCell("water");
		}
		if (empty) return new EmptyCell(MineUtils.getImage("grass",true));
		return createCell("grass");
	}

	@Override
	public Cell createSoil(boolean empty) {
		if (RAND.nextInt(10) < 7) {
			if (empty) return new EmptyCell(MineUtils.getImage("dirt",true));
			return createCell("dirt");
		}
		if (RAND.nextInt(100) < 10) {
			if (empty) return new EmptyCell(MineUtils.getImage("stone",true));
			return createCell("coal");
		}
		if (RAND.nextInt(100) < 5) {
			if (empty) return new EmptyCell(MineUtils.getImage("stone",true));
			return createCell("gold");
		}
		if (RAND.nextInt(100) < 5) {
			if (empty) return new EmptyCell(MineUtils.getImage("stone",true));
			return createCell("iron");
		}
		if (RAND.nextInt(100) < 5) {
			if (empty) return new EmptyCell(MineUtils.getImage("stone",true));
			return createCell("copper");
		}
		if (RAND.nextInt(100) < 3) {
			if (empty) return new EmptyCell(MineUtils.getImage("stone",true));
			return createCell("ladder");
		}
		if (RAND.nextInt(100) < 1) {
			if (empty) return new EmptyCell(MineUtils.getImage("stone",true));
			return createCell("chest");
		}
		if (empty) return new EmptyCell(MineUtils.getImage("stone",true));
		return createCell("stone");
	}

	@Override
	public Cell createTree() {
		//if (empty) return new EmptyCell(MineUtils.getImage("tree"));
		return createCell("tree");
	}

	@Override
	public Cell createLeaves() {
		//if (empty) return new EmptyCell(MineUtils.getImage("leaves"));
		return createCell("leaves");
	}

}
