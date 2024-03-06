/**d);d
 * @author Bend);jamin
 * RuneScape Private Server Interface
 * parent class for hard-coded interfaces.
 */

package rspsi;

import rspsi.client.*;
import rspsi.client.config.ItemDef;
import rspsi.client.graphics.Sprite;
import rspsi.client.graphics.TextDrawingArea;
import rspsi.client.stream.Stream;
import com.jagex.cache.Archive;
import rspsi.io.FileOperations;

public class Rspsi extends Interface {

//	public static TextDrawingArea[] tda;
//	public static StreamLoader streamLoader;

	public Rspsi(TextDrawingArea[] tda, Archive archive) {
//		Rspsi.tda = tda;
//		Rspsi.streamLoader = streamLoader;

		//insert interface executions here
//		setColors(7332, new int[] { 0xff00ff });
//		equipment();
		//sprite(1667, true, "staticons", 4);
		//model(1667, new int[] { 1 }, new int[] { 634 }, 500);
	}

//	public void equipment() {
//        /*addScreen(id);*/
//        create(18793, 0);
//        setBounds(18793, 512, 334);
//        /*addHoveredButton(id1, sprite1, id2, id3, sprite2, tooltip, width, height);*/
//        //addHoveredButton(18795, 4, 18796, 18797, 5, "Close", 21, 21);
//        create(18795, 0);
//        create(18796, 0);
//        create(18797, 0);
//        /*addInterfaceSprite(id, sprite1, sprite2);*/
//        //addInterfaceSprite(18794, 3, -1);
//        create(18794, 0);
//        /*text(int id, String text, int fontStyle, int[] colors)*/
//        text(18798, "Equip Your Character...", 2, new int[] { 0xff981f });
//        text(1673, "Attack bonus", 2, new int[] { 0xff981f });
//        text(1674, "Defence bonus", 2, new int[] { 0xff981f });
//        text(1675, "Stab: +120", 1, new int[] { 0xff981f });
//        text(1676, "Slash: +120", 1, new int[] { 0xff981f });
//        text(1677, "Crush: +120", 1, new int[] { 0xff981f });
//        text(1678, "Magic: +120", 1, new int[] { 0xff981f });
//        text(1679, "Range: +120", 1, new int[] { 0xff981f });
//        text(1680, "Stab: +120", 1, new int[] { 0xff981f });
//        text(1681, "Slash: +120", 1, new int[] { 0xff981f });
//        text(1682, "Crush: +120", 1, new int[] { 0xff981f });
//        text(1683, "Magic: +120", 1, new int[] { 0xff981f });
//        text(1684, "Range: +120", 1, new int[] { 0xff981f });
//        text(1685, "Other bonuses", 2, new int[] { 0xff981f });
//        text(1686, "Strength: +120", 1, new int[] { 0xff981f });
//        text(1687, "Prayer: +120", 1, new int[] { 0xff981f });
//        /*disabledColor(id, color);*/
//        get(184).disabledColor = 0xff981f;
//        /*addCharacter(id);*/
//        create(18799, 6);
//        RSInterface rsi = get(18799);
//        rsi.contentType = 328;
//        rsi.height = 168;
//        rsi.disabledAnimation = -1;
//        rsi.enabledAnimation = -1;
//        rsi.verticalRotation = 150;
//        rsi.horizontalRotation = 0;
//
//        int frame = 0;
//        rsi = get(18793);
//        totalChildren(45, rsi);
//        child(frame, 18794, 12, 20, rsi); frame++; //window
//        child(frame, 18795, 472, 27, rsi); frame++; //close
//        child(frame, 18796, 472, 27, rsi); frame++; //close_hover
//        child(frame, 18798, 23, 29, rsi); frame++; //equip your character...
//  
//        child(frame, 1673, 365, 71, rsi); frame++; //attack bonus
//        child(frame, 1674, 365, 163, rsi); frame++; //defence bonus
//        child(frame, 1675, 372, 85, rsi); frame++; //attack stab
//        child(frame, 1676, 372, 99, rsi); frame++; //attack slash
//        child(frame, 1677, 372, 113, rsi); frame++; //attack crush
//        child(frame, 1678, 372, 127, rsi); frame++; //attack mage
//        child(frame, 1679, 372, 141, rsi); frame++; //attack range
//        child(frame, 1680, 372, 177, rsi); frame++; //defence stab
//        child(frame, 1681, 372, 191, rsi); frame++; //defence slash
//        child(frame, 1682, 372, 205, rsi); frame++; //defence crush
//        child(frame, 1683, 372, 219, rsi); frame++; //defence mage
//        child(frame, 1684, 372, 233, rsi); frame++; //defence range
//        child(frame, 1685, 365, 253, rsi); frame++; //other bonuses
//        child(frame, 1686, 372, 267, rsi); frame++; //other strength
//        child(frame, 1687, 372, 281, rsi); frame++; //other prayer
//        child(frame, 184, 94, 286, rsi); frame++; //weight
//  
//        child(frame, 1645, 83, 106, rsi); frame++;
//        child(frame, 1646, 83, 135, rsi); frame++;
//        child(frame, 1647, 83, 172, rsi); frame++;
//        child(frame, 1648, 83, 213, rsi); frame++;
//        child(frame, 1649, 27, 185, rsi); frame++;
//        child(frame, 1650, 27, 221, rsi); frame++;
//        child(frame, 1651, 139, 185, rsi); frame++;
//        child(frame, 1652, 139, 221, rsi); frame++;
//        child(frame, 1653, 53, 148, rsi); frame++;
//        child(frame, 1654, 112, 148, rsi); frame++;
//        child(frame, 1655, 63, 109, rsi); frame++;
//        child(frame, 1656, 117, 108, rsi); frame++;
//        child(frame, 1657, 83, 71, rsi); frame++;
//        child(frame, 1658, 42, 110, rsi); frame++;
//        child(frame, 1659, 83, 110, rsi); frame++;
//        child(frame, 1660, 124, 110, rsi); frame++;
//        child(frame, 1661, 27, 149, rsi); frame++;
//        child(frame, 1662, 83, 149, rsi); frame++;
//        child(frame, 1663, 139, 149, rsi); frame++;
//        child(frame, 1664, 83, 189, rsi); frame++;
//        child(frame, 1665, 83, 229, rsi); frame++;
//        child(frame, 1666, 27, 229, rsi); frame++;
//        child(frame, 1667, 139, 229, rsi); frame++;
//  
//        child(frame, 1688, 29, 111, rsi); frame++; //inventory
//  
//        child(frame, 18799, 278, 200, rsi); frame++; //character
//    }

