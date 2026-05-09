package com.cybage.model;

public class UploadFileResponse
{
	 static int uploadCount;

	 private String fileName;

	    public UploadFileResponse(String fileName) {
	        this.fileName = fileName;
	        uploadCount++;
	    }

	    public String getFileName() {
	        return fileName;
	    }

	    public void setFileName(String fileName) {
	        this.fileName = fileName;
	    }

		public static int getUploadCount() {
			return uploadCount;
		}

		public static void setUploadCount(int uploadCount) {
			UploadFileResponse.uploadCount = uploadCount;
		}
	    
	    
}
