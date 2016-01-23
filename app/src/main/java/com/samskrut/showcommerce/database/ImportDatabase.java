package com.samskrut.showcommerce.database;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ImportDatabase {

	InputStream ips;

	public ImportDatabase(InputStream inputstream) {
		// TODO Auto-generated constructor stub

		this.ips = inputstream;

	}

	public void copyDataBase() {

		try {
			
			// Open your local db as the input stream

			OutputStream databaseOutputStream = new FileOutputStream(
					"/data/data/com.samskrut.showcommerce/databases/commerceDB.sql");
			// Path to the just created empty db

			InputStream databaseInputStream;

			byte[] buffer = new byte[1024];
			
			int length;

			databaseInputStream = ips;

			while ((length = databaseInputStream.read(buffer)) > 0) {
				databaseOutputStream.write(buffer);
			}
			databaseInputStream.close();
			databaseOutputStream.flush();
			databaseOutputStream.close();
			
			
			
			
			

		} catch (Exception e) {
			System.out.println("copying database  fail.... ");
			// TODO: handle exception
			// Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
}