	/* Miscellaneous methods */
	public static int getNextFree() {
		for (int abc = 0 + 18786; abc < interfaceCache.length; abc++)
			if (get(abc) == null)
				return abc;
		return -1;
	}
	
	public static void fixIDs() {
		int option = Main.getWorkspace().promptForConfirm("RSPSi will attempt to fix the ids and parentIds on the current open interface." + //ask to fix
				"\nDo you wish to continue?", 0);
		if (option != 0)
			return;

		boolean fixAll = Main.getWorkspace().promptForConfirm("RSPSi can also fix parentIds of all children." + //ask to fix all children
				"\nDo you want to fix all parendIds?", 0) == 0 ? true : false;
		
		String[] input = Main.getWorkspace().promptForInput("Fix IDs()", new String[] { "Main interface ID:" }, 1, 0); //ask for topParent id
    	if (input == null)
    		return;

    	int id = Integer.parseInt(input[0]);
    	if (id < 0 || id >= interfaceCache.length || get(id) == null) {
    		Main.getWorkspace().promptForNotice("Interface is null, cannot fix"); //if interfaceCache[id] is null, prompt & return
    		return;
    	}
		
		Interface rsi = get(id); //grab the topParent based on given id
		rsi.id = id; //set the id
		rsi.parentId = id; //set the parentId - to self
		Main.load(id); //refresh
		
		if (fixAll) { //if fixing all
			for (int layer = 0; layer < rsi.children.length; layer++) { //loop through children ids
				fixID(layer, rsi.children[layer], id); //fix each child
			}
		}
		
	}
	
