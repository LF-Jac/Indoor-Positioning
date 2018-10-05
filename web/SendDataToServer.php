<?php
 // receive data from app's http request
 $data=$_POST["myHttpData"];
 
 $splitData = explode(",", $data);
 if (!isset($splitData[1])) {
	 $splitData[1] = null;
 }
 $fileCount = $splitData[0];
 $filename = 'PositionDataPlot.txt';
 
 //create file if not exist. Then write (append) to file.
 if(file_exists($filename))
 {
	$myfile = fopen($filename, "w") or die("Unable to open file!");
	fwrite($myfile, $data . PHP_EOL);
 }
 else
 {
	$myfile = fopen($filename, "w") or die("Unable to open file!");
	fwrite($myfile, $data . PHP_EOL);	
 }
 fclose($myfile); 
?>