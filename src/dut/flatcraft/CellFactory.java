package dut.flatcraft;

import fr.univartois.migl.utils.DesignPattern;

@DesignPattern(name = "Abstract Factory")
public interface CellFactory {

	Cell createSky();

	Cell createGrass();

	Cell createSoil();

	Cell createTree();

	Cell createLeaves();
}
