package dut.flatcraft;

import java.util.Random;

public class ModernCellFactory implements CellFactory {
	public static final Random RAND = new Random();

	@Override
	public Cell createSky() {
		if (RAND.nextInt(10) < 1) {
			return new EmptyCell(MineUtils.CLOUD);
		}
		return new EmptyCell(MineUtils.ICE);
	}

	@Override
	public Cell createGrass() {
		if (RAND.nextInt(10) < 1) {
			return new NormalCell(MineUtils.JUNGLEGRASS);
		}
		if (RAND.nextInt(10) < 2) {
			return new NormalCell(MineUtils.WATER);
		}
		return new NormalCell(MineUtils.GRASS);
	}

	@Override
	public Cell createSoil() {
		if (RAND.nextInt(10) < 7) {
			return new NormalCell(MineUtils.DIRT);
		}
		return new NormalCell(MineUtils.STONE);
	}
	
	@Override
	public Cell createTree() {
		return new NormalCell(MineUtils.TREE);
	}

}
