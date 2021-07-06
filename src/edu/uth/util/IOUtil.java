package edu.uth.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import edu.mines.jtk.io.ArrayFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class IOUtil {

	public static boolean readBinaryFileToArray1(float[][] D, String selectedFileName) {
		DataInputStream dis = null;
		try {
			File file = new File(selectedFileName);
			if (!file.exists())
				return false;
			file = new File(selectedFileName);
			dis = new DataInputStream(new FileInputStream(selectedFileName));
			for (int i = 0; i < D.length; i++) {
				for (int j = 0; j < D[0].length; j++) {
					D[i][j] = dis.readFloat();
				}
			}
			// af.readFloats(D);
		} catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " + e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (IOException e) {
				}
		}
		return true;
	}

	public static boolean readBinaryFileToArray(float[][] D, String selectedFileName) {
		ArrayFile af = null;
		try {
			File file = new File(selectedFileName);
			if (!file.exists())
				return false;
			file = new File(selectedFileName);
			af = new ArrayFile(file, "r", ByteOrder.LITTLE_ENDIAN, ByteOrder.LITTLE_ENDIAN);
			af.seek(0);
			for (int i = 0; i < D.length; i++) {
				for (int j = 0; j < D[0].length; j++) {
					D[i][j] = af.readFloat();
				}
			}
			// af.readFloats(D);
		} catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " + e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			if (af != null)
				try {
					af.close();
				} catch (IOException e) {
				}
		}
		return true;
	}

	public static boolean readTextFileToArray(float[][] D, String selectedFile) {
		int nx = D[0].length;
		int nz = D.length;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(selectedFile));

			for (int i = 0; i < nz; i++) {
				String line = reader.readLine();
				StringTokenizer st = new StringTokenizer(line, " ");
				for (int j = 0; j < nx; j++) {
					D[i][j] = Float.parseFloat(st.nextToken().trim());
				}
			}
			reader.close();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		return true;
	}

	public static boolean writeArrayToBinaryFile1(float[][] D, String selectedFileName) {
		DataOutputStream dos = null;
		try {
			File file = new File(selectedFileName);
			if (file.exists()) {
				file.delete();
			}
			file = new File(selectedFileName);
			dos = new DataOutputStream(new FileOutputStream(selectedFileName));
			for (int i = 0; i < D.length; i++) {
				for (int j = 0; j < D[0].length; j++) {
					dos.writeFloat(D[i][j]);
				}
			}
			// af.writeFloats(D);
		} catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " + e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			if (dos != null)
				try {
					dos.close();
				} catch (IOException e) {
				}
		}
		return true;
	}

	public static boolean writeArrayToTextFile(float[][] D, String selectedFile) {
		int nx = D[0].length;
		int nz = D.length;

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile, false));

			for (int i = 0; i < nz; i++) {
				for (int j = 0; j < nx; j++)
					bufferedWriter.write(D[i][j] + " ");
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		return true;
	}

	public static void writeTextToFile(String text, String selectedFile) {
		if(text==null || text.isEmpty()) {
			return;
		}
		try {
			PrintWriter out = new PrintWriter(new FileWriter(selectedFile));
			out.print(text);
			if (out.checkError()) {
				throw new IOException("Error while writing to file.");
			}
			out.close();
		} catch (IOException ioexception) {
			String s1 = "IOException: " + selectedFile;
			JOptionPane.showMessageDialog(null, s1, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static boolean writeArrayToBinaryFile(float[][] D, String selectedFileName) {
		ArrayFile af = null;
		try {
			File file = new File(selectedFileName);
			if (file.exists()) {
				file.delete();
			}
			file = new File(selectedFileName);
			af = new ArrayFile(file, "rw", ByteOrder.LITTLE_ENDIAN, ByteOrder.LITTLE_ENDIAN);
			af.seek(0);
			for (int i = 0; i < D.length; i++) {
				for (int j = 0; j < D[0].length; j++) {
					af.writeFloat(D[i][j]);
				}
			}
			// af.writeFloats(D);
		} catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " + e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} finally {
			if (af != null)
				try {
					af.close();
				} catch (IOException e) {
				}
		}
		return true;
	}

	public static void renameFileExtension(String rootPath, String srcFileExtension, String targetFileExtension) {
		String name = null;
		String srcFileName = null;
		String targetFileName = null;
		File[] listOfFiles = new File(rootPath).listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			name = listOfFiles[i].getName();
			System.out.println(listOfFiles[i].getName());
			if (listOfFiles[i].isFile()) {
				if (FilenameUtils.isExtension(name, srcFileExtension)) {
					srcFileName = rootPath + File.separator + name;
					targetFileName = rootPath + File.separator + FilenameUtils.getBaseName(name) + "." + targetFileExtension;
					try {
						FileUtils.moveFile(new File(srcFileName), new File(targetFileName));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				String childSrcPath = rootPath + File.separator + name;
				renameFileExtension(childSrcPath, srcFileExtension, targetFileExtension);
			}
		}
	}

	public static List<String> readTextFileToList(String fileName) {
		String[] encodings = new String[] {
				"UTF-8", "UTF-16", "UTF-32", "ASCII",
		};
		for (int i = 0; i < encodings.length; i++) {
			List<String> a = readTextFileToList(fileName, encodings[i]);
			if (a != null) {
				return a;
			}
		}
		return null;
	}

	public static List<String> readTextFileToList(String fileName, String encoding) {
		try {
			return FileUtils.readLines(new File(fileName), encoding);
		} catch (IOException e) {
			return null;
		}
	}

	public static void writeStringToFile(String fileName, String dataString) {
		try {
			FileUtils.writeStringToFile(new File(fileName), dataString);
		} catch (IOException e) {
		}
	}

	public static void writeLines(String fileName, List<String> lines) {
		try {
			FileUtils.writeLines(new File(fileName), lines);
		} catch (IOException e) {
		}
	}

	public static void countFiles(String root) {
		long fileCount;
		try {
			fileCount = Files.walk(Paths.get(root))
					.parallel()
					.filter(p -> !p.toFile().isDirectory())
					.count();
			System.out.println(root);
			System.out.printf("File Count:: %d ", fileCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void walkingThroughFiles(boolean recursive, String srcPath, String targetPath) {
		long len = 0L;
		String name = null;
		String srcFileName = null;
		String targetFileName = null;
		File[] listOfFiles = new File(srcPath).listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			name = listOfFiles[i].getName();
			System.out.println(listOfFiles[i].getName());
			if (listOfFiles[i].isFile()) {
				srcFileName = srcPath + File.separator + name;
				if (FilenameUtils.isExtension(name, "js")) {
					if (!name.contains("Test")) {
						// targetFileName = targetPath+File.separator+name;
						// System.out.println(targetFileName);
						// readTextFile(0, srcFileName, targetFileName);
					}
					// if(new File(targetFileName).exists()) {
					// if(_overwriteSeg2File) convertToSegy(srcFileName, targetFileName,
					// _bytePosition, _byteLen, _byteValue);
					// } else {
					// convertToSegy(srcFileName, targetFileName, _bytePosition, _byteLen,
					// _byteValue);
					// }
					// FileUtils.forceDelete(src);
				} else {
					// if(!_copyOtherFile) continue;
					// targetFileName = targetPath+File.separator+name;
					// try {
					// if(new File(targetFileName).exists()) {
					// if(_overwriteNonSeg2File) {
					// FileUtils.copyFile(new File(srcFileName), new
					// File(targetFileName));
					// LogWindowHandler.getInstance().publish("Copy
					// "+targetFileName+"\n");
					// }
					// } else {
					// FileUtils.copyFile(new File(srcFileName), new
					// File(targetFileName));
					// LogWindowHandler.getInstance().publish("Copy
					// "+targetFileName+"\n");
					// }
					//
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
				}
			} else {
				if (!recursive) {
					continue;
				}
				String childTargetPath = targetPath + File.separator + name;
				File childFile = new File(childTargetPath);
				if (!childFile.exists()) {
					try {
						FileUtils.forceMkdir(childFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				walkingThroughFiles(recursive, srcPath + File.separator + name, childTargetPath);
			}
		}
	}

}
