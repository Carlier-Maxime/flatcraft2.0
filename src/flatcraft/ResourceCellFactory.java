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
			if (empty) return new EmptyCell(MineUtils.getImage("junglegrass"));
			return createCell("junglegrass");
		}
		if (RAND.nextInt(10) < 2) {
			if (empty) return new EmptyCell(MineUtils.getImage("water"));
			return createCell("water");
		}
		if (empty) return new EmptyCell(MineUtils.getImage("grass"));
		return createCell("grass");
	}

	@Override
	public Cell createSoil(boolean empty) {
		if (RAND.nextInt(10) < 7) {
			if (empty) return new EmptyCell(MineUtils.getImage("dirt"));
			return createCell("dirt");
		}
		if (RAND.nextInt(100) < 10) {
			if (empty) return new EmptyCell(MineUtils.getImage("coal"));
			return createCell("coal");
		}
		if (RAND.nextInt(100) < 5) {
			if (empty) return new EmptyCell(MineUtils.getImage("gold"));
			return createCell("gold");
		}
		if (RAND.nextInt(100) < 5) {
			if (empty) return new EmptyCell(MineUtils.getImage("iron"));
			return createCell("iron");
		}
		if (RAND.nextInt(100) < 5) {
			if (empty) return new EmptyCell(MineUtils.getImage("copper"));
			return createCell("copper");
		}
		if (RAND.nextInt(100) < 3) {
			if (empty) return new EmptyCell(MineUtils.getImage("ladder"));
			return createCell("ladder");
		}
		if (RAND.nextInt(100) < 1) {
			if (empty) return new EmptyCell(MineUtils.getImage("chest"));
			return createCell("chest");
		}
		if (empty) return new EmptyCell(MineUtils.getImage("stone"));
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
