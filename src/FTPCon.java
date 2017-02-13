import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//todo maven, gradle, spring framework, junit, sqllite
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPCon {
	
	static String server = "localhost";
	static int port = 21;
	
	static FTPClient ftpClient = new FTPClient();
	static boolean logined = false;
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args)
	{
			System.out.println("Enter login and password:");
			String log = scanner.next();
			String pass = scanner.next();
			FTPConnect(log, pass);
			if (ftpClient.isConnected()) logined = true;
			boolean com = scanner.hasNext();
			while (com)
			{
				try
				{
					String comman = scanner.nextLine();
					String[] command = comman.split(" ");
					if (command[0].trim().equals("go"))
					{
						GoToDirectory(command[1]);
					}
					else if (command[0].trim().equals("ls"))
					{
						ShowFiles();
					}
					else if (command[0].trim().equals("back"))
					{
						GoBack();
					}
					else if (command[0].trim().equals("mkdir"))
					{
						CreateDir(command[1]);
					}
					else if (command[0].trim().equals("deldir"))
					{
						DeleteDir(command[1]);
					}
					else if (command[0].trim().equals("delfile"))
					{
						DeleteFile(command[1]);
					}
					else if (command[0].trim().equals("up"))
					{
						UploadFile(command[1], command[2]);
					}
					else if (command[0].trim().equals("down"))
					{
						DownloadFile(command[1], command[2]);
					}
					else if (command[0].trim().equals("help"))
					{
						System.out.println("ls - show the list of files");
						System.out.println("go - go to directory");
						System.out.println("back - back to parent directory");
						System.out.println("mkdir - create directory");
						System.out.println("deldir - delete directory");
						System.out.println("delfile - delete file");
						System.out.println("up - upload file (path, name)");
						System.out.println("down - download file (name, path)");
					}
					else System.out.println("Invalid input. Use 'help' command");
				} catch (Exception e) {
					System.out.println("Error");
				}
			}
		 
	}
			
	private static void DeleteFile(String path) 
	{
		try {
			ftpClient.deleteFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void DeleteDir(String path) 
	{
		try {
			ftpClient.removeDirectory(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void FTPConnect(String user, String password)
	{
		try
		{
			ftpClient.connect(server, port);
			ftpClient.login(user, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);		
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public static void ShowFiles()
	{
		if (!ftpClient.isConnected())
		{
			System.out.println("Not connected");
		} 
		else
		{
			try 
			{
				FTPFile[] files = ftpClient.listFiles();
				for (FTPFile file : files)
				{
					if (file.isDirectory())
					{
						System.out.println("[" + file.getName() + "]" + "\t\t" + file.getTimestamp().getTime());
					} else System.out.println(file.getName() + "\t\t" + file.getTimestamp().getTime());
				}
			}
			catch (IOException e) 
			{
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	public static void GoToDirectory(String path)
	{
		try {
			ftpClient.changeWorkingDirectory(path);
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public static void GoBack()
	{
		try {
			ftpClient.changeToParentDirectory();
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public static void CreateDir(String path)
	{
		try {
			ftpClient.makeDirectory(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void UploadFile(String localFilePath, String remoteFileName) throws IOException
	{
		File localFile = new File(localFilePath);
		try {
			InputStream is = new FileInputStream(localFile);
			System.out.println("Start uploading first file");
	        boolean done = ftpClient.storeFile(remoteFileName, is);
	        is.close();
	        if (done) 
	        {
	        	System.out.println("The file has been uploaded successfully.");
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void DownloadFile(String remoteFileName, String localFilePath) throws IOException
	{
        OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(localFilePath));
        boolean success = ftpClient.retrieveFile(remoteFileName, outputStream1);
        outputStream1.close();
        if (success) {
            System.out.println("File has been downloaded successfully.");
        }
	}
}