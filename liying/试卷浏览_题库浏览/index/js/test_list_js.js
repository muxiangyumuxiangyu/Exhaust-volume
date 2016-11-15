window.onload = function()
 	{
 		
 		var title = document.getElementsByName("title");
 		var answerdiv = document.getElementsByName("answerdiv");
 		var falg = true;
 		for(var i=0;i<title.length;i++){
 				title[i].onclick=function(){
 				if(falg == true){
 					answerdiv[0].style.display="block";
 					flag = false;
 				}else if(falg==false){
 					answerdiv[0].style.display="none";
 					flag = true;
 				}
 			}
 		}
 	}