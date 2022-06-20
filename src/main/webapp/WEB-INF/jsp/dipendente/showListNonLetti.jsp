<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<jsp:include page="../header.jsp" />
	<title>Pagina dei risultati</title>
	
</head>
<body class="d-flex flex-column h-100">
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
			  ${successMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
			  ${errorMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Lista dei Messaggi</h5> 
			    </div>
			    		<div class='card-body'>
			    		<a href="${pageContext.request.contextPath}/dipendente/searchMessaggio" class='btn btn-outline-secondary' >
				            <i class='fa fa-chevron-left'></i> Torna alla Ricerca
				   	 	</a>
			    
			        <div class='table-responsive'>
			            <table class='table table-striped ' >
			                <thead>
			                    <tr>		                        
			                        <th>Oggetto</th>
			                        <th>Testo</th>
			                        <th>Data Inserimento</th>
			                        <th>Data Lettura</th>
			                        <th>Azioni</th>
			                    </tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${messaggiNonLetti_list_attribute}" var="messaggioItem">
									<tr>
										<td>${messaggioItem.oggetto}</td>
										<td>${messaggioItem.testo }</td>
										<td><fmt:formatDate type="date" value = "${messaggioItem.dataInserimento}" /></td>
										<td><fmt:formatDate type="date" value = "${messaggioItem.dataLettura}" /></td>
										<td>
											<a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/dipendente/showMessaggio/${messaggioItem.id}">Visualizza</a>
										</td>
									</tr>
								</c:forEach>
			                </tbody>
			            </table>
			        </div>
			   
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>