<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>

	   <title>Inserisci Nuovo Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">

	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>


			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">

			  		<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="insert_richiesta_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>

			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>

			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci nuovo Richiesta di Permesso</h5> 
				    </div>
				    <div class='card-body'>

							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>


							<form:form enctype="multipart/form-data" modelAttribute="insert_richiesta_attr" method="post" action="${pageContext.request.contextPath}/richiestaPermesso/save" novalidate="novalidate" class="row g-3">								

								<div class="col-md-3">
									<label for="tipoPermesso" class="form-label">TIPO PERMESSO <span class="text-danger">*</span></label>
								    <spring:bind path="tipoPermesso">
									    <select class="form-select ${status.error ? 'is-invalid' : ''}" id="tipoPermesso" name="tipoPermesso" required>
									    	<option value="" selected> - Selezionare - </option>
									      	<option value="FERIE" ${insert_richiesta_attr.tipoPermesso == 'FERIE'?'selected':''} >FERIE</option>
									      	<option value="MALATTIA" ${insert_richiesta_attr.tipoPermesso == 'MALATTIA'?'selected':''} >MALATTIA</option>
									    </select>
								    </spring:bind>
								    <form:errors  path="tipoPermesso" cssClass="error_field" />
								</div>

								<div class="col-md-6">
									<label for="nota" class="form-label">Note <span class="text-danger">*</span></label>
									<spring:bind path="note">
										<textarea class="form-control rounded-0" id="note" name="note" rows="3" name="note"></textarea>
									</spring:bind>
									<form:errors  path="note" cssClass="error_field" />
								</div>

								<div class="col-md-6" id="codiceCertificato">
									<label for="codiceCertificato" class="form-label">Codice Certificato <span class="text-danger">*</span></label>
									<spring:bind path="codiceCertificato">
										<input type="text" name="codiceCertificato" id="codiceCertificato" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il codiceCertificato" value="${insert_richiesta_attr.codiceCertificato }" required>
									</spring:bind>
									<form:errors  path="codiceCertificato" cssClass="error_field" />
								</div>


								<div class="col-md-6" id="attachment">
								  <label for="attachment" class="form-label">Allegato </label>
								  <spring:bind path="attachment">
									  <input class="form-control" type="file" id="attachment" name="attachment" value="">
								  </spring:bind>
								</div>


								<div class="col-md-12">
								  <input class="form-check-input" type="checkbox"  id="giornoSingolo" name="giornoSingolo">
								  <label class="form-check-label" for="giornoSingolo">Giorno Singolo</label>
								</div>

								<div class="col-md-6" id="dataInizio">	
									<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${insert_richiesta_attr.dataInizio}' />
									<div class="form-group col-md-6">
									<label for="dataInizio" class="form-label">Data di Inizio</label>
			                        	<spring:bind path="dataInizio">
				                        	<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataInizio" type="date" placeholder="dd/MM/yy"
				                            		title="formato : gg/mm/aaaa"  name="dataInizio" value="${parsedDate}" >
				                           </spring:bind>
			                            <form:errors  path="dataInizio" cssClass="error_field" />
									</div>
								</div>

								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDateFine" type='date' value='${insert_richiesta_attr.dataFine}' />
								<div class="col-md-3">
									<label for="dataFine" class="form-label">Data Fine <span class="text-danger">*</span></label>
                        			<spring:bind path="dataFine">
	                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataFine" type="date" placeholder="dd/MM/yy"
	                            		title="formato : gg/mm/aaaa"  name="dataFine" required 
	                            		value="${parsedDateFine}" >
		                            </spring:bind>
	                            	<form:errors  path="dataFine" cssClass="error_field" />
								</div>



								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
								</div>



						</form:form>


  						<script type="text/javascript">
						$(document).ready(function(){
							if($("#giornoSingolo").is(':checked')){
								
								$("#dataFine").attr("disabled","disabled");
							}
							else{
								$("#dataFine").removeAttr("disabled");
							}
							
							$("#giornoSingolo").change(function() {
								
								if($("#giornoSingolo").is(':checked')){
									
									$("#dataFine").attr("disabled","disabled");
								}
								else{
									$("#dataFine").removeAttr("disabled");
								}
							});
							
						});
						</script>

				    <script type="text/javascript">
						$(document).ready(function(){
							if($("#tipoPermesso").val() == "MALATTIA"){
								
								$("#codiceCertificato").show();
								$("#attachment").show();
							}
							else{
								
								$("#codiceCertificato").hide();
								$("#attachment").hide();
							}
							
							$("#tipoPermesso").change(function() {
								
								if($("#tipoPermesso").val() == "MALATTIA"){
									
									$("#codiceCertificato").show();
									$("#attachment").show();
								}
								else{
									
									$("#codiceCertificato").hide();
									$("#attachment").hide();
								}
							});
							
						});
						</script>


					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		


			  <!-- end container -->  
			  </div>

			</main>

			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html> 