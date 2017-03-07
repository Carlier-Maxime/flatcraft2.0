package dut.flatcraft;

import java.util.Random;

public class ResourceCellFactory implements CellFactory {

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
			return MineUtils.getResourceByName("junglegrass").newInstance();
		}
		if (RAND.nextInt(10) < 2) {
			return MineUtils.getResourceByName("water").newInstance();
		}
		return MineUtils.getResourceByName("grass").newInstance();
	}

	@Override
	public Cell createSoil() {
		if (RAND.nextInt(10) < 7) {
			return MineUtils.getResourceByName("dirt").newInstance();
		}
		if (RAND.nextInt(100) < 10) {
			return MineUtils.getResourceByName("coal").newInstance();
		}
		if (RAND.nextInt(100) < 5) {
			return MineUtils.getResourceByName("gold").newInstance();
		}
		if (RAND.nextInt(100) < 5) {
			return MineUtils.getResourceByName("iron").newInstance();
		}
		return MineUtils.getResourceByName("stone").newInstance();
	}
	
	@Override
	public Cell createTree() {
		return MineUtils.getResourceByName("tree").newInstance();
	}

}
