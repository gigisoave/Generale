<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<html>
<script type="text/javascript" src="jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="jsgrid.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<head>
	<meta charset="ISO-8859-1" content="">
	<meta name="viewport" content="initial-scale=1, width=device-width">
	<link rel='stylesheet' href='bootstrap/css/bootstrap.min.css'>
	<link rel="stylesheet" href="Stili.css" />
	<title>Libri bottega</title>
</head>
  <body>
    <div id="pageheader">
      <jsp:invoke fragment="header"/>
    </div>
    <div id="body">
      <jsp:doBody/>
    </div>
    <div id="pagefooter">
      	<jsp:invoke fragment="footer"/>
    </div>
  </body>
</html>