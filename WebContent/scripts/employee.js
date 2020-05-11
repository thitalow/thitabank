function getAllTrans(){
	fetch('http://localhost:2222/p1.5/allTransactions', {
		method:'GET'
	})
	  .then(response => 
	  	{
			if(response.status == 501){
				response.text().then(message => {
					document.getElementById("allTrans").innerHTML = "<span style='color: red;'>"+message+"</span>";
				});
			} else {
				response.json().then(data => {
					  printTable="<table class='accountsTable'><tr>" +
				  		"<th>Transaction ID</th>" +
				  		"<th>From Account</th>" +
				  		"<th>To Account</th>" +
				  		"<th>Amount</th>" +
				  		"<th>Type</th>" +
				  		"<th>Status</th>" +
				  		"</tr>";
					  data.forEach(a => {
						  console.log(a.id);
						  printTable = printTable + `<tr><td>${a.id}</td>
						  <td>${a.fromAcc}</td>
						  <td>${a.toAcc}</td>
						  <td>${a.amount}</td>
						  <td>${parseType(a.type)}</td>
						  	<td>${parseStatus(a.status)}</td></tr>`;
					  });
				  printTable = printTable + "</table>";
				  document.getElementById("allTrans").innerHTML=printTable;
				});
			}
	  	})
}

function getAllCustomers(){
	fetch('http://localhost:2222/p1.5/allCustomers', {
		method:'GET'
	})
	  .then(response => 
	  	{
			if(response.status == 501){
				response.text().then(message => {
					document.getElementById("allCusts").innerHTML = "<span style='color: red;'>"+message+"</span>";
				});
			} else {
				response.json().then(data => {
					  printTable="<table class='accountsTable'><tr>" +
				  		"<th>Customer ID</th>" +
				  		"<th>Name</th>" +
				  		"<th>Username</th>" +
				  		"<th>Password</th>" +
				  		"</tr>";
					  data.forEach(a => {
						  console.log(a.id);
						  printTable = printTable + `<tr><td>${a.id}</td>
						  <td>${a.name}</td>
						  <td>${a.username}</td>
						  	<td>${a.password}</td></tr>`;
					  });
				  printTable = printTable + "</table>";
				  document.getElementById("allCusts").innerHTML=printTable;
				});
			}
	  	})
}

function getAllPendingAccs(){
//	fetch('http://localhost:2222/p1.5/getAllPendingAccounts')
//	  .then(response => response.json())
//	  .then(data => {
//		  console.log(data);
//		  printTable="<table class='accountsTable'><tr>" +
//		  		"<th>Account ID</th>" +
//		  		"<th>Initial Balance</th>" +
//		  		"<th>Account Owner's ID</th>" +
//		  		"</tr>";
//			  data.forEach(a => {
//				  console.log(a.id);
//				  printTable = printTable + `<tr><td>${a.id}</td>
//				  <td>${a.balance}</td>
//				  	<td>${a.userid}</td></tr>`;
//			  });
//		  printTable = printTable + "</table>";
//		  document.getElementById("allPendingAccs").innerHTML=printTable;
//	  })
	fetch('http://localhost:2222/p1.5/getAllPendingAccounts', {
		method:'GET'
	})
	  .then(response => 
	  	{
			if(response.status == 501){
				response.text().then(message => {
					document.getElementById("allPendingAccs").innerHTML = "<span style='color: red;'>"+message+"</span>";
				});
			} else {
					response.json().then(data => {
						printTable="<table class='accountsTable'><tr>" +
				  		"<th>Account ID</th>" +
				  		"<th>Initial Balance</th>" +
				  		"<th>Account Owner's ID</th>" +
				  		"</tr>";
					  data.forEach(a => {
						  console.log(a.id);
						  printTable = printTable + `<tr><td>${a.id}</td>
						  <td>${a.balance}</td>
						  	<td>${a.userid}</td></tr>`;
					  });
				  printTable = printTable + "</table>";
				  document.getElementById("allPendingAccs").innerHTML=printTable;
				});
			}
	  	})
}

