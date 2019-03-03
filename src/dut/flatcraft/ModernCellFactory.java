package dut.flatcraft;

import java.util.Random;

public class ModernCellFactory implements CellFactory {
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
			return new NormalCell(MineUtils.getImage("junglegrass"));
		}
		if (RAND.nextInt(10) < 2) {
			return new NormalCell(MineUtils.getImage("water"));
		}
		return new NormalCell(MineUtils.getImage("grass"));
	}

	@Override
	public Cell createSoil() {
		if (RAND.nextInt(10) < 7) {
			return new NormalCell(MineUtils.getImage("dirt"));
		}
		return new NormalCell(MineUtils.getImage("stone"));
	}
	
	@Override
	public Cell createTree() {
		return new NormalCell(MineUtils.getImage("tree"));
	}

	@Override
	public Cell createLeaves() {
		return new NormalCell(MineUtils.getImage("leaves_simple"));
	}

}
