# Instructions
Place a standard 317-377 cache (no new model headers etc) in .file_store/.cache/

# Features & what they do.

## CHATBOX COMMANDS:

/lockmag - Locks the magnifier at the current x/y of your mouse inside
the client area

## MENU ITEMS:

### File Menu

New - Lets you create a new interface

Load -\>

From Cache - Load an interface from the currently loaded interfaceCache

From Cache File - Load a new interfaceCache from a .dat file

Import - Import an exported interface to the interfaceCache from a .rsi
file

Save -\>

To Cache - Lets you save the currently loaded interfaceCache to your own
cache

To Cache File - Save the currently loaded interfaceCache to a .dat file
in original format

Export - Export an interface to a .rsi file which you can later import

Exit - \*Duh\* Prompts the editor for exit

### Edit Menu

Move - Move an object on an interface

Resize - Resize an object or container on an interface

Media - Toms Suite is integrated for extra features. 

a) Edit Raw File System - Ex: Choose index 0, then dump file 4 ( media.jag ) which enables you to use the menu item Tools > Options.

b) Edit Jagex Archives - Allows editing of archives. Ex: Title.jag, Media.jag.

c) Edit Image Archvies - Allowing for editing of sprites from Title/Media/Texture.

Set -\>

Bounds() - Sets the width/height of selected objects automatically based
on their types

Font - Set the font ID of selected objects

Color - Set a particular type of color of selected objects

Layer -\>

Move To Top - Moves selected objects to the top of their container(s)

Move To... - Moves selected objects to a specified layer of their
container(s)

Move To Bottom - Moves selected objects to the bottom of their
container(s)

Duplicate - Duplicates the selected object(s) to a specified id

Remove - Removes an object from its container

### View Menu

\[ \] Reference - Whether the referenced image is to be drawn

\[ \] Outline - Whether an outline around the selected object(s) is to
be drawn

\[ \] - Shaded - Whether a shaded box is to be drawn within the outline
of the selected object(s)

Set Reference - Lets you browse for a reference image

Set Region - Lets you change the region for the interface to be drawn
(GameScreen, Inventory, Chatbox, Fullscreen)

Interface Menu

Insert -\>

Existing Object - Insert an object that already exists

New Object - Insert a new object of a specified id and type

Fix IDs() - Attempts to fix broken ids/parentIds of the current
interface as well as its children if specified

Export Selected - Lets you export specific objects of an interface which
you can later import

### Tools Menu

\[ \] Magnifier - Whether the magnifier tool is to be shown

\[ \] Values Editor - Whether the values editor tool is to be shown

Options -\>

Media.jag location - You can browse for a media.jag that you wish to use

Backup Modifications to RSI/DAT file - Every interface modification is saved to the local 'Saves' folder, as a DAT or RSI file.

This is like manually going File > To Cache File/ Export. The difference is that every change you make is automatically saved.

These buttons are toggable & both can be active at the same time.

Help - Shows a help window



# Credits:
* Robin Spud - for being super sexy and helping me out.
* Peter - Reference of his interface editor, basic design layout idea.
* FileChooserDemo2.java - ImagePreview.java
* Advocatus - Gradle Build + Updates
* Snow Essence - Toms Suite Integration + Backup Options.
