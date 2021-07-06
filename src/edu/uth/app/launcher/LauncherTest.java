package edu.uth.app.launcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uth.radx.model.Foa;
import edu.uth.radx.model.FoaProjects;

public class LauncherTest implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(LauncherTest.class.getName());

	public LauncherTest() {

	}

	public void run() {
		 testJson();
		//listAllClass();
		//printEnvironmentVariables();
		// effectiveVelocity();
		// mti01();
		// postIt();
		// testDirSize();
		// listFile();
		// manipulateFileName();
		// testPrimexx();
		// testPrimexx1();
		// testTime();
		//countFiles("C:\\prowess\\2019-09-R\\java\\org.ucdm\\src");
		System.out.println("Successfully Done!");
	}

	public static void main(String[] args) {
		System.out.println("Executing LauncherTest()");
		SwingUtilities.invokeLater(new LauncherTest());
	}

	public void printEnvironmentVariables() {
		Properties p = System.getProperties();
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
		    String key = (String)keys.nextElement();
		    String value = (String)p.get(key);
		    System.out.println(key + ": " + value);
		}
	}
	public void countFiles(String root) {

		long fileCount;
		try {
			fileCount = Files.walk(Paths.get(root))
					.parallel()
					.filter(p -> !p.toFile().isDirectory())
					.count();
			System.out.println(root);
			System.out.printf("File Count:: %d ", fileCount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testDirSize() {
		boolean recursive = true;
		String srcPath = "C:\\prowess\\java\\org.ucdm\\src\\org\\ucdm";
		String targetPath = "C:\\prowess\\java\\org.ucdm\\posted160531";
		final long start = System.nanoTime();
		long size = 0;
		for (int i = 0; i < 100; i++) {
			// size = DirSize.sizeOf(new File(srcPath));
			// size = DirSize.sizeOfByForkJoin(new File(srcPath));
		}
		final long takenInNano = System.nanoTime() - start;
		final long taken = TimeUnit.NANOSECONDS.toSeconds(takenInNano);

		LOGGER.info("Size of " + srcPath + ": " + size + " bytes (in " + takenInNano + " nano)" + " and (in " + taken + " S)");
	}

	public void testTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS");
		LocalDateTime stageOriginTime = LocalDateTime.parse("2019-05-04 11:32:49.00692", formatter);
		System.out.println(stageOriginTime);
		LocalDateTime stageOriginTime1 = LocalDateTime.parse("2019-05-04 11:32:49.00740", formatter);
		System.out.println(stageOriginTime1);

		float a = (float) Duration.between(stageOriginTime, stageOriginTime1).getNano();
		System.out.println("a=" + a);

	}

	public void postIt() {
		// boolean recursive = true;
		// String srcPath = "C:\\prowess\\java\\org.ucdm\\src\\org\\ucdm";
		// String targetPath = "C:\\prowess\\java\\org.ucdm\\posted160531";
		// String targetPath =
		// "C:\\prowess\\TFS\\checkedOut\\MicroseismicSuite\\Research\\UCDM";
		// walkingThroughFiles(recursive, srcPath, targetPath);
	}

	public void walkingThroughFiles(boolean recursive, String srcPath, String targetPath) {
		long len = 0L;
		String name = null;
		String srcFileName = null;
		String targetFileName = null;
		File[] listOfFiles = new File(srcPath).listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			name = listOfFiles[i].getName();
			// System.out.println(listOfFiles[i].getName());
			if (listOfFiles[i].isFile()) {
				srcFileName = srcPath + File.separator + name;
				if (FilenameUtils.isExtension(name, "java")) {
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
				if (!recursive)
					continue;
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

	public void listFile() {
		// String cwd = "C:\\prowess\\ucdm\\jars\\poi-bin-4.0.0-20180907\\lib";
		// String cwd = "\\Desktop-p4oiufb\\D"; //credential DAS das
		String cwd = "K:\\"; // credential DAS das
		File folder = new File(cwd);
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				System.out.println(file.getName());
			} else {

			}
		}
		// try {
		// Files.list(Paths.get(cwd)).forEach(System.out::println);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public void manipulateFileName() {
		String cwd = "C:\\prowess\\2019-06-R\\java\\org.ucdm\\src\\org\\ucdm\\dsp\\wavelet\\";
		String outputFolder = cwd.replaceFirst("java", "CPP");
		System.out.println(outputFolder);

		try {
			FileUtils.forceMkdir(new File(outputFolder));
		} catch (IOException e) {
			// e.printStackTrace();
		}

	}

	// id=0 for remove comments
	// id=1 for jave2c
	public void readTextFile(int id, String srcFileName, String targetFileName) {
		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(srcFileName));
			writer = new BufferedWriter(new FileWriter(targetFileName));
			String line = null;
			String lineTrimed = null;
			int k = 0;
			while ((line = reader.readLine()) != null) {
				lineTrimed = line.trim();
				if (id == 0) {
					// if(lineTrimed.startsWith("/")||lineTrimed.startsWith("*")) {
					if (lineTrimed.isEmpty() || lineTrimed.startsWith("/") || lineTrimed.startsWith("*")) {
						// System.out.println(lineTrimed);
						continue;
					} else {
						writer.write(line);
						writer.newLine();
					}
				} else if (id == 1) {
					if (lineTrimed.isEmpty() || lineTrimed.startsWith("/") || lineTrimed.startsWith("*")) {
						// System.out.println(lineTrimed);
						continue;
					} else {
						writer.write(line);
						writer.newLine();
					}
				}

				k++;
			}

			reader.close();
			writer.close();
		} catch (IOException e) {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException exc) {
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException exc) {
				}
			}
		}
	}

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
    
    private void listAllClass() {
    	String path = "C:\\Users\\dpei2\\Projects\\uth\\java\\uth\\src";
    	File directory = new File(path);
    	String packageName = "edu.uth";
//    	try {
//			//List<String> classes = findClasses(directory, packageName);
//			for (String c : classes) {
//				System.out.println(c);
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	//File dir = new File(directory);
//    	File [] files = directory.listFiles(new FilenameFilter() {
//    	    @Override
//    	    public boolean accept(File dir, String name) {
//    	        return name.endsWith(".java");
//    	    }
//    	});
//
//    	for (File xmlfile : files) {
//    	    System.out.println(xmlfile);
//    	}
    	
    	//getFilesRecursive(directory);
    	
    	String[] types = {"java"};
    	Collection<File> files = FileUtils.listFiles(directory, types, true);
    	int k = path.length();
    	System.out.println("n="+files.size()+" k="+k);
    	for(File f: files) {
    		String fileName = f.getAbsolutePath();
    		String className = fileName.substring(k+1);
    		System.out.println("-keep class "+className.replace('\\', '.'));
    	}
    }
    
    private void getFilesRecursive(File pFile) {
        for(File file : pFile.listFiles())        {
            if(file.isDirectory())            {
                getFilesRecursive(file);
            } else {
            	if(file.getName().endsWith(".java")){
            	      System.out.println("-keep class "+file.getAbsolutePath());
            	    }
            	
            }
        }
        
        
    }
    
    private void testJson() {
    	String fileName = "C:\\Users\\dpei2\\Projects\\uth\\release\\resources\\projectList.json";
    	
    	FoaProjects projects = new FoaProjects(fileName);
    	projects.printObject(0);
    	
//    	Foa f1 = Foa.AUTOMATIC;
//    	
//    	String a = "SCENT";
//    	for (Foa foa : Foa.values()) { 
//    		if(foa==f1) {
//    	    System.out.println(foa+" : "+foa.getDescription()); 
//    		}
//    		if(a.equals(foa.toString())) {
//    			System.out.println(foa+" : "+foa.getDescription()); 
//    		}
//    		
//    		System.out.println(foa+" : "+foa.getDescription());
//    	}
    	
    	
//    	ObjectMapper mapper = new ObjectMapper();
//        try {
//        	Map<String, Object> jsonMap = mapper.readValue(new File(fileName),
//        		    new TypeReference<Map<String,Object>>(){});
//        	
//        	for (Map.Entry<String,Object> entry : jsonMap.entrySet()) {
//                System.out.println("Key = " + entry.getKey() +
//                                 ", Value = " + entry.getValue());
//        	}
//        	
//        	//String jsonData = mapper.readValueAsString(new File(fileName));
//			//Projects projects = mapper.readValue(new File(fileName), Projects.class);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
