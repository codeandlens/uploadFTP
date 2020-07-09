package com.transfer.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SftpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SftpApplication.class, args);
		uploadFTP();
	}

	private static void uploadFTP() {

		FTPClient ftpClient = new FTPClient();
		
		String ipServer = "10.100.1.21";
		Integer port = 22;
		String user = "administrator";
		String password = ".=hwfhcojovo@2563";
		
		String pathFileLocal = "D:\\FPS\\q.gif";
		
		try {
			ftpClient.connect(ipServer, port);
			boolean login = ftpClient.login(user, password);
			if (login) {
				ftpClient.enterLocalPassiveMode();

				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

				String LocalFile = "D:\\FPS\\q.gif";
				File file = new File(LocalFile);

				if (file.exists()) {

					String remoteFile = "/"+file.getName();
					InputStream inputStream = new FileInputStream(file);

					System.out.println("Start uploading first file");
					boolean done = ftpClient.storeFile(remoteFile, inputStream);
					inputStream.close();
					if (done) {
						System.out.println("The first file is uploaded using FTP Successfully.");
					}else
						System.out.println("The file is uploaded FTP Fail.");
				}else
						System.out.println("The system cannot find the file specified");
			} else
				System.out.println("fail");

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

	}

}