function acceptPendingAccount(){
	let acceptPendingAccountIdValidate=document.getElementById("acceptPendingAccountId").value;
	var message="";
	var flag=true;
	if(acceptPendingAccountIdValidate==""){
		message="Please enter an Account ID.";
		flag=false;
	}
	if(flag== true){
		let data = {id: document.getElementById("acceptPendingAccountId").value};
		fetch('http://localhost:2222/p1.5/acceptPendingAccount', {
			method: 'POST',
			headers: {
			    'Content-Type': 'application/json',
			  },
			body: JSON.stringify(data)
		})
		.then(response => 
	//	console.log(response)
			{
				if(response.status == 501){
					response.text().then(message => {
						document.getElementById("acceptPendingAccountMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
					});
				} else {
					response.text().then(message => {
						document.getElementById("acceptPendingAccountMessage").innerHTML = "<span style='color: green;'>"+message+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("acceptPendingAccountMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function rejectPendingAccount(){
	let rejectPendingAccountIdValidate=document.getElementById("rejectPendingAccountId").value;
	var message="";
	var flag=true;
	if(rejectPendingAccountIdValidate==""){
		message="Please enter an Account ID.";
		flag=false;
	}
	if(flag== true){
		let data = {id: document.getElementById("rejectPendingAccountId").value};
		fetch('http://localhost:2222/p1.5/rejectPendingAccount', {
			method: 'POST',
			headers: {
			    'Content-Type': 'application/json',
			  },
			body: JSON.stringify(data)
		})
		.then(response => 
	//	console.log(response)
			{
				if(response.status == 501){
					response.text().then(message => {
						document.getElementById("rejectPendingAccountMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
					});
				} else {
					response.text().then(message => {
						document.getElementById("rejectPendingAccountMessage").innerHTML = "<span style='color: green;'>"+message+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("rejectPendingAccountMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function viewCustomerAccount(){
	let viewCustomerAccountIdValidate=document.getElementById("viewCustomerAccountId").value;
	var message="";
	var flag=true;
	if(viewCustomerAccountIdValidate==""){
		message="Please enter a Customer ID.";
		flag=false;
	}
	if(flag== true){
		let data = {id: document.getElementById("viewCustomerAccountId").value};
		fetch('http://localhost:2222/p1.5/viewCustomersAccounts', {
			method: 'POST',
			headers: {
			    'Content-Type': 'application/json',
			  },
			body: JSON.stringify(data)
		})
		.then(response => 
	//	console.log(response)
			{
				if(response.status == 501){
					response.text().then(message => {
						document.getElementById("allCustomersAccountsOutput").innerHTML = "<span style='color: red;'>"+message+"</span>";
					});
				} else {
					response.json().then(data => {
						printTable="<table class='accountsTable'><tr>" +
				  		"<th>Account ID</th>" +
				  		"<th>Initial Balance</th>" +
				  		"<th>Account Owner's ID</th>" +
				  		"<th>Status</th>" +
				  		"</tr>";
					  data.forEach(a => {
						  console.log(a.id);
						  printTable = printTable + `<tr><td>${a.id}</td>
						  <td>${a.balance}</td>
						  <td>${a.userid}</td>
						  <td>${parseStatus(a.status)}</td></tr>`;
					  });
					  printTable = printTable + "</table>";
					  document.getElementById("allCustomersAccountsOutput").innerHTML=printTable;
					});
				}
			}
		)
	} else {
		document.getElementById("allCustomersAccountsOutput").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function parseStatus(statusID){
	switch(statusID) {
	  case 0:
	    return "REJECTED";
	    break;
	  case 1:
		return "PENDING";
	    break;
	  case 2:
		return "APPROVED";
		break;
	  default:
		return "ERROR";
	}
}

function parseType(statusType){
	switch(statusType) {
	  case 1:
	    return "DEPOSIT";
	    break;
	  case 2:
		return "WITHDRAW";
	    break;
	  case 3:
		return "TRANSFER";
		break;
	  default:
		return "ERROR";
	}
}
