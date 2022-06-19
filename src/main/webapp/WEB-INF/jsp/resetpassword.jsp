<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Inserisci Nuovo Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci nuovo elemento</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form method="post" action="confirmResetPassword" novalidate="novalidate" class="row g-3">
					
							
								<div class="col-md-6">
									<label for="vecchiaPassword" class="form-label">Vecchia Password: <span class="text-danger">*</span></label>
									
										<input type="password" name="vecchiaPassword" id="vecchiaPassword" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire la vecchia password" required>
									
									<form:errors  path="vecchiaPassword" cssClass="error_field" />
								</div>
								
								<div class="col-md-6">
									<label for="nuovaPassword" class="form-label">Nuova Password: <span class="text-danger">*</span></label>
									
										<input type="password" name="nuovaPassword" id="nuovaPassword" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire la nuova password" required>
									
									<form:errors  path="nuovaPassword" cssClass="error_field" />
								</div>
								<div class="col-md-6">
									<label for="confermaNPassword" class="form-label">Conferma Nuova password <span class="text-danger">*</span></label>
									
										<input type="password" class="form-control ${status.error ? 'is-invalid' : ''}" name="confermaNPassword" id="confermaNPassword" placeholder="Confermare la nuova password" required>
									
									<form:errors  path="confermaNPassword" cssClass="error_field" />
								</div>
								
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								</div>
		
						</form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="footer.jsp" />
	  </body>
</html>