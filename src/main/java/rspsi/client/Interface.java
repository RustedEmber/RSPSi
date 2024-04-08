package rspsi.client;

import java.awt.Color;

import com.jagex.cache.Archive;
import rspsi.Main;
import rspsi.client.animable.Model;
import rspsi.client.config.EntityDef;
import rspsi.client.config.ItemDef;
import rspsi.client.graphics.Sprite;
import rspsi.client.graphics.TextDrawingArea;
import rspsi.client.stream.Stream;

public class Interface {

    public static void unpack(Stream stream, TextDrawingArea textDrawingAreas[], com.jagex.cache.Archive archive_1) {
        aMRUNodes_sprite = new MRUNodes(50000);
        textDrawingAreas_rsi = textDrawingAreas;
        archive_rsi = archive_1;

        int j = stream.readUnsignedWord();
        interfaceCache = new Interface[j + 22000];
        unpack(stream);

//        try {
//	        java.io.FileWriter fw = new java.io.FileWriter(new java.io.File("nullDump.txt"));
//	        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
//	        String line = "";
//			for (int abc = 0/* + 18786*/; abc < interfaceCache.length; abc++)
//				if (rspsi.Rspsi.get(abc) == null)
//					line += abc + ",";
//
//			for (String s : line.split(",")) {
//        		bw.write(s);
//        		bw.newLine();
//	        }
//			bw.flush();
//			bw.close();
//
//        } catch (Exception e) {
//        	e.printStackTrace();
//        }
//        new Rspsi(textDrawingAreas_rsi, streamLoader_rsi);
        
    }

