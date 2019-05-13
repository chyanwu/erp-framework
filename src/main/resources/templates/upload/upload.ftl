<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>uploader</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<form method="POST" action="/upload/upload" enctype="multipart/form-data">
    <input type="file" name="file" /><br/><br/>
    <input type="submit" value="Submit" />
</form>
</body>