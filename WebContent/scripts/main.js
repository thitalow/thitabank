// Disable form submissions if there are invalid fields, from https://www.w3schools.com/bootstrap4/bootstrap_forms.asp

function validateLogin(){
	let userid=document.getElementById("loginUsername").value;
	let userpassword=document.getElementById("loginPassword").value;
	var message="";
	var flag=true;
	if(userid==""){
		message="Please enter a username.";
		flag=false;
	}
	if(flag==true){
		if(userpassword==""){
			message="Please enter a password.";
			flag=false;
		}
	}
	if(flag== true){
		let data = {username: document.getElementById("loginUsername").value,password: document.getElementById("loginPassword").value};
//		console.log(JSON.stringify(data));
		fetch('http://localhost:2222/p1.5/login', {
			redirect: 'follow',
			method: 'POST',
			headers: {
			    'Content-Type': 'application/json',
			  },
			body: JSON.stringify(data)
		})
		.then(response => 
//		console.log(response)
			{
				if(response.status == 501){
					response.text().then(innerMessage => {
						document.getElementById("loginMessage").innerHTML = "<span style='color: red;'>"+innerMessage+"</span>";
					});
				} else {
					window.location.href = response.url;
				}
			}
		)
	} else {
		document.getElementById("loginMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}
}

function validateRegister(){
	let registerid=document.getElementById("registerUsername").value;
	let registerpassword=document.getElementById("registerPassword").value;
	let registerCode=document.getElementById("registerCode").value;
	var message="";
	var flag=true;
	if(registerid==""){
		message="Please enter a username.";
		flag=false;
	}
	if(flag==true){
		if(registerpassword==""){
			message="Please enter a password.";
			flag=false;
		}
    }
	if(flag== true){
		let data = {code: document.getElementById("registerCode").value,name: document.getElementById("registerName").value,username: document.getElementById("registerUsername").value,password: document.getElementById("registerPassword").value};
//		console.log(JSON.stringify(data));
		fetch('http://localhost:2222/p1.5/register', {
			method: 'POST',
			headers: {
			    'Content-Type': 'application/json',
			  },
			body: JSON.stringify(data)
		})
		.then(response => 
//		console.log(response)
			{
				if(response.status == 501){
					response.text().then(innerMessage => {
						document.getElementById("registerMessage").innerHTML = "<span style='color: red;'>"+innerMessage+"</span>";
					});
				} else {
					response.text().then(innerMessage => {
						document.getElementById("registerMessage").innerHTML = "<span style='color: green;'>"+innerMessage+"</span>";
					});
				}
			}
		)
	} else {
		document.getElementById("registerMessage").innerHTML = "<span style='color: red;'>"+message+"</span>";
	}

}
