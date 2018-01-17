<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <script type="text/javascript" src="jquery-3.2.1.min.js"></script>
 <t:master>
		<jsp:body>
			<ol class="breadcrumb">
			  <li><a href="index.jsp">Home</a></li>
			  <li><a href="ListaLibri.jsp">Lista</a></li>
			  <li><a href="#">Dettaglio -17/01</a></li>
			</ol>
			<!--<jsp:useBean id="libro" scope="session" class="utility.Libro"></jsp:useBean>-->
			<form action="DaInserireController" method="get">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-1">
							<label id="label-0" >ISBN:</label>
						</div>
						<div class="col-lg-3">
						<input id="isbn" class="form-control"  type="text" name="ISBN" value="${libro.get_isbn() }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-1">
							<label id="label-1">Titolo:</label>
						</div>
						<div class="col-lg-3">
							<input id="titolo" class="form-control"  type="text" name="titolo" value="${libro.get_titolo() }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-1">
							<label id="label-2">Autori:</label>
						</div>
						<div class="col-lg-3">
							<input id="autori" class="form-control"  type="text" name="autori" value="${libro.get_autoriString()}"/>
						</div>			
					</div>		
					<div class="row">
						<div class="col-lg-1">
						<label id="label-3">Casa Editrice:</label>
						</div>
						<div class="col-lg-3">
						<input id="casaeditrice" class="form-control"  type="text" name="casaeditrice" value="${libro.get_casaEditrice()}"/>
						</div>
					</div>	
					<div class="row">
						<div class="col-lg-1">
						<label id="label-4">Genere:</label>
						</div>
						<div class="col-lg-3 dropdown">	
						  <input id="genere" class="form-control"  type="hidden" name="genere" value= "${libro.get_genere()}"/>
						  <button class="btn btn-default dropdown-toggle" 
						  	type="button" id="genereDropDown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" name="genereDropDown">
						    ${libro.get_genere()} <span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu">
						  	<c:forEach items="${generi}" var="genere" varStatus="status">			  		
							    <li  onclick="$('#genere')[0].value = '${genere}';"><a href="#">${genere}</a></li>
						  	</c:forEach>
						  </ul>
						</div>
					</div>			
					<div class="row">
						<div class="col-lg-1">
						<label id="label-5">Prezzo:</label>
						</div>
						<div class="col-lg-3">
						<input id="prezzo" class="form-control"  type="text" name="prezzo" value="${libro.get_prezzo()}"/>
						</div>
					</div>				
					<div class="row"><div class="col-lg-1">
								<label id="label-6">Acquistati:</label>
							</div>
							<div class="col-lg-1">
								<input id="quantita" class="form-control"  type="text" name="quantita" value="${libro.get_quantita()}"/>
							</div>
							<div class="col-lg-1">
								<label id="label-9">Disponibili:</label>
							</div>
							<div class="col-lg-1">
								<input id="disponibili" class="form-control"  type="text" name="disponibili" value="${libro.GetDisponibili()}"/>
							</div>				
							<div class="col-lg-1">
								<label id="label-7">Venduti:</label>
							</div>
							<div class="col-lg-1">
								<input id="venduti" class="form-control"  type="text" name="venduti" value="${libro.get_venduti()}"/>
							</div>
							<div class="col-lg-1">
								<label id="label-8">Resi:</label>
							</div>
							<div class="col-lg-1">
								<input id="resi" class="form-control"  type="text" name="resi" value="${libro.get_resi()}"/>
							</div>
					</div>
					<div class="panel-footer">
						<div class="row">
							<div class="col-lg-1">
							<input type="submit" class="btn" value="Aggiorna" id="button-1"/>
							</div>
						</div>
					</div>
				</div>
			</form>
			<script type="text/javascript">
				$(".dropdown-menu li a").click(function(){
					  $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
					  $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
					});
				$(document).ready(function() {
					if ($('#isbn')[0].value == "") {
						$.get("DaInserireController", {
		            		"ISBN": location.href.substring(location.href.search('isbn') + 5),
		            		"view": true,
		            		"all": true
		            		},
		            		function (libro) {
			            		var jsonLibro = JSON.parse(libro);
			            		$('#isbn')[0].value = jsonLibro._isbn;		   
			            		$('#titolo')[0].value = jsonLibro._titolo;		   
			            		$('#autori')[0].value = jsonLibro._autoriString;		   
			            		$('#casaeditrice')[0].value = jsonLibro._casaEditrice;		   
			            		$('#genere')[0].value = jsonLibro._genere;		               
			            		$('#prezzo')[0].value = jsonLibro._prezzo;		               
			            		$('#quantita')[0].value = jsonLibro._quantita;		               
			            		$('#resi')[0].value = jsonLibro._resi;	               
			            		$('#venduti')[0].value = jsonLibro._venduti;	           
			            		$('#disponibili')[0].value = jsonLibro._disponibili;	            		
		            		}
		            	);
						}
					});
			</script>
		</jsp:body>
</t:master>