<!DOCTYPE html>
<html>
<title>Collaborative Positioning</title>
<meta http-equiv="refresh" content="2">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style type="text/css">
#up_finished {
    font-size:
	24px;
}
</style>
<style>
h2 {font-size: 34px;}
h3 {font-size: 34px;}
</style>
<body>
<header class="w3-container w3-grey w3-center" style="padding:5px 12px">
  <h1 class="w3-margin">Indoor Positioning Demo</h1>
</header>

<!-- First Grid 
<div class="w3-container w3-cell" id="up_finished">
      <h2 class="w3-text-teal">Position Estimation</h2>
	  <p class="w3-text-teal"> Floor Plan </p>
	  <img src="images/floorPlan.png" style="width:380px;height:180px;"><br /><br />
      <?php include 'positionChecked.php';?>
	  
	  <?php include 'doorMap.php';?>
</div>-->

<!-- Second Grid -->
<div class="w3-container w3-cell w3-leftbar">
      <h3 class="w3-text-teal"> Positioning Graph Plot </h3>
      <?php include 'graphPlotCombined.php';?>  
</div>

<div class="w3-container w3-cell w3-leftbar">
      <h3 class="w3-text-teal"> Correction Point Detection </h3>
      <?php include 'WiFiMap.php';?>  
</div>

</body>
</html>