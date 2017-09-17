<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link type="text/css" rel="stylesheet" href="jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="jsgrid-theme.min.css" />
<script src="jquery-3.2.1.min.js"></script>
 <t:master>
		<jsp:body>
			<ol class="breadcrumb">
			  <li><a href="Index.jsp">Home</a></li>
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
		           "resi":  "${libro.get_resi()}",
		           "prezzo": "${libro.get_prezzo()}",
		      });
		     </c:forEach>

				var pageRow = 20;
				if ($(window).height() < 900) {
					pageRow = 10;
				}
		    var jsgrid = $("#jsGrid").jsGrid({
		        width: "100%",
		 
		        inserting: false,
		        editing: true,
		        sorting: true,
		        paging: true,
		        selecting: true,

				 		pageSize: pageRow,
		        data: libri,
		 
		        fields: [
		            { name: "ISBN", type: "text", width: 70, validate: "required" },
		            { name: "Titolo", type: "text", width: 200, validate: "required" },
		            { name: "Autore", type: "text", width: 200 },
		            { name: "prezzo", type: "text", width: 50, validate: "required" },
		            { name: "genere", type: "text", width: 50 },
		            { name: "disponibili", type: "text", width: 50 },
		            { type: "control" }
		        ],
		    		onItemEditing: function(args) {
		        		args.cancel = true;
		        		$.get("ListaLibriController", {
		            		"isbn": args.item.ISBN,
		            		},
		            		function() {
			            		window.location = "DaInserire.jsp";
		                		});
		        		//window.location = "DaInserire.jsp";
						} 
		    });
      
				
			</script>
		</jsp:body>
</t:master>
		
 		