	public static void fixID(int fixLayer, int fixId, int topParent) {
		Interface rsi = get(fixId);

		if (rsi.type == 0) {
			String[] input = Main.getWorkspace().promptForInput("Fix IDs()", new String[] { "Layer " + fixLayer + " - Container ID :" }, 1, 0);
	    	if (input == null)
	    		return;

	    	int id = Integer.parseInt(input[0]);
	    	if (id < 0 || id >= interfaceCache.length || get(id) == null) {
	    		Main.getWorkspace().promptForNotice("Interface is null, cannot fix");
	    		return;
	    	}
			
			rsi = get(id);
			rsi.id = id;
			rsi.parentId = topParent;
			Main.load(topParent);

			for (int layer = 0; layer < rsi.children.length; layer++) { //loop through children ids
				fixID(layer, rsi.children[layer], topParent); //fix each child
			}

		} else {
			rsi.id = fixId;
			rsi.parentId = topParent;
			Main.load(topParent);
		}
		
	}

	/* Methods for managing children */

	public static void totalChildren(int total, Interface rsi) {
        rsi.children = new int[total];
        rsi.childX = new int[total];
        rsi.childY = new int[total];
    }

    public static void child(int frame, int[] childIdXY, Interface rsi) {
        rsi.children[frame] = childIdXY[0];
        rsi.childX[frame] = childIdXY[1];
        rsi.childY[frame] = childIdXY[2];
    }

    public static void appendChild(int frame, int[] childIdXY, Interface rsi) {
    	int[] oldChildren = rsi.children,
        oldChildX = rsi.childX,
        oldChildY = rsi.childY;

    	totalChildren(rsi.children.length + 1, rsi);
		child(frame, childIdXY, rsi);
		for (int abc = 0; abc < rsi.children.length; abc++)
			if (abc < frame)
				child(abc, new int[] { oldChildren[abc], oldChildX[abc], oldChildY[abc] }, rsi);
			else if (abc > frame)
				child(abc, new int[] { oldChildren[abc - 1], oldChildX[abc - 1], oldChildY[abc - 1] }, rsi);
    }

    public static void removeChild(int from, Interface rsi) {
    	int[] oldChildren = rsi.children,
        oldChildX = rsi.childX,
        oldChildY = rsi.childY;

    	totalChildren(rsi.children.length - 1, rsi);
		for (int abc = 0; abc < rsi.children.length; abc++) {
			if (abc < from)
				child(abc, new int[] { oldChildren[abc], oldChildX[abc], oldChildY[abc] }, rsi);
			else if (abc >= from)
    			child(abc, new int[] { oldChildren[abc + 1], oldChildX[abc + 1], oldChildY[abc + 1] }, rsi);
		}
    }

    public static void moveChild(int from, int to, Interface rsi) {
    	if (from < to) {
			moveChildDown(from, to, rsi);
    	} else if (to < from) {
    		for (int abc = to; abc < from; abc++) {
    			moveChildDown(to, from, rsi);
    		}
    	}
    }

    public static void moveChildDown(int from, int to, Interface rsi) {
    	int[] oldChildren = rsi.children,
        oldChildX = rsi.childX,
        oldChildY = rsi.childY;

    	totalChildren(rsi.children.length, rsi);
		for (int abc = 0; abc < rsi.children.length; abc++) {
			if (abc < from || abc > to)
				child(abc, new int[] { oldChildren[abc], oldChildX[abc], oldChildY[abc] }, rsi);
			else if (abc < to)
    			child(abc, new int[] { oldChildren[abc + 1], oldChildX[abc + 1], oldChildY[abc + 1] }, rsi);
			else
				child(to, new int[] { oldChildren[from], oldChildX[from], oldChildY[from] }, rsi);
		}
    }

