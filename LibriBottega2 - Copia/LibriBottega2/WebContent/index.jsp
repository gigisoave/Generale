 <%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
 <script type="text/javascript" src="jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
 <t:master>
 	<jsp:attribute name="header">
 		<ol class="breadcrumb">
		  <li><a href="#">Home</a></li>
		</ol>
	</jsp:attribute>
	
 	<jsp:body>
 	<form>
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-4">
					<label id="label-1" for="ISBN">ISBN:</label>
					<input  class="form-control" id="ISBN" name="ISBN" value="${viewstate_index}"></input>
				</div>
			</div>
		</div>
		<div class="panel-footer">
				<div class="row">
					<input type="text" id="hiddenButtonName" name="hiddenButtonName" hidden="true"/>
					<div class="col-lg-1">
						<input type="submit" class="btn" value="Inserisci" id="button-inserisci" onclick="form.action='GetBookController';"/>
					</div>
					<div class="col-lg-1">
						<input type="submit" class="btn" value="Tutti" id="buttViewAll" onclick="$('#hiddenButtonName')[0].value='All'; form.action='ViewController';"/>
					</div>
					<div class="col-lg-1">
						<input type="submit" class="btn" value="Disponibili" id="buttViewInShop" onclick="$('#hiddenButtonName')[0].value='InShop'; form.action='dacanc';"/>
					</div>
					<div class="col-lg-1">
						<input type="submit" class="btn" value="Venduti" id="buttViewSold" onclick="$('#hiddenButtonName')[0].value='Sold'; form.action='ViewController';"/>
					</div>
				</div>
		</div>
		
			</form>
 	</jsp:body> 
 </t:master>