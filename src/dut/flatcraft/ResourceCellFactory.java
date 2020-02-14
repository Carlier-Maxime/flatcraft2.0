package dut.flatcraft;

import java.util.Random;

public class ResourceCellFactory implements CellFactory {

	public static final Random RAND = new Random();

	@Override
	public Cell createSky() {
		if (RAND.nextInt(10) < 1) {
			return new EmptyCell(MineUtils.getImage("cloud"));
		}
		return new EmptyCell(MineUtils.getImage("ice"));
	}

	@Override
	public Cell createGrass() {
		if (RAND.nextInt(10) < 1) {
			return createCell("junglegrass");
		}
		if (RAND.nextInt(10) < 2) {
			return createCell("water");
		}
		return createCell("grass");
	}

	@Override
	public Cell createSoil() {
		if (RAND.nextInt(10) < 7) {
			return createCell("dirt");
		}
		if (RAND.nextInt(100) < 10) {
			return createCell("coal");
		}
		if (RAND.nextInt(100) < 5) {
			return createCell("gold");
		}
		if (RAND.nextInt(100) < 5) {
			return createCell("iron");
		}
		if (RAND.nextInt(100) < 5) {
			return createCell("copper");
		}
		if (RAND.nextInt(100) < 3) {
			return createCell("ladder");
		}
		if (RAND.nextInt(100) < 1) {
			return createCell("chest");
		}
		return createCell("stone");
	}

	@Override
	public Cell createTree() {
		return createCell("tree");
	}

	@Override
	public Cell createLeaves() {
		return createCell("leaves");
	}

}