    /* Methods for creating/modifying interfaces */

	public static Interface get(int id) {
		return interfaceCache[id];
	}

	public static void create(int id, int type) {
		Interface rsi = interfaceCache[id] = new Interface();
		rsi.id = id;
		rsi.parentId = id;
		rsi.parentId = Main.topParent == null ? id : Main.topParent.parentId;
		rsi.type = type;
	}

	public static void setBounds(int id, int width, int height) {
		Interface rsi = get(id);
		rsi.width = width;
		rsi.height = height;
	}

	public static void setBounds(int id, int type) {
		Interface rsi = get(id);
		if (type == 4) {
			if ((rsi.disabledText != null || rsi.enabledText != null)
					&& (rsi.disabledText != "" || rsi.enabledText != "")) {
				setBounds(id,
						rsi.fontStyle.getTextWidth(rsi.disabledText) > rsi.fontStyle
								.getTextWidth(rsi.enabledText) ? rsi.fontStyle
								.getTextWidth(rsi.disabledText) : rsi.fontStyle
								.getTextWidth(rsi.enabledText),
						rsi.fontStyle.height);
			}
		} else if (type == 5) {
			if (rsi.disabledSprite != null && rsi.enabledSprite != null) {
				setBounds(id,
						rsi.disabledSprite.myWidth > rsi.enabledSprite.myWidth ? rsi.disabledSprite.myWidth
								: rsi.enabledSprite.myWidth,
						rsi.disabledSprite.myHeight > rsi.enabledSprite.myHeight ? rsi.disabledSprite.myHeight
								: rsi.enabledSprite.myHeight);
			} else if (rsi.disabledSprite != null)
				setBounds(id, rsi.disabledSprite.myWidth, rsi.disabledSprite.myHeight);
			else if (rsi.enabledSprite != null)
				setBounds(id, rsi.enabledSprite.myWidth, rsi.enabledSprite.myHeight);
		}
	}

	public static void setColors(int id, int[] colors) {
		Interface rsi = get(id);

		rsi.disabledColor = colors.length > 0 ? colors[0] != -1 ? colors[0] : 0
				: 0;
		rsi.enabledColor = colors.length > 1 ? colors[1] != -1 ? colors[1] : 0
				: 0;
		rsi.mouseOverDisabledColor = colors.length > 2 ? colors[2] != -1 ? colors[2] : 0
				: 0;
		rsi.mouseOverEnabledColor = colors.length > 3 ? colors[3] != -1 ? colors[3] : 0
				: 0;
	}

	public static void setSprites(int id, boolean cache, String[] directories, int[] index) {
		Interface rsi = get(id);
		
		if (directories.length > 0 && directories[0] != null
				&& directories[0] != "" && index.length > 0
				&& index[0] != -1) {
			rsi.disabledSprite = cache ? method207(index[0], archive_rsi, directories[0])
					: customSpriteLoader(directories[0], index[0]);
			rsi.disabledSpriteDir = directories[0];
			rsi.disabledSpriteId = index[0];
		}

		if (directories.length > 1 && directories[1] != null
				&& directories[1] != "" && index.length > 1
				&& index[1] != -1) {
			rsi.enabledSprite = cache ? method207(index[1], archive_rsi, directories[1])
					: customSpriteLoader(directories[1], index[1]);
			rsi.enabledSpriteDir = directories[1];
			rsi.enabledSpriteId = index[1];
		}

		else if (directories.length > 0 && directories[0] != null
				&& directories[0] != "" && index.length > 1
				&& index[1] != -1) {
			rsi.enabledSprite = cache ? method207(index[1], archive_rsi, directories[0])
					: customSpriteLoader(directories[0], index[1]);
			rsi.enabledSpriteDir = directories[0];
			rsi.enabledSpriteId = index[1];
		}
	}
	
