<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="./header.jsp" />
		<!-- Custom styles per le features di bootstrap 'Columns with icons' -->
	   <link href="${pageContext.request.contextPath}/assets/css/features.css" rel="stylesheet">
	   
	   <title>Gestione Permessi</title>
	   
	   <sec:authorize access="hasRole('BO_USER')">
		   <script type="text/javascript">
				$(document).ready(function()  {
					$.ajax({
					    type: 'GET',
					    url:  "${pageContext.request.contextPath}/dipendente/presentiMessaggiNonLetti",
					    statusCode: {
					        200: function(responseObject, textStatus, jqXHR) {
					        	$('#messaggiNonLettiModal').modal('show');
					        },  
						    204: function(responseObject, textStatus, jqXHR) {
					        	// no content quindi non faccio nulla
					        }  
					    }
					});
				});
			</script>
		</sec:authorize>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   		
	   		<!-- #####################################  -->
	   		<!-- elementi grafici per le features in basso  -->
	   		<!-- #####################################  -->
	   		<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
			  <symbol id="people-circle" viewBox="0 0 16 16">
			    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
			    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
			  </symbol>
			  <symbol id="collection" viewBox="0 0 16 16">
			    <path d="M2.5 3.5a.5.5 0 0 1 0-1h11a.5.5 0 0 1 0 1h-11zm2-2a.5.5 0 0 1 0-1h7a.5.5 0 0 1 0 1h-7zM0 13a1.5 1.5 0 0 0 1.5 1.5h13A1.5 1.5 0 0 0 16 13V6a1.5 1.5 0 0 0-1.5-1.5h-13A1.5 1.5 0 0 0 0 6v7zm1.5.5A.5.5 0 0 1 1 13V6a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-.5.5h-13z"/>
			  </symbol>
			  <symbol id="toggles2" viewBox="0 0 16 16">
			    <path d="M9.465 10H12a2 2 0 1 1 0 4H9.465c.34-.588.535-1.271.535-2 0-.729-.195-1.412-.535-2z"/>
			    <path d="M6 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 1a4 4 0 1 1 0-8 4 4 0 0 1 0 8zm.535-10a3.975 3.975 0 0 1-.409-1H4a1 1 0 0 1 0-2h2.126c.091-.355.23-.69.41-1H4a2 2 0 1 0 0 4h2.535z"/>
			    <path d="M14 4a4 4 0 1 1-8 0 4 4 0 0 1 8 0z"/>
			  </symbol>
			  <symbol id="chevron-right" viewBox="0 0 16 16">
			    <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
			  </symbol>
			</svg>
			<!-- ############## end ###################  -->
	   
	   
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="./navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  	<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
				  ${errorMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
			    
			     <div class="p-5 mb-4 bg-light rounded-3">
			     	<sec:authorize access="isAuthenticated() && hasRole('ADMIN')">
				      <div class="container-fluid py-5">
				        <h1 class="display-5 fw-bold">Benvenuto alla Gestione dei permessi</h1>
				        <p class="col-md-8 fs-4">Using a series of utilities, you can create this jumbotron, just like the one in previous versions of Bootstrap. </p>
				        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/utente/search">Gestione Utenze</a>
				 		<a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/dipendente/search">Ricerca Dipendenti</a>
				      </div>
					</sec:authorize>
					<sec:authorize access="isAuthenticated() && hasRole('BO_USER')">
				      <div class="container-fluid py-5">
				        <h1 class="display-5 fw-bold">Benvenuto alla Gestione dei permessi</h1>
				        <p class="col-md-8 fs-4">Using a series of utilities, you can create this jumbotron, just like the one in previous versions of Bootstrap. </p>
				        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/dipendente/search">Gestione Dipendenti</a>
				        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/dipendente/searchMessaggio">Ricerca messaggio</a>
				      </div>
					</sec:authorize>
					<sec:authorize access="isAuthenticated() && hasRole('DIPENDENTE_USER')">
				      <div class="container-fluid py-5">
				        <h1 class="display-5 fw-bold">Benvenuto!</h1>
				        <p class="col-md-8 fs-4">Using a series of utilities, you can create this jumbotron, just like the one in previous versions of Bootstrap. </p>
				        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/richiestaPermesso/search">Ricerca Permesso</a>
				      </div>
					</sec:authorize>
			    </div>
			    
			  </div>
			  
			  <!--  features di bootstrap 'Columns with icons'  -->
			 <sec:authorize access="isAuthenticated() && hasRole('DIPENDENTE_USER')">
			  <div class="container px-4 py-5" id="featured-3">
			    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
			      <div class="feature col">
			        <div class="feature-icon bg-primary bg-gradient">
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#collection"/></svg>
			        </div>
			        <h2>Inserisci Nuovo Permesso</h2>
			        	<p>Paragraph of text beneath the heading to explain the heading. We'll add onto it with another sentence and probably just keep going until we run out of words.</p>
			        	<a href="${pageContext.request.contextPath}/richiestaPermesso/insert" class="icon-link">
			          		Vai alla funzionalitą
			          		<svg class="bi" width="1em" height="1em"><use xlink:href="#chevron-right"/></svg>
			        	</a>
			      </div>
			      <div class="feature col">
			        <div class="feature-icon bg-primary bg-gradient">
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#people-circle"/></svg>
			        </div>
			        <h2>Inserisci Nuovo Regista</h2>
			        <p>Paragraph of text beneath the heading to explain the heading. We'll add onto it with another sentence and probably just keep going until we run out of words.</p>
			        <a href="${pageContext.request.contextPath}/regista/insert" class="icon-link">
			          Vai alla funzionalitą
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#chevron-right"/></svg>
			        </a>
			      </div>
			      <div class="feature col">
			        <div class="feature-icon bg-primary bg-gradient">
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#toggles2"/></svg>
			        </div>
			        <h2>Ricerca Film</h2>
			        <p>Paragraph of text beneath the heading to explain the heading. We'll add onto it with another sentence and probably just keep going until we run out of words.</p>
			        <a href="${pageContext.request.contextPath}/film/search" class="icon-link">
			          Vai alla funzionalitą
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#chevron-right"/></svg>
			        </a>
			      </div>
			    </div>
			  </div>
			 </sec:authorize>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="./footer.jsp" />
			
			<!-- Modal -->
			<div class="modal fade" id="messaggiNonLettiModal" tabindex="-1"  aria-labelledby="messaggiNonLettiModalLabel"
			    aria-hidden="true">
			    <div class="modal-dialog" >
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title" id="messaggiNonLettiModalLabel">Info presenza messaggi</h5>
			                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			            </div>
			            <div class="modal-body">
			                Sono presenti messaggi non letti. Accedere all'area messaggi?
			            </div>
			            <div class="modal-footer">
			                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Torna in Pagina</button>
			                <a href="${pageContext.request.contextPath }/dipendente/listNonLetti" class='btn btn-primary' >
					            Continua
					        </a>
			            </div>
			        </div>
			    </div>
			</div>
	  </body>
</html>