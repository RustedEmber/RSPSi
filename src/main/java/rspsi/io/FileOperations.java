/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rspsi.io;

import java.io.*;

import rspsi.client.stream.Stream;

/**
 *
 * @author Benjamin
 */
public class FileOperations {

//    public void savePreferences() {
//        try {
//            File file = new File("FileStore/pref.xml");
//            FileWriter fw = new FileWriter(file);
//            BufferedWriter bw = new BufferedWriter(fw);
//
//            bw.write("");
//            bw.newLine();
//
//            bw.flush();
//            bw.close();
//
//            //Rspsi.setInterfaceSaved(true);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

	public static byte[] readFile(String dir) {
		try {
			File file = new File(dir);
			byte[] data = new byte[(int) file.length()];

			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(data);
			dis.close();

			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeFile(String dir, Stream out) {
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(
					new File(dir)));
			dos.write(out.buffer, 0, out.currentOffset);
			dos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
