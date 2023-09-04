function showHide2(id){
	console.log(id);
if ( $( "#"+id ).is( ":hidden" ) ) {
    $( "#"+id ).slideDown( "slow" );
  } else {
    $( "#"+id ).hide();
  }


//	$( "#"+id ).slideDown( "slow", function() {
    // Animation complete.
  
};

/*
if ( $( "ul:next" ).is( ":hidden" ) ) {
    $( "#"+id ).slideDown( "slow" );
  } else {
    $( "#"+id ).hide();
  }
  */
  

  function myShowHide() {
 var x = document.getElementById("showhide291");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}


function showHide(id) {
    var ulshowhide = document.getElementsByClassName(id)
    if (ulshowhide[0].style.display === "none") {
        ulshowhide[0].style.display = "block"
    } else {
        ulshowhide[0].style.display = "none"
    }
  }