	public static void setMediaTypes(int id, int[] type, int[] model) {
		Interface rsi = get(id);
		
		if (type.length > 0 && type[0] != -1
				&& model.length > 0 && model[0] != -1) {
			rsi.disabledMediaType = type[0];
			rsi.disabledMediaId = model[0];
		}
		
		if (type.length > 1 && type[0] != -1
				&& model.length > 1 && model[1] != -1) {
			rsi.enabledMediaType = type[1];
			rsi.enabledMediaId = model[1];
			
		} else if (type.length > 0 && type[0] != -1
				&& model.length > 1 && model[1] != -1) {
			rsi.enabledMediaType = type[0];
			rsi.enabledMediaId = model[1];
		}
	}

	/* Methods for containers */

	public static void container(int id, int width, int height) {
		create(id, 0);
		setBounds(id, width, height);
		totalChildren(0, get(id));
	}

	public static void container(int id, int width, int height, int scroll) {
		container(id, width, height);
		get(id).scrollMax = height + scroll;
	}

	/* Methods for inventories */
	
	public static void inventory(int id) {
		create(id, 2);
		Interface rsi = get(id);
		rsi.inv = new int[0];
		rsi.invStackSizes = new int[0];
		rsi.invItemsCanBeSwapped = false;
		rsi.inventoryInterface = false;
		rsi.usableItemInterface = false;
		rsi.invSwapDeletesTargetSlot = false;
		rsi.invSpritePadX = 0;
		rsi.invSpritePadY = 0;
        rsi.spritesX = new int[20];
        rsi.spritesY = new int[20];
        rsi.sprites = new Sprite[20];
        rsi.spritesDir = new String[20];
        rsi.spritesId = new int[20];
		rsi.actions = new String[5];
        rsi.selectedActionName = "";
        rsi.spellName = "";
        rsi.spellUsableOn = 0;

        rsi.fontStyle = textDrawingAreas_rsi[0];
		rsi.fontId = 0;
		rsi.shadowed = false;
		rsi.disabledColor = 0;
	}

	/* Methods for boxes */

	public static void box(int id) {
		create(id, 3);
		get(id).filled = false;
		setColors(id, new int[] { -1, -1, -1, -1 });
	}

	public static void box(int id, int width, int height, int[] colors) {
		create(id, 3);
		setBounds(id, width, height);
		setColors(id, colors);
	}

	public static void box(int id, int width, int height, byte alpha, int[] colors) {
		box(id, width, height, colors);
		get(id).alpha = alpha;
	}

	public static void box(int id, int width, int height, boolean filled, int[] colors) {
		box(id, width, height, colors);
		get(id).filled = filled;
	}

	public static void box(int id, int width, int height, byte alpha, boolean filled, int[] colors) {
		box(id, width, height, alpha, colors);
		get(id).filled = filled;
	}

	/* Methods for texts */

	public static void text(int id) {
		create(id, 4);
		setColors(id, new int[] { -1, -1, -1, -1 });
		Interface rsi = get(id);
		rsi.disabledText = "";
		rsi.enabledText = "";
		rsi.fontStyle = textDrawingAreas_rsi[0];
		rsi.fontId = 0;
		rsi.centered = false;
		rsi.shadowed = false;
	}

	public static void text(int id, String text, int fontId, int[] colors) {
		create(id, 4);
		setColors(id, colors);
		Interface rsi = get(id);
		rsi.disabledText = text;
		rsi.enabledText = "";
		rsi.fontStyle = textDrawingAreas_rsi[fontId];
		rsi.fontId = fontId;
		rsi.shadowed = true;
		setBounds(id, 4);
	}

	public static void text(int id, String text, int fontStyle, boolean[] centerShadow, int[] colors) {
		text(id, text, fontStyle, colors);
		Interface rsi = get(id);
		rsi.centered = centerShadow[0];
		rsi.shadowed = centerShadow[1];
	}

	public static void text(int id, String[] text, int fontStyle, int[] colors) {
		text(id, text[0], fontStyle, colors);
		get(id).enabledText = text[1];
	}

