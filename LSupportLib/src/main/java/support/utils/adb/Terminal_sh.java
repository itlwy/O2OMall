package support.utils.adb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;
/**
 * 安卓执行java代码执行shell 命令
 * support.utils.adb
 * Terminal_sh.java
 * 
 * 2015-10-7-上午11:16:09
 * author-lwy
 * @version 1.0.0
 */
public class Terminal_sh {
	private Process process = null;
	private DataOutputStream os = null;
	private Context context;

	public Terminal_sh(Context context) throws IOException {
		this.context = context;
		process = Runtime.getRuntime().exec("/system/bin/sh");
		os = new DataOutputStream(process.getOutputStream());
	}

	public  int RootCommand1(String command) {
		if (command != null) {
			Log.i("debug", "RootCommand :" + command);
		}
		try {
			os.write((command + "\n").getBytes("ASCII"));
			os.flush();
			os.write(("exit\n").getBytes("ASCII"));
			os.flush();
		
			process.waitFor();
			
		} catch (Exception e) {
			Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
		}
		return 	process.exitValue();
	}
	
	public static int  RootCommand(String command) {

		if (command != null) {
		}
		Process process = null;
		DataOutputStream os = null;
		String erroInfo = "";
		String rightInfo = "";
		try {
			process = Runtime.getRuntime().exec("/system/bin/sh");
			os = new DataOutputStream(process.getOutputStream());
			// read(process.getInputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			BufferedReader readerin = new BufferedReader(new InputStreamReader(process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
				output.append(buffer, 0, read);
			}
			reader.close();

			int read1;
			char[] buffer1 = new char[4096];
			StringBuffer output1 = new StringBuffer();
			// ByteArrayOutputStream output1 = new ByteArrayOutputStream();
			while ((read1 = readerin.read(buffer1)) > 0) {
				output1.append(buffer1, 0, read1);
			}
			readerin.close();

			process.waitFor();
			erroInfo = output.toString();
			rightInfo = output1.toString();

		} catch (Exception e) {
			return   process.exitValue();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return   process.exitValue();
	}
}
