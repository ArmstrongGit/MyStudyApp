 function $1(x){
  return document.getElementById(x);
 }
 function selonchange(x,y){
 var wrap=$1(x);
 var result=wrap.getElementsByTagName("span");
 var sel=$1(y);
  var opt=sel.getElementsByTagName("option");
  var len=opt.length;
  for(i=0;i<len;i++){
   if(opt[i].selected==true){
    x=opt[i].innerHTML;
   }
  }
  result[0].innerHTML=x;
 }

$(function(){ 
$('.search1').live('focus', function(){  
 $(this).removeClass("new");
})
$('.search1').live('blur', function(){  
if($(this).val()==""){
	
$(this).addClass("new");
}
})
})

function selectAllDels() 
{ 
var allCheckBoxs = document.getElementsByName("preDelCheck"); 
var desc = document.getElementById("allChecked"); 
var selectOrUnselect=false; 
for(var i = 0; i < allCheckBoxs.length; i ++ ) 
{ 
if(allCheckBoxs[i].checked){ 
selectOrUnselect=true; 
break; 
} 
} 
if (selectOrUnselect) 
{ 
_allUnchecked(allCheckBoxs); 
}else 
{ 
_allchecked(allCheckBoxs); 
} 
} 
function _allchecked(allCheckBoxs){ 
for(var i = 0; i < allCheckBoxs.length; i ++ ) 
{ 
allCheckBoxs[i].checked = true; 
} 
} 
function _allUnchecked(allCheckBoxs){ 
for(var i = 0; i < allCheckBoxs.length; i ++ ) 
{ 
allCheckBoxs[i].checked = false; 
} 
} 

function selectAll() 
{ 
var allCheckBoxs1 = document.getElementsByName("collect"); 
var desc1 = document.getElementById("allCheck"); 
var selectOrUnselect=false; 
for(var i = 0; i < allCheckBoxs1.length; i ++ ) 
{ 
if(allCheckBoxs1[i].checked){ 
selectOrUnselect=true; 
break; 
} 
} 
if (selectOrUnselect) 
{ 
_allUnchecked(allCheckBoxs1); 
}else 
{ 
_allchecked(allCheckBoxs1); 
} 
} 
function _allchecked(allCheckBoxs1){ 
for(var i = 0; i < allCheckBoxs1.length; i ++ ) 
{ 
allCheckBoxs1[i].checked = true; 
} 
} 
function _allUnchecked(allCheckBoxs1){ 
for(var i = 0; i < allCheckBoxs1.length; i ++ ) 
{ 
allCheckBoxs1[i].checked = false; 
} 
} 

