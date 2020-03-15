<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link type="text/css" rel="stylesheet" href="jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="jsgrid-theme.min.css" />
<script src="jquery-3.2.1.js"></script>
 <t:master>
		<jsp:body>
			<ol class="breadcrumb">
			  <li><a href="index.jsp">Home</a></li>
			  <li><a href="#">Lista</a></li>
			</ol>
			<div id="jsGrid"></div>
			<form action="ExportController" method="get">
			  <input type="text" id="hiddenButtonName" name="hiddenButtonName" hidden="true"/>
				<div class="panel-footer">
				
					<div class="col-lg-1">
						<input type="submit" class="btn" value="Esporta" id="buttExport" onclick="$('#hiddenButtonName')[0].value='Export';"/>
					</div>
					<div class="col-lg-1">
						<input type="submit" class="btn" value="Rendi" id="buttRendi" onclick="$('#hiddenButtonName')[0].value='Rendi';"/>
					</div>
				</div>
			</form>
			<script type="text/javascript">
			$(function () {
				  $( "#dialog1" ).dialog({
				    autoOpen: false
				  });
				  
				  $("#opener").click(function() {
				    $("#dialog1").dialog('open');
				  });
				});
				function getFields() {
					  var fields= [
				            { name: "ISBN", type: "text", width: 70, validate: "required" },
				            { name: "Titolo", type: "text", width: 200, validate: "required" },
				            { name: "Autore", type: "text", width: 200 },
				            { name: "prezzo", type: "text", width: 50, validate: "required" },
				            { name: "genere", type: "text", width: 50 },
				            { name: "disponibili", type: "text", width: 70 },
				            { type: "control", 
				            	 modeSwitchButton: false,
				                 editButton: false
				            }
				        ]
						if ("${list_type}" == "Sold") {
							fields= [
					            { name: "ISBN", type: "text", width: 70, validate: "required" },
					            { name: "Titolo", type: "text", width: 200, validate: "required" },
					            { name: "Autore", type: "text", width: 200 },
					            { name: "prezzo", type: "text", width: 50, validate: "required" },
					            { name: "genere", type: "text", width: 50 },
					            { name: "ultimaVendita", type: "text", width: 70, title: "Ultima Vendita" },
					            { type: "control", 
					            	 modeSwitchButton: false,
					                 editButton: false
					            }
					            ]
						}
				 	return fields;      				 	
				}
			
				var libri = new Array();
		 		 <c:forEach items="${libri}" var="libro" varStatus="status">
		  		 libri.push(
		  		 {
		  	  		 "ISBN": "${libro.get_isbn()}",  	
		  	  		 "casaeditrice": "${libro.get_casaEditrice()}",    		 
		       	   "Titolo": "${libro.get_titolo()}",  		 
		       	   "titolo": "${libro.get_titolo()}",
		       	   "Autore": "${libro.get_autoriString()}",
		           "autori": "${libro.get_autoriString()}",
		           "genere": "${libro.get_genere()}",
		           "acquistati": "${libro.get_quantita()}",
		           "disponibili":  "${libro.GetDisponibili()}",
		           "venduti":  "${libro.get_venduti()}",
		           "ultimaVendita": "${libro.get_LastDateVenditeAsString()}",
		           "resi":  "${libro.get_resi()}",
		           "prezzo": "${libro.get_prezzo()}",
		      });
		     </c:forEach>

				var pageRow = 20;
				if ($(window).height() < 900) {
					pageRow = 10;
				}
				$.extend(
						{
						    redirectPost: function(location, args)
						    {
						        var form = '';
						        $.each( args, function( key, value ) {
						            form += '<input type="hidden" name="'+key+'" value="'+value+'">';
						        });
						        $('<form action="'+location+'" method="POST">'+form+'</form>').submit();
						    }
						});
		    var jsgrid = $("#jsGrid").jsGrid({
		        width: "100%",
		 
		        inserting: false,
		        editing: true,
		        sorting: true,
		        paging: true,
		        selecting: true,

				 		pageSize: pageRow,
		        data: libri,
// 		        rowClick: function(args) {
// 		            showDetailsDialog("Edit", args.item)
// 		        },
		        fields: getFields(),
		    		onItemEditing: function(args) {
		        		args.cancel = true;
		        		//response.sendRedirect(request.getContextPath()+"/DaInserireController");
		        		$.get("DaInserireController", {
		            		"ISBN": args.item.ISBN,
		            		"view": true
		            		},
		            		function (isbn) {
		            			var redirect = function(url, method) {
		            			    var form = document.createElement('form');
		            			    form.method = method;
		            			    form.action = url;
		            			    form.submit();
		            			};
		            			//window.location.href = "DaInserire.jsp?isbn=" + isbn;
			            		//$.redirectPost('DaInserire.jsp', {'libro': libro._isbn });
		            			window.location.href = "GetBookController?ISBN=" + isbn + "&hiddenButtonName="
		            		}
		            	);
		        		
		    		}
		    });
      
		    $("#detailsDialog").dialog({
		        autoOpen: false,
		        width: 400,
		        close: function() {
		            $("#detailsForm").validate().resetForm();
		            $("#detailsForm").find(".error").removeClass("error");
		        }
		    });
		    var showDetailsDialog = function(dialogType, client) {
		        $("#titolo").val(client.Titolo);
		        $("#autori").val(client.Age);
		        $("#prezzo").val(client.Address);
		        
		        formSubmitHandler = function() {
		            saveClient(client, dialogType === "Add");
		        };
		 
		        $("#detailsDialog").dialog("option", "title", dialogType + " Client")
		                .dialog("open");
		    };
				
			</script>
		</jsp:body>
</t:master>
		
 		