# Trucs et actuces

## Gestion des images

```java
    /**
     * Create a scaled up version of the original icon, to have a MineCraft
     * effect.
     * 
     * @param imageName
     *            the name of the texture file.
     * @return an ImageIcon scaled up to 40x40.
     */
    public static ImageIcon scaled(String imageName) {
        try {
            return new ImageIcon(ImageIO.read(MineUtils.class.getResource(imageName)).getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE,
                    Image.SCALE_DEFAULT));
        } catch (IOException e) {
            return new ImageIcon();
        }
    }

    /**
     * Create a new scaled up version of the original icon, over an already
     * scaled image (e.g. STONE).
     * 
     * @param background
     *            a scaled up background image
     * @param imageName
     *            the new image to put on top of the background.
     * @return an image consisting of imageName with the given background.
     */
    public static ImageIcon overlay(ImageIcon background, String imageName) {
        try {
            Image foreground = ImageIO.read(MineUtils.class.getResource(imageName)).getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE,
                    Image.SCALE_DEFAULT);
            BufferedImage merged = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
            Graphics g = merged.getGraphics();
            g.drawImage(background.getImage(), 0, 0, null);
            g.drawImage(foreground, 0, 0, null);
            return new ImageIcon(merged);
        } catch (IOException e) {
            return new ImageIcon();
        }
    }
```

## Synchronisation des dépôts

Déclarer le dépot commun à tous les étudiants de DUT2 :

```shell
git remote add upstream https://forge.univ-artois.fr/dut2017/flatcraft.git
```

Mettre à jour son dépôt à partir de dépôt commun :

```shell
git pull upstream master
```
