function getAllAccounts(){ 
  	fetch('http://localhost:2222/p1.5/account', {
		method:'GET'
	})
	  .then(response => 
	  	{
			if(response.status == 501){
				response.text().then(message => {
					document.getElementById("allAccount").innerHTML = "<span style='color: red;'>"+message+"</span>";
				});
			} else {
				response.json().then(data => {
					  printTable="<table class='accountsTable'><tr>" +
				  		"<th>Account ID</th>" +
				  		"<th>Balance</th>" +
				  		"</tr>";
					  data.forEach(a => {
//						  console.log(a.id);
						  printTable = printTable + `<tr><td>${a.id}</td>
						  	<td>${a.balance}</td></tr>`;
					  });
					  printTable = printTable + "</table>";
					  document.getElementById("allAccount").innerHTML=printTable;
				});
			}
	  	})
}

function applyForAccount(){
	let applyBalance=document.getElementById("applyAccountBalance").value;
	var message="";
	var flag=true;
	if(applyBalance=="" || applyBalance < 0){
		message="Please enter a positive amount.";
		flag=false;
	}
	if(flag== true){
		let data = {balance: document.getElementById("applyAccountBalance").value};
		fetch('http://localhost:2222/p1.5/account', {
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
					response.text().then(innerMessage => {
						document.getElementById("applyAccountMessage").innerHTML = "<span style='color: red;'>"+innerMessage+"</span>";
					});
				} else {
					response.text().then(innerMessage => {
						document.getElementById("applyAccountMessage").innerHTML = "<span style='color: green;'>"+innerMessage+"</span>";
					});
				}
			})
	} else {
		document.getElementById("applyAccountMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function deposit(){
	let depositIdValidate=document.getElementById("depositId").value;
	let depositAmountValidate=document.getElementById("despositAmount").value;
	var message="";
	var flag=true;
	if(depositIdValidate==""){
		message="Please enter an Account ID.";
		flag=false;
	}
	if(flag==true){
		if(depositAmountValidate==""){
			message="Please enter a positive amount.";
			flag=false;
		}
    }
	if(flag== true){
		let data = {id: document.getElementById("depositId").value,balance: document.getElementById("despositAmount").value};
		fetch('http://localhost:2222/p1.5/deposit', {
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
					response.text().then(innerMessage => {
						document.getElementById("depositMessage").innerHTML = "<span style='color: red;'>"+innerMessage+"</span>";
					});
				} else {
					response.text().then(innerMessage => {
						document.getElementById("depositMessage").innerHTML = "<span style='color: green;'>"+innerMessage+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("depositMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}


function withdraw(){
	let withdrawIdValidate=document.getElementById("withdrawId").value;
	let withdrawAmountValidate=document.getElementById("withdrawAmount").value;
	var message="";
	var flag=true;
	if(withdrawIdValidate==""){
		message="Please enter an Account ID.";
		flag=false;
	}
	if(flag==true){
		if(withdrawAmountValidate==""){
			message="Please enter a positive amount.";
			flag=false;
		}
    }
	if(flag== true){
		let data = {id: document.getElementById("withdrawId").value,balance: document.getElementById("withdrawAmount").value};
		fetch('http://localhost:2222/p1.5/withdraw', {
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
					response.text().then(innerMessage => {
						document.getElementById("withdrawMessage").innerHTML = "<span style='color: red;'>"+innerMessage+"</span>";
					});
				} else {
					response.text().then(innerMessage => {
						document.getElementById("withdrawMessage").innerHTML = "<span style='color: green;'>"+innerMessage+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("withdrawMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function getAllPendingTransfers(){
	fetch('http://localhost:2222/p1.5/transfer', {
		method:'GET'
	})
	  .then(response => 
	  	{
			if(response.status == 501){
				response.text().then(message => {
					document.getElementById("allPendingTransfers").innerHTML = "<span style='color: red;'>"+message+"</span>";
				});
			} else {
				response.json().then(data => {
					  printTable="<table class='accountsTable'><tr>" +
				  		"<th>Transaction ID</th>" +
				  		"<th>From Account</th>" +
				  		"<th>To Account</th>" +
				  		"<th>Amount</th>" +
				  		"</tr>";
					  data.forEach(a => {
						  console.log(a.id);
						  printTable = printTable + `<tr><td>${a.id}</td>
						  <td>${a.fromAcc}</td>
						  <td>${a.toAcc}</td>
						  <td>${a.amount}</td></tr>`;
					  });
				  printTable = printTable + "</table>";
				  document.getElementById("allPendingTransfers").innerHTML=printTable;
				});
			}
	  	})
}

function postTransfer(){
	let fromAccountValidate=document.getElementById("fromAccount").value;
	let toAccountValidate=document.getElementById("toAccount").value;
	let transferAmountValidate=document.getElementById("transferAmount").value;
	var message="";
	var flag=true;
	if(fromAccountValidate==""){
		message="Please enter an Transfer ID to transfer from.";
		flag=false;
	}
	if(flag==true){
		if(toAccountValidate==""){
			message="Please enter an Transfer ID to transfer to.";
			flag=false;
		}
    }
	if(flag==true){
		if(transferAmountValidate=="" || transferAmountValidate < 0){
			message="Please enter a positive amount.";
			flag=false;
		}
    }
	if(flag== true){
		let data = {fromAcc: document.getElementById("fromAccount").value,toAcc: document.getElementById("toAccount").value,amount: document.getElementById("transferAmount").value};
		fetch('http://localhost:2222/p1.5/transfer', {
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
						document.getElementById("postTransferMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
					});
				} else {
					response.text().then(message => {
						document.getElementById("postTransferMessage").innerHTML = "<span style='color: green;'>"+message+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("postTransferMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function acceptTransfer(){
	let acceptTransferIdValidate=document.getElementById("acceptTransferId").value;
	var message="";
	var flag=true;
	if(acceptTransferIdValidate==""){
		message="Please enter an Transfer ID.";
		flag=false;
	}
	if(flag== true){
		let data = {id: document.getElementById("acceptTransferId").value};	
		fetch('http://localhost:2222/p1.5/acceptTransfer', {
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
						document.getElementById("acceptTransferMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
					});
				} else {
					response.text().then(message => {
						document.getElementById("acceptTransferMessage").innerHTML = "<span style='color: green;'>"+message+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("acceptTransferMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function rejectTransfer(){
	let rejectTransferIdValidate=document.getElementById("rejectTransferId").value;
	var message="";
	var flag=true;
	if(rejectTransferIdValidate==""){
		message="Please enter an Transfer ID.";
		flag=false;
	}
	if(flag== true){
		let data = {id: document.getElementById("rejectTransferId").value};
		fetch('http://localhost:2222/p1.5/rejectTransfer', {
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
						document.getElementById("rejectTransferMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
					});
				} else {
					response.text().then(message => {
						document.getElementById("rejectTransferMessage").innerHTML = "<span style='color: green;'>"+message+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("rejectTransferMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}