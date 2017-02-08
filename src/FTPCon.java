import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPCon {
	
	public static void FTPConnect(String user, String password)
	{
		String server = "localhost";
		int port = 21;
		
		FTPClient ftpClient = new FTPClient();
		
		try
		{
			ftpClient.connect(server, port);
			ftpClient.login(user, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			FTPFile[] files = ftpClient.listFiles();
			JOptionPane.showMessageDialog(null, files);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}