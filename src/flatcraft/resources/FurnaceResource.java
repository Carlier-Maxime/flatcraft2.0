package flatcraft.resources;

import flatcraft.Main;
import flatcraft.player.Player;
import flatcraft.tools.ToolType;
import flatcraft.ui.Furnace;

import javax.swing.*;

public class FurnaceResource extends ExecutableResource{
    private static final JDialog dialog = initDialog();

    public FurnaceResource(String name, ImageIcon appearance, int hardness, ToolType toolType) {
        super(name, appearance, hardness, toolType);
    }

    @Override
    public ExecutableResourceInstance newInstance(JLabel label) {
        ExecutableResourceInstance instance = super.newInstance(label);
        instance.setRunnable(() -> {
            if (dialog.isVisible()){
                dialog.setVisible(false);
            } else {
                JFrame frame = Main.getFrame();
                dialog.setLocation(frame.getWidth()/2,frame.getHeight()/2);
                dialog.setVisible(true);
            }
        });
        return instance;
    }

    @Override
    public ExecutableResourceInstance newInstance() {
        return newInstance(new JLabel());
    }

    private static JDialog initDialog(){
        JDialog dialog = new JDialog(Main.getFrame(),"Furnace");
        dialog.add(new Furnace(Player.instance()));
        dialog.pack();
        return dialog;
    }
}