	public static void text(int id, String[] text, int fontStyle, boolean[] centerShadow, int[] colors) {
		text(id, text[0], fontStyle, centerShadow, colors);
		get(id).enabledText = text[1];
	}

	/* Methods for sprite's */

	public static void sprite(int id) {
		create(id, 5);
		Interface rsi = get(id);
		rsi.disabledSprite = null;
		rsi.disabledSpriteDir = "";
		rsi.disabledSpriteId = -1;
		rsi.enabledSprite = null;
		rsi.enabledSpriteDir = "";
		rsi.enabledSpriteId = -1;
	}

	public static void sprite(int id, boolean cache, String directory, int sprite) {
		create(id, 5);
		setSprites(id, cache, new String[] { directory }, new int[] { sprite });
		setBounds(id, 5);
	}

	public static void sprite(int id, boolean cache, String[] directory, int[] sprites) {
		create(id, 5);
		setSprites(id, cache, directory, sprites);
		setBounds(id, 5);
	}

	/* Methods for models */

	public static void model(int id) {
		create(id, 6);
		Interface rsi = get(id);
		rsi.disabledMediaType = -1;
		rsi.disabledMediaId = -1;
		rsi.enabledMediaType = -1;
		rsi.enabledMediaId = -1;
		rsi.disabledAnimation = -1;
		rsi.enabledAnimation = -1;
		rsi.modelZoom = 128;
		rsi.verticalRotation = 256;
		rsi.horizontalRotation = 64;
	}

	public static void model(int id, int[] type, int[] model, int zoom) {
		create(id, 6);
		Interface rsi = get(id);
		setMediaTypes(id, type, model);
		rsi.modelZoom = zoom;
		rsi.verticalRotation = 256;
		rsi.horizontalRotation = 64;
	}

	public static void model(int id, int[] type, int[] model, int zoom, int[] rotation) {
		model(id, type, model, zoom);
		Interface rsi = get(id);
		rsi.verticalRotation = (2047 / 360) * rotation[0];
		rsi.horizontalRotation = (2047 / 360) * rotation[1];
	}

	public static void model(int id, int[] type, int[] model, int[] anim, int zoom, int[] rotation) {
		model(id, type, model, zoom, rotation);
		Interface rsi = get(id);
		rsi.disabledAnimation = anim[0];
		rsi.enabledAnimation = anim[1];
	}

	/* Methods for item lists */
	
	public static void itemList(int id) {
		create(id, 7);
		Interface rsi = get(id);
		rsi.inv = new int[0];
		rsi.invStackSizes = new int[0];
		rsi.centered = false;
		rsi.fontStyle = textDrawingAreas_rsi[0];
		rsi.fontId = 0;
		rsi.shadowed = false;
		rsi.disabledColor = 0;
		rsi.invSpritePadX = 0;
		rsi.invSpritePadY = 0;
		rsi.inventoryInterface = false;
		rsi.actions = new String[5];
	}

	/* Methods for tool-tips */

	public static void tooltip(int id) {
		create(id, 8);
		get(id).disabledText = "";
	}

	public static void tooltip(int id, int width, int height, String text) {
		create(id, 8);
		setBounds(id, width, height);
		get(id).disabledText = text;
	}

	/* Methods for packing */

	public static int getModelId(int type, int media) {
        if (type == 1)
        	return media;
        if (type == 4)
        	return ItemDef.forID(media).modelId;
        return 0;
	}

	public static void export(String dir, Interface[] rsi) {
		Stream out = new Stream(new byte[999999]);
		out.writeWord(rsi.length > 1 ? -1 : rsi[0].id);
		for (Interface rsi1 : rsi)
			parse(rsi1, out);

		FileOperations.writeFile(dir, out);
		System.out.println("Dump success");
	}

	public static byte[] toData() {
		Stream out = parseAll();
		byte[] data = new byte[out.currentOffset];
		System.arraycopy(out.buffer, 0, data, 0, out.currentOffset);
		return data;
	}

