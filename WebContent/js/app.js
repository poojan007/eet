function populateDependentDropDown(parentId, targetFieldId, nextTargetFieldId, fieldCons, isAll)
{
	var childFieldId = '#'+targetFieldId;
	$(childFieldId).empty();
	
	if("Y" != isAll){
		$(childFieldId).append("<option value=''>--SELECT--</option>");
	}
	
	var url = context+'/JsonDataLoader.data?fieldCons='+fieldCons+'&parentId='+parentId;
	$.ajax({	
		type: "GET",
		url : url,
		cache: false,
		async: false,
		dataType : "json",
		success : function(data) 
		{
			if("Y" == isAll){
				$(childFieldId).append("<option value='ALL'>ALL</option>");
			}	
			
			for (var i = 0; i < data.length; i++) {
				$(childFieldId).append("<option value=" + data[i].headerId + ">"+ data[i].headerName + "</option>");
		    }
		},
		error : function(jqXHR, textStatus, errorThrown) {		
			//alert(textStatus);
		}
	});
}

function sweetAlert(title, msg, type){
	swal({
		  title: title,
		  text: msg,
		  type: type,
		  timer: 2000,
		  buttons: false
		}, function () {
        	swal.close();
    });
}