    public static void unpack(Stream stream) {
        int i = -1;
        while (stream.currentOffset < stream.buffer.length) {
            int k = stream.readUnsignedWord();
            if (k == 65535) {
                i = stream.readUnsignedWord();
                k = stream.readUnsignedWord();
            }
            Interface rsi = interfaceCache[k] = new Interface();
            rsi.id = k;
            rsi.parentId = i;
            rsi.type = stream.readUnsignedByte();
            rsi.actionType = stream.readUnsignedByte();
            rsi.contentType = stream.readUnsignedWord();
            rsi.width = stream.readUnsignedWord();
            rsi.height = stream.readUnsignedWord();
            rsi.alpha = (byte) stream.readUnsignedByte();
            rsi.trigger = stream.readUnsignedByte();
            if (rsi.trigger != 0)
                rsi.trigger = (rsi.trigger - 1 << 8) + stream.readUnsignedByte();
            else
                rsi.trigger = -1;
            int i1 = stream.readUnsignedByte();
            if (i1 > 0) {
                rsi.valueCompareType = new int[i1];
                rsi.requiredValues = new int[i1];
                for (int j1 = 0; j1 < i1; j1++) {
                    rsi.valueCompareType[j1] = stream.readUnsignedByte();
                    rsi.requiredValues[j1] = stream.readUnsignedWord();
                }

            }
            int k1 = stream.readUnsignedByte();
            if (k1 > 0) {
                rsi.valueIndexArray = new int[k1][];
                for (int l1 = 0; l1 < k1; l1++) {
                    int i3 = stream.readUnsignedWord();
                    rsi.valueIndexArray[l1] = new int[i3];
                    for (int l4 = 0; l4 < i3; l4++)
                        rsi.valueIndexArray[l1][l4] = stream.readUnsignedWord();

                }

            }
            if (rsi.type == 0) {
                rsi.scrollMax = stream.readUnsignedWord();
                rsi.triggered = stream.readUnsignedByte() == 1;
                int i2 = stream.readUnsignedWord();
                rsi.children = new int[i2];
                rsi.childX = new int[i2];
                rsi.childY = new int[i2];
                for (int j3 = 0; j3 < i2; j3++) {
                    rsi.children[j3] = stream.readUnsignedWord();
                    rsi.childX[j3] = stream.readSignedWord();
                    rsi.childY[j3] = stream.readSignedWord();
                }

            }
            if (rsi.type == 1) {
                stream.readUnsignedWord();
                stream.readUnsignedByte();
            }
            if (rsi.type == 2) {
                rsi.inv = new int[rsi.width * rsi.height];
                rsi.invStackSizes = new int[rsi.width * rsi.height];
                rsi.invItemsCanBeSwapped = stream.readUnsignedByte() == 1;
                rsi.inventoryInterface = stream.readUnsignedByte() == 1;
                rsi.usableItemInterface = stream.readUnsignedByte() == 1;
                rsi.invSwapDeletesTargetSlot = stream.readUnsignedByte() == 1;
                rsi.invSpritePadX = stream.readUnsignedByte();
                rsi.invSpritePadY = stream.readUnsignedByte();
                rsi.spritesX = new int[20];
                rsi.spritesY = new int[20];
                rsi.sprites = new Sprite[20];
                rsi.spritesDir = new String[20]; //custom
                rsi.spritesId = new int[20]; //custom
                for (int j2 = 0; j2 < 20; j2++) {
                    int k3 = stream.readUnsignedByte();
                    if (k3 == 1) {
                        rsi.spritesX[j2] = stream.readSignedWord();
                        rsi.spritesY[j2] = stream.readSignedWord();
                        String s1 = stream.readString();
                        if (archive_rsi != null && s1.length() > 0) {
                            int i5 = s1.lastIndexOf(",");
                            rsi.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), archive_rsi, s1.substring(0, i5));
                            rsi.spritesDir[j2] = s1.substring(0, i5); //custom
                            rsi.spritesId[j2] = Integer.parseInt(s1.substring(i5 + 1)); //custom
                        }
                    }
                }

                rsi.actions = new String[5];
                for (int l3 = 0; l3 < 5; l3++) {
                    rsi.actions[l3] = stream.readString();
                    if (rsi.actions[l3].length() == 0)
                        rsi.actions[l3] = null;
                }

            }
            if (rsi.type == 3)
                rsi.filled = stream.readUnsignedByte() == 1;
            if (rsi.type == 4 || rsi.type == 1) {
                rsi.centered = stream.readUnsignedByte() == 1;
                int k2 = stream.readUnsignedByte();
                if (textDrawingAreas_rsi != null)
                    rsi.fontStyle = textDrawingAreas_rsi[k2];
                rsi.fontId = k2; //custom
                rsi.shadowed = stream.readUnsignedByte() == 1;
            }
            if (rsi.type == 4) {
                rsi.disabledText = stream.readString();
                rsi.enabledText = stream.readString();
            }
            if (rsi.type == 1 || rsi.type == 3 || rsi.type == 4)
                rsi.disabledColor = stream.readDWord();
            if (rsi.type == 3 || rsi.type == 4) {
                rsi.enabledColor = stream.readDWord();
                rsi.mouseOverDisabledColor = stream.readDWord();
                rsi.mouseOverEnabledColor = stream.readDWord();
            }
            if (rsi.type == 5) {
                String s = stream.readString();
                if (archive_rsi != null && s.length() > 0) {
                    int i4 = s.lastIndexOf(",");
                    rsi.disabledSprite = method207(Integer.parseInt(s.substring(i4 + 1)), archive_rsi, s.substring(0, i4));
                    rsi.disabledSpriteDir = s.substring(0, i4); //custom
                    rsi.disabledSpriteId = Integer.parseInt(s.substring(i4 + 1)); //custom
                }
                s = stream.readString();
                if (archive_rsi != null && s.length() > 0) {
                    int j4 = s.lastIndexOf(",");
                    rsi.enabledSprite = method207(Integer.parseInt(s.substring(j4 + 1)), archive_rsi, s.substring(0, j4));
                    rsi.enabledSpriteDir = s.substring(0, j4); //custom
                    rsi.enabledSpriteId = Integer.parseInt(s.substring(j4 + 1)); //custom
                }
            }
            if (rsi.type == 6) {
                int l = stream.readUnsignedByte();
                if (l != 0) {
                    rsi.disabledMediaType = 1;
                    rsi.disabledMediaId = (l - 1 << 8) + stream.readUnsignedByte();
                }
                l = stream.readUnsignedByte();
                if (l != 0) {
                    rsi.enabledMediaType = 1;
                    rsi.enabledMediaId = (l - 1 << 8) + stream.readUnsignedByte();
                }
                l = stream.readUnsignedByte();
                if (l != 0)
                    rsi.disabledAnimation = (l - 1 << 8) + stream.readUnsignedByte();
                else
                    rsi.disabledAnimation = -1;
                l = stream.readUnsignedByte();
                if (l != 0)
                    rsi.enabledAnimation = (l - 1 << 8) + stream.readUnsignedByte();
                else
                    rsi.enabledAnimation = -1;
                rsi.modelZoom = stream.readUnsignedWord();
                rsi.verticalRotation = stream.readUnsignedWord();
                rsi.horizontalRotation = stream.readUnsignedWord();
            }
            if (rsi.type == 7) {
                rsi.inv = new int[rsi.width * rsi.height];
                rsi.invStackSizes = new int[rsi.width * rsi.height];
                rsi.centered = stream.readUnsignedByte() == 1;
                int l2 = stream.readUnsignedByte();
                if (textDrawingAreas_rsi != null)
                    rsi.fontStyle = textDrawingAreas_rsi[l2];
                rsi.fontId = l2; //custom
                rsi.shadowed = stream.readUnsignedByte() == 1;
                rsi.disabledColor = stream.readDWord();
                rsi.invSpritePadX = stream.readSignedWord();
                rsi.invSpritePadY = stream.readSignedWord();
                rsi.inventoryInterface = stream.readUnsignedByte() == 1;
                rsi.actions = new String[5];
                for (int k4 = 0; k4 < 5; k4++) {
                    rsi.actions[k4] = stream.readString();
                    if (rsi.actions[k4].length() == 0)
                        rsi.actions[k4] = null;
                }

            }
            if (rsi.actionType == 2 || rsi.type == 2) {
                rsi.selectedActionName = stream.readString();
                rsi.spellName = stream.readString();
                rsi.spellUsableOn = stream.readUnsignedWord();
            }

