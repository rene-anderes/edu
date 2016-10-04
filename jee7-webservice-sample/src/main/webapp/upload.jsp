<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="w3.css">
	<title>Upload your file here</title>
</head>
<body>
	<section class="w3-container w3-content" style="max-with:600px">
		<form action="FileUploadServlet" method="post" enctype="multipart/form-data">
			<p>
				<label>Select File to Upload:</label>
				<input class="w3-input" type="file" name="fileName" multiple="multiple" required autofocus value=""><br>
			</p>
			<button class="w3-btn w3-red" type="submit" value="Upload">upload</button>
		</form>
	</section>
</body>
</html>