	public static void packToData(String dir) {
		Stream out = parseAll();
		FileOperations.writeFile(dir, out);

		System.out.println("Dump success");
	}

	public static Stream parse(Interface parent, Stream out) {
		getDataFor(parent, out);

		if (parent.children != null)
			for (int abc = 0; abc < parent.children.length; abc++) {
				Interface child = get(parent.children[abc]);
				getDataFor(child, out);
				if (child.type == 0)
					out = parse(child, out);
			}

		return out;
	}

	public static Stream parseAll() {
		Stream out = new Stream(new byte[9999999]);

		int length = 0;
		for (int abc = interfaceCache.length - 1; abc > -1; abc--)
			if (get(abc) != null) {
				length = abc + 1;
				System.out.println("interfaceCache length: " + length);
				break;
			}

		out.writeWord(length);
		for (int abc = 0; abc < length; abc++)
			getDataFor(get(abc), out);

		return out;
	}

	public static Stream getDataFor(Interface rsi, Stream out) {
		if (rsi == null)
			return out;

		int parent = rsi.parentId;
		if (rsi.parentId != -1) {
			out.writeWord(65535);
			out.writeWord(parent);
			out.writeWord(rsi.id);
		} else
			out.writeWord(rsi.id);

		out.writeWordBigEndian(rsi.type);
		out.writeWordBigEndian(rsi.actionType);
		out.writeWord(rsi.contentType);
		out.writeWord(rsi.width);
		out.writeWord(rsi.height);
		out.writeWordBigEndian(rsi.alpha);
		if (rsi.trigger != -1)
			out.writeWordSpaceSaver(rsi.trigger);
		else
			out.writeWordBigEndian(0);

		int valuesLength = 0;

		if (rsi.valueCompareType != null)
			valuesLength = rsi.valueCompareType.length;

		out.writeWordBigEndian(valuesLength);
		if (valuesLength > 0)
			for (int i = 0; i < valuesLength; i++) {
				out.writeWordBigEndian(rsi.valueCompareType[i]);
				out.writeWord(rsi.requiredValues[i]);
			}

		valuesLength = 0;

		if (rsi.valueIndexArray != null)
			valuesLength = rsi.valueIndexArray.length;

		out.writeWordBigEndian(valuesLength);
		if (valuesLength > 0)
			for (int l1 = 0; l1 < valuesLength; l1++) {
				int i3 = rsi.valueIndexArray[l1].length;
				out.writeWord(i3);
				for (int l4 = 0; l4 < i3; l4++)
					out.writeWord(rsi.valueIndexArray[l1][l4]);

			}

		if (rsi.type == 0) {
			out.writeWord(rsi.scrollMax);
			out.writeWordBigEndian(rsi.triggered ? 1 : 0);
			out.writeWord(rsi.children.length);
			for (int i = 0; i < rsi.children.length; i++) {
				out.writeWord(rsi.children[i]);
				out.writeWord(rsi.childX[i]);
				out.writeWord(rsi.childY[i]);
			}
		} else if (rsi.type == 1) {
			out.writeWord(0);
			out.writeWordBigEndian(0);
		} else if (rsi.type == 2) {
			out.writeWordBigEndian(rsi.invItemsCanBeSwapped ? 1 : 0);
			out.writeWordBigEndian(rsi.inventoryInterface ? 1 : 0);
			out.writeWordBigEndian(rsi.usableItemInterface ? 1 : 0);
			out.writeWordBigEndian(rsi.invSwapDeletesTargetSlot ? 1 : 0);
			out.writeWordBigEndian(rsi.invSpritePadX);
			out.writeWordBigEndian(rsi.invSpritePadY);
			for (int i = 0; i < 20; i++) {
				out.writeWordBigEndian(rsi.sprites[i] == null ? 0 : 1);
				if (rsi.sprites[i] != null) {
					out.writeWord(rsi.spritesX[i]);
					out.writeWord(rsi.spritesY[i]);
					out.writeString(rsi.spritesDir[i] + "," + rsi.spritesId[i]);
				}
			}				
			for (int i = 0; i < 5; i++)
				if (rsi.actions[i] != null)
					out.writeString(rsi.actions[i]);
				else
					out.writeString("");

		} else if (rsi.type == 3)
			out.writeWordBigEndian(rsi.filled ? 1 : 0);

		if (rsi.type == 4 || rsi.type == 1) {
			out.writeWordBigEndian(rsi.centered ? 1 : 0);
			out.writeWordBigEndian(rsi.fontId);
			out.writeWordBigEndian(rsi.shadowed ? 1 : 0);
		}
		if (rsi.type == 4) {
			out.writeString(rsi.disabledText);
			out.writeString(rsi.enabledText);
		}
		if (rsi.type == 1 || rsi.type == 3 || rsi.type == 4)
			out.writeDWord(rsi.disabledColor);
		if (rsi.type == 3 || rsi.type == 4) {
			out.writeDWord(rsi.enabledColor);
			out.writeDWord(rsi.mouseOverDisabledColor);
			out.writeDWord(rsi.mouseOverEnabledColor);
		}
		if (rsi.type == 5) {
			if (rsi.disabledSprite != null)
				if (rsi.disabledSpriteDir != null && rsi.disabledSpriteId != -1)
					out.writeString(rsi.disabledSpriteDir + "," + rsi.disabledSpriteId);
				else
					out.writeString("");
			else
				out.writeString("");

			if (rsi.enabledSprite != null)
				if (rsi.enabledSpriteDir != null && rsi.enabledSpriteId != -1)
					out.writeString(rsi.enabledSpriteDir + "," + rsi.enabledSpriteId);
				else
					out.writeString("");
			else
				out.writeString("");

		} else if (rsi.type == 6) {
			if (rsi.disabledMediaType != -1 && rsi.disabledMediaId > 0)
				out.writeWordSpaceSaver(Rspsi.getModelId(rsi.disabledMediaType, rsi.disabledMediaId));
			else
				out.writeWordBigEndian(0);						

			if (rsi.enabledMediaType != -1 && rsi.enabledMediaId > 0)
				out.writeWordSpaceSaver(rsi.enabledMediaId);
			else
				out.writeWordBigEndian(0);						

			if (rsi.disabledAnimation > 0)
				out.writeWordSpaceSaver(rsi.disabledAnimation);
			else
				out.writeWordBigEndian(0);						

			if (rsi.enabledAnimation > 0)
				out.writeWordSpaceSaver(rsi.enabledAnimation);
			else
				out.writeWordBigEndian(0);						

			out.writeWord(rsi.modelZoom);
			out.writeWord(rsi.verticalRotation);
			out.writeWord(rsi.horizontalRotation);

		} else if (rsi.type == 7) {
			out.writeWordBigEndian(rsi.centered ? 1 : 0);
			out.writeWordBigEndian(rsi.fontId);
			out.writeWordBigEndian(rsi.shadowed ? 1 : 0);
			out.writeDWord(rsi.disabledColor);
			out.writeWord(rsi.invSpritePadX);
			out.writeWord(rsi.invSpritePadY);
			out.writeWordBigEndian(rsi.inventoryInterface ? 1 : 0);
			for (int i = 0; i < 5; i++)
				if (rsi.actions[i] != null)
					out.writeString(rsi.actions[i]);
				else
					out.writeString("");
		}

		if (rsi.actionType == 2 || rsi.type == 2) {
			out.writeString(rsi.selectedActionName);
			out.writeString(rsi.spellName);
			out.writeWord(rsi.spellUsableOn);
		}

		if (rsi.type == 8) {
			out.writeString(rsi.disabledText);
//			out.writeString("");
		}

		if (rsi.actionType == 1 || rsi.actionType == 4 || rsi.actionType == 5 || rsi.actionType == 6)
			out.writeString(rsi.tooltip);
		return out;
	}

}
