<?php
error_reporting(E_ALL ^ E_DEPRECATED);

$rows = array();
$table = array();
$table['cols'] = array( 
	array('label' => 'X', 'type' => 'number'),
	array('label' => 'Position Coordinates without CP', 'type' => 'number'),
	//array('label' => 'Position Coordinates with CP', 'type' => 'number')
	);
	
$file = fopen("PositionDataPlot.txt", "r");
while(!feof($file)){
    $line = fgets($file);
	$splitData = explode(",", $line);
	if ( ! isset($splitData[1])) {
		$splitData[1] = null;
	}
	if ( ! isset($splitData[2])) {
		$splitData[2] = null;
	}
	if ( ! isset($splitData[3])) {
		$splitData[3] = null;
	}
	
	$xValue = $splitData[0];
	$yValue = $splitData[1];
	
	$xValue2 = $splitData[2];
	$yValue2 = $splitData[3];
	
	global $x1; $x1= (int) $xValue;
	global $y1; $y1= (int) $yValue;
	global $x2; $x2= (int) $xValue2;
	global $y2; $y2= (int) $yValue2;
	
	$temp = array();
	$temp[0] = array('v' => (int)$xValue);
	$temp[1] = array('v' => (int)$yValue);
	$rows[] = array('c' => $temp);
	
	$temp2 = array();
	$temp2[0] = array('v' => (int)$xValue2);
	$temp2[2] = array('v' => (int)$yValue2);
	//$rows[] = array('c' => $temp2);
}
fclose($file);

$table['rows'] = $rows;
$jsonTable = json_encode($table);

global $errorX;
$errorX = (int) $x2 - (int) $x1;
global $errorY;
$errorY = (int)$y2 - (int) $y1;

//echo '<pre>'; print_r($table['cols']); echo '</pre>';
?>

<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
	 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript">
	
      google.load("visualization", "1.1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
		  
		var data = new google.visualization.DataTable(<?php echo $jsonTable ?>);
		
	    var options = {		
		title: 'Positioning Map via PDR',
        hAxis: {title: 'Y-Axis', minValue: 0, maxValue: 10},
        vAxis: {title: 'X-Axis', minValue: 0, maxValue: 10},		
		legend: 'right',
		
		annotation: { 1: {style: 'letter'}},
		annotations: {
			textStyle: {
			auraColor: '#ccc',
			bold: true,
			color: '#000',
			fontSize: 10,
			opacity: 0.3
			}
		},
		displayAnnotations: true,
		lineWidth: 0,
		pointSize: 5
        };

		var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
		//var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
        chart.draw(data, options); 
	}		
    </script>
  </head>
  <body>
  
	<!--Divs that will hold the charts-->
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
	</script>

  </body>
</html>