            if (rsi.type == 8)
                rsi.disabledText = stream.readString();

            if (rsi.actionType == 1 || rsi.actionType == 4 || rsi.actionType == 5 || rsi.actionType == 6) {
                rsi.tooltip = stream.readString();
                if (rsi.tooltip.length() == 0) {
                    if (rsi.actionType == 1)
                        rsi.tooltip = "Ok";
                    if (rsi.actionType == 4)
                        rsi.tooltip = "Select";
                    if (rsi.actionType == 5)
                        rsi.tooltip = "Select";
                    if (rsi.actionType == 6)
                        rsi.tooltip = "Continue";
                }
            }

        }
    }

	public static Sprite customSpriteLoader(String s, int id) {
		long l = (TextClass.method585(s) << 8) + (long) id;
		Sprite sprite = (Sprite) aMRUNodes_sprite.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		} try {
			sprite = new Sprite(".file_store/media/custom/" + s + ".png");
			aMRUNodes_sprite.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

    private Model method206(int i, int j) {
        Model model = (Model) aMRUNodes_model.insertFromCache((i << 16) + j);
        if (model != null)
            return model;
        if (i == 1)
            model = Model.method462(j);
        if (i == 2)
            model = EntityDef.forID(j).method160();
        if (i == 3)
            model = client.myPlayer.method453();
        if (i == 4)
            model = ItemDef.forID(j).method202(50);
        if (i == 5)
            model = null;
        if (model != null)
            aMRUNodes_model.removeFromCache(model, (i << 16) + j);
        return model;
    }

    protected static Sprite method207(int i, com.jagex.cache.Archive archive, String s) {
        long l = (TextClass.method585(s) << 8) + (long) i;
        Sprite sprite = (Sprite) aMRUNodes_sprite.insertFromCache(l);
        if (sprite != null)
            return sprite;
        try {
            sprite = new Sprite(archive, s, i);
            aMRUNodes_sprite.removeFromCache(sprite, l);
        } catch (OutOfMemoryError ex) {
        	Main.workspace.pushMessage("Sprite(s) missing from cache!", 1, "[RSPSi]");
        	Main.workspace.pushMessage("You can use your own media.jag under the options menu.", 1, "[RSPSi]");
        	return null;
        } catch (Exception _ex) {
            return null;
        }
        return sprite;
    }

    public static void method208(boolean flag, Model model) {
        int i = 0;//was parameter
        int j = 5;//was parameter
        if (flag)
            return;
        aMRUNodes_model.unlinkAll();
        if (model != null && j != 4)
            aMRUNodes_model.removeFromCache(model, (j << 16) + i);
    }

    public Model method209(int j, int k, boolean flag) {
        Model model;
        if (flag)
            model = method206(enabledMediaType, enabledMediaId);
        else
            model = method206(disabledMediaType, disabledMediaId);
        if (model == null)
            return null;
        if (k == -1 && j == -1 && model.anIntArray1640 == null)
            return model;
        Model model_1 = new Model(true, Class36.method532(k) & Class36.method532(j), false, model);
        if (k != -1 || j != -1)
            model_1.method469();
        if (k != -1)
            model_1.method470(k);
        if (j != -1)
            model_1.method470(j);
        model_1.method479(64, 768, -50, -10, -50, true);
        return model_1;
    }

    public void swapInventoryItems(int i, int j) {
        int k = inv[i];
        inv[i] = inv[j];
        inv[j] = k;
        k = invStackSizes[i];
        invStackSizes[i] = invStackSizes[j];
        invStackSizes[j] = k;
    }

    public Interface() {
        id = -1;
        parentId = -1;
        type = 0;
        actionType = 0;
        contentType = 0;
        width = 0;
        height = 0;
        alpha = (byte) 0;
        trigger = -1;
    }

    public static Interface interfaceCache[], lastCache[];
    public static TextDrawingArea[] textDrawingAreas_rsi;
    public static Archive archive_rsi;
    public static MRUNodes aMRUNodes_sprite;
    public static final MRUNodes aMRUNodes_model = new MRUNodes(30);
    public int anInt208;
    public int animationFrame;
    public int scrollPosition;
    public int xDrawOffset;
    public int yDrawOffset;

    public int id;
    public int parentId;
    public int type;
    public int actionType;
    public int contentType;
    public int width;
    public int height;
    public int trigger;
    public String tooltip;
    public int valueCompareType[];
    public int requiredValues[];
    public int valueIndexArray[][];
    public String actions[];

    public int scrollMax;
    public boolean triggered;
    public int children[];
    public int childX[];
    public int childY[];

    public int inv[];
    public int invStackSizes[];
    public boolean inventoryInterface;
    public boolean invItemsCanBeSwapped;
    public boolean usableItemInterface;
    public boolean invSwapDeletesTargetSlot;
    public int invSpritePadX;
    public int invSpritePadY;
    public int spritesX[];
    public int spritesY[];
    public Sprite sprites[];
    public String spritesDir[];
    public int spritesId[];
    public String selectedActionName;
    public String spellName;
    public int spellUsableOn;

    public boolean filled;
    public byte alpha;

    public String disabledText;
    public String enabledText;
    public TextDrawingArea fontStyle;
    public int fontId; //custom
    public int disabledColor;
    public int enabledColor;
    public int mouseOverDisabledColor;
    public int mouseOverEnabledColor;
    public boolean centered;
    public boolean shadowed;

    public Sprite disabledSprite;
    public String disabledSpriteDir;
    public int disabledSpriteId;
    public Sprite enabledSprite;
    public String enabledSpriteDir;
    public int enabledSpriteId;

	public int disabledMediaType;
    public int disabledMediaId;
    public int enabledMediaType;
    public int enabledMediaId;
    public int disabledAnimation;
    public int enabledAnimation;
    public int modelZoom;
    public int verticalRotation;
    public int horizontalRotation;

    public int getId() {
		return id;
	}

//	public void setId(int id) {
//		this.id = id;
//	}

	public int getParentId() {
		return parentId;
	}

//	public void setParentId(int parentId) {
//		this.parentId = parentId;
//	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTrigger() {
		return trigger;
	}

	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public int[] getValueCompareType() {
		return valueCompareType;
	}

	public void setValueCompareType(int[] valueCompareType) {
		this.valueCompareType = valueCompareType;
	}

	public int[] getRequiredValues() {
		return requiredValues;
	}

	public void setRequiredValues(int[] requiredValues) {
		this.requiredValues = requiredValues;
	}

	public int[][] getValueIndexArray() {
		return valueIndexArray;
	}

	public void setValueIndexArray(int[][] valueIndexArray) {
		this.valueIndexArray = valueIndexArray;
	}

	public String[] getActions() {
		return actions;
	}

	public void setActions(String[] actions) {
		this.actions = actions;
	}

	public int getScrollMax() {
		return scrollMax;
	}

	public void setScrollMax(int scrollMax) {
		this.scrollMax = scrollMax;
	}

	public boolean isTriggered() {
		return triggered;
	}

	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	public int[] getChildren() {
		return children;
	}

	public void setChildren(int[] children) {
		this.children = children;
	}

	public int[] getChildX() {
		return childX;
	}

	public void setChildX(int[] childX) {
		this.childX = childX;
	}

	public int[] getChildY() {
		return childY;
	}

	public void setChildY(int[] childY) {
		this.childY = childY;
	}

	public int[] getInv() {
		return inv;
	}

	public void setInv(int[] inv) {
		this.inv = inv;
	}

	public int[] getInvStackSizes() {
		return invStackSizes;
	}

	public void setInvStackSizes(int[] invStackSizes) {
		this.invStackSizes = invStackSizes;
	}

	public boolean isInventoryInterface() {
		return inventoryInterface;
	}

	public void setInventoryInterface(boolean inventoryInterface) {
		this.inventoryInterface = inventoryInterface;
	}

	public boolean isInvItemsCanBeSwapped() {
		return invItemsCanBeSwapped;
	}

	public void setInvItemsCanBeSwapped(boolean invItemsCanBeSwapped) {
		this.invItemsCanBeSwapped = invItemsCanBeSwapped;
	}

	public boolean isUsableItemInterface() {
		return usableItemInterface;
	}

	public void setUsableItemInterface(boolean usableItemInterface) {
		this.usableItemInterface = usableItemInterface;
	}

	public boolean isInvSwapDeletesTargetSlot() {
		return invSwapDeletesTargetSlot;
	}

	public void setInvSwapDeletesTargetSlot(boolean invSwapDeletesTargetSlot) {
		this.invSwapDeletesTargetSlot = invSwapDeletesTargetSlot;
	}

	public int getInvSpritePadX() {
		return invSpritePadX;
	}

	public void setInvSpritePadX(int invSpritePadX) {
		this.invSpritePadX = invSpritePadX;
	}

	public int getInvSpritePadY() {
		return invSpritePadY;
	}

	public void setInvSpritePadY(int invSpritePadY) {
		this.invSpritePadY = invSpritePadY;
	}

	public int[] getSpritesX() {
		return spritesX;
	}

	public void setSpritesX(int[] spritesX) {
		this.spritesX = spritesX;
	}

	public int[] getSpritesY() {
		return spritesY;
	}

	public void setSpritesY(int[] spritesY) {
		this.spritesY = spritesY;
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}

	public String getSelectedActionName() {
		return selectedActionName;
	}

	public void setSelectedActionName(String selectedActionName) {
		this.selectedActionName = selectedActionName;
	}

	public String getSpellName() {
		return spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	public int getSpellUsableOn() {
		return spellUsableOn;
	}

	public void setSpellUsableOn(int spellUsableOn) {
		this.spellUsableOn = spellUsableOn;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public int getAlpha() {
		return (int) alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = (byte) alpha;
	}

	public String getDisabledText() {
		return disabledText;
	}

	public void setDisabledText(String disabledText) {
		this.disabledText = disabledText;
	}

	public String getEnabledText() {
		return enabledText;
	}

	public void setEnabledText(String enabledText) {
		this.enabledText = enabledText;
	}

//	public TextDrawingArea getFontStyle() {
//		return fontStyle;
//	}
//
//	public void setFontStyle(TextDrawingArea fontStyle) {
//		this.fontStyle = fontStyle;
//	}

	public int getFontId() {
		return fontId;
	}

	public void setFontId(int fontId) {
		this.fontId = fontId;
		this.fontStyle = textDrawingAreas_rsi[fontId];
	}

	public Color getDisabledColor() {
		return new Color(disabledColor);
	}

	public void setDisabledColor(Color disabledColor) {
		int color = disabledColor.getRGB();
		this.disabledColor = color == -16777216 ? 0 : color;
	}

	public Color getEnabledColor() {
		return new Color(enabledColor);
	}

	public void setEnabledColor(Color enabledColor) {
		int color = enabledColor.getRGB();
		this.enabledColor = color == -16777216 ? 0 : color;
	}

	public Color getMouseOverDisabledColor() {
		return new Color(mouseOverDisabledColor);
	}

	public void setMouseOverDisabledColor(Color mouseOverDisabledColor) {
		int color = mouseOverDisabledColor.getRGB();
		this.mouseOverDisabledColor = color == -16777216 ? 0 : color;
	}

	public Color getMouseOverEnabledColor() {
		return new Color(mouseOverEnabledColor);
	}

	public void setMouseOverEnabledColor(Color mouseOverEnabledColor) {
		int color = mouseOverEnabledColor.getRGB();
		this.mouseOverEnabledColor = color == -16777216 ? 0 : color;
	}

	public boolean isCentered() {
		return centered;
	}

	public void setCentered(boolean centered) {
		this.centered = centered;
	}

	public boolean isShadowed() {
		return shadowed;
	}

	public void setShadowed(boolean shadowed) {
		this.shadowed = shadowed;
	}

//	public Sprite getDisabledSprite() {
//		return disabledSprite;
//	}
//
//	public void setDisabledSprite(Sprite disabledSprite) {
//		this.disabledSprite = disabledSprite;
//	}

	public String getDisabledSpriteDir() {
		return disabledSpriteDir;
	}

	public void setDisabledSpriteDir(String disabledSpriteDir) {
		this.disabledSpriteDir = disabledSpriteDir;
		if (disabledSpriteDir != null && disabledSpriteId != -1)
			if (disabledSpriteDir.startsWith("[custom]"))
				this.disabledSprite = customSpriteLoader(disabledSpriteDir.replace("[custom]", ""), disabledSpriteId);
			else
				this.disabledSprite = method207(disabledSpriteId, archive_rsi, disabledSpriteDir);
	}

	public int getDisabledSpriteId() {
		return disabledSpriteId;
	}

	public void setDisabledSpriteId(int disabledSpriteId) {
		this.disabledSpriteId = disabledSpriteId;
		if (disabledSpriteDir != null && disabledSpriteId != -1)
			if (disabledSpriteDir.startsWith("[custom]"))
				this.disabledSprite = customSpriteLoader(disabledSpriteDir.replace("[custom]", ""), disabledSpriteId);
			else
				this.disabledSprite = method207(disabledSpriteId, archive_rsi, disabledSpriteDir);
	}

//	public Sprite getEnabledSprite() {
//		return enabledSprite;
//	}
//
//	public void setEnabledSprite(Sprite enabledSprite) {
//		this.enabledSprite = enabledSprite;
//	}

	public String getEnabledSpriteDir() {
		return enabledSpriteDir;
	}

	public void setEnabledSpriteDir(String enabledSpriteDir) {
		this.enabledSpriteDir = enabledSpriteDir;
		if (enabledSpriteDir != null && enabledSpriteId != -1)
			if (enabledSpriteDir.startsWith("[custom]"))
				this.enabledSprite = customSpriteLoader(enabledSpriteDir.replace("[custom]", ""), enabledSpriteId);
			else
				this.enabledSprite = method207(enabledSpriteId, archive_rsi, enabledSpriteDir);
	}

	public int getEnabledSpriteId() {
		return enabledSpriteId;
	}

	public void setEnabledSpriteId(int enabledSpriteId) {
		this.enabledSpriteId = enabledSpriteId;
		if (enabledSpriteDir != null && enabledSpriteId != -1)
			if (enabledSpriteDir.startsWith("[custom]"))
				this.enabledSprite = customSpriteLoader(enabledSpriteDir.replace("[custom]", ""), enabledSpriteId);
			else
				this.enabledSprite = method207(enabledSpriteId, archive_rsi, enabledSpriteDir);
	}

	public int getDisabledMediaType() {
		return disabledMediaType;
	}

	public void setDisabledMediaType(int disabledMediaType) {
		this.disabledMediaType = disabledMediaType;
	}

	public int getDisabledMediaId() {
		return disabledMediaId;
	}

	public void setDisabledMediaId(int disabledMediaId) {
		this.disabledMediaId = disabledMediaId;
	}

	public int getEnabledMediaType() {
		return enabledMediaType;
	}

	public void setEnabledMediaType(int enabledMediaType) {
		this.enabledMediaType = enabledMediaType;
	}

	public int getEnabledMediaId() {
		return enabledMediaId;
	}

	public void setEnabledMediaId(int enabledMediaId) {
		this.enabledMediaId = enabledMediaId;
	}

	public int getDisabledAnimation() {
		return disabledAnimation;
	}

	public void setDisabledAnimation(int disabledAnimation) {
		this.disabledAnimation = disabledAnimation;
	}

	public int getEnabledAnimation() {
		return enabledAnimation;
	}

	public void setEnabledAnimation(int enabledAnimation) {
		this.enabledAnimation = enabledAnimation;
	}

	public int getModelZoom() {
		return modelZoom;
	}

	public void setModelZoom(int modelZoom) {
		this.modelZoom = modelZoom;
	}

	public int getVerticalRotation() {
		if (verticalRotation > 2047)
			verticalRotation = 2047;
		return verticalRotation/* / 360*/;
	}

	public void setVerticalRotation(int verticalRotation) {
		this.verticalRotation = verticalRotation;
/*		while (verticalRotation > 359) {
			verticalRotation -= 360;
		}

		int rot = (2047 / 360) * verticalRotation;
		if (rot > -1 && rot < 2048)
			this.verticalRotation = rot;
		else {
			if (rot < 0)
				this.verticalRotation = -rot;
			if (rot > 2047)
				this.verticalRotation = rot - 2047;
		}*/
	}

	public int getHorizontalRotation() {
		if (horizontalRotation > 2047)
			horizontalRotation = 2047;
		return horizontalRotation/* / 360*/;
	}

	public void setHorizontalRotation(int horizontalRotation) {
		this.horizontalRotation = horizontalRotation;
		/*while (horizontalRotation > 359) {
			horizontalRotation -= 360;
		}

		int rot = (2047 / 360) * horizontalRotation;
		if (rot > 0 && rot < 2047)
			this.horizontalRotation = rot;
		else {
			if (rot < 0)
				this.horizontalRotation = -rot;
			if (rot > 2047)
				this.horizontalRotation = rot - 2047;
		}*/
	}

}
