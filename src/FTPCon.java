import java.io.IOException;
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
		try{
			System.out.println("Enter login and password:");
			String log = scanner.next();
			String pass = scanner.next();
			FTPConnect(log, pass);
			if (ftpClient.isConnected()) logined = true;
			boolean com = scanner.hasNext();
			while (com)
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
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
	
	
}