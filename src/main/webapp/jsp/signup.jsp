<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>

/* Style the container for inputs */
.container {
	background-color: #f1f1f1;
	padding: 20px;
}

/* The message box is shown when the user clicks on the password field */
#message {
	display: none;
	background: #f1f1f1;
	color: #000;
	position: relative;
	padding: 20px;
	margin-top: 10px;
}

#message p {
	padding: 10px 35px;
	font-size: 18px;
}

/* Add a green text color and a checkmark when the requirements are right */
.valid {
	color: green;
}

.valid:before {
	position: relative;
	left: -35px;
	content: "✔";
}

/* Add a red text color and an "x" when the requirements are wrong */
.invalid {
	color: red;
}

.invalid:before {
	position: relative;
	left: -35px;
	content: "✖";
}
</style>
</head>
<body>

	<h3>Password Validation</h3>
	<p>Try to submit the form.</p>

	<form id="form">
		<table border="0" cellspacing="2" cellpadding="1">
			<tr>
				<td>Username:</td>
				<td><input type="text" id="usrname" name="usrname" required></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" id="psw" name="psw"
					pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
					title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
					required></td>
			</tr>
			<tr>
				<td>Retype Password:</td>
				<td><input type="password" id="psw2" name="psw2"
					pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
					title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
					required></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button"
					onclick="sendobject()" value="submit"></input></td>
			</tr>
		</table>
	</form>

	<div id="message">
		<h3>Password must contain the following:</h3>
		<p id="letter" class="invalid">
			A <b>lowercase</b> letter
		</p>
		<p id="capital" class="invalid">
			A <b>capital (uppercase)</b> letter
		</p>
		<p id="number" class="invalid">
			A <b>number</b>
		</p>
		<p id="length" class="invalid">
			Minimum <b>8 characters</b>
		</p>
		<p id="match" class="invalid">
			Passwords <b>don't match</b>
		</p>
	</div>

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		var password1 = document.getElementById("psw");
		var password2 = document.getElementById("psw2");
		var letter = document.getElementById("letter");
		var capital = document.getElementById("capital");
		var number = document.getElementById("number");
		var length = document.getElementById("length");
		var match = document.getElementById("match");
		var correctPassword = 0;

		// When the user clicks on the password field, show the message box
		password1.onfocus = function() {
			document.getElementById("message").style.display = "block";
		}

		password2.onfocus = function() {
			document.getElementById("message").style.display = "block";
		}

		// When the user clicks outside of the password field, hide the message box
		password1.onblur = function() {
			document.getElementById("message").style.display = "none";
		}

		password2.onblur = function() {
			document.getElementById("message").style.display = "none";
		}

		function passwordChecker() {
			correctPassword = 0;
			// Validate lowercase letters
			var lowerCaseLetters = /[a-z]/g;
			if (password1.value.match(lowerCaseLetters)) {
				letter.classList.remove("invalid");
				letter.classList.add("valid");
				correctPassword = correctPassword + 1;
			} else {
				letter.classList.remove("valid");
				letter.classList.add("invalid");
			}

			// Validate capital letters
			var upperCaseLetters = /[A-Z]/g;
			if (password1.value.match(upperCaseLetters)) {
				capital.classList.remove("invalid");
				capital.classList.add("valid");
				correctPassword = correctPassword + 1;
			} else {
				capital.classList.remove("valid");
				capital.classList.add("invalid");
			}

			// Validate numbers
			var numbers = /[0-9]/g;
			if (password1.value.match(numbers)) {
				number.classList.remove("invalid");
				number.classList.add("valid");
				correctPassword = correctPassword + 1;
			} else {
				number.classList.remove("valid");
				number.classList.add("invalid");
			}

			// Validate length
			if (password1.value.length >= 8) {
				length.classList.remove("invalid");
				length.classList.add("valid");
				correctPassword = correctPassword + 1;
			} else {
				length.classList.remove("valid");
				length.classList.add("invalid");
			}

			// Validate match

			if (password1.value == password2.value) {
				match.classList.remove("invalid");
				match.classList.add("valid");
				document.getElementById("match").innerHTML = "Passwords <b>match</b>";
				correctPassword = correctPassword + 1;
			} else {
				match.classList.remove("valid");
				match.classList.add("invalid");
				document.getElementById("match").innerHTML = "Passwords <b>don't match</b>";
			}

		}

		// When the user starts to type something inside the password field
		password1.onkeyup = passwordChecker;
		password2.onkeyup = passwordChecker;

		function sendobject() {
			if (correctPassword != 5) {
				alert("Passwords did not meet requirements");
				return;
			}

			var x = $("form").serializeArray();
			$.each(x, function(i, field) {
				if (field.name == 'usrname')
					clientId = field.value;

				if (field.name == 'psw')
					clientSecret = field.value;

			});

			var authorizationBasic = btoa(clientId + ':' + clientSecret);
			var postData = {}
			postData.username = clientId;
			var json = JSON.stringify(postData)

			$.ajax({
				type : 'POST',
				url : '/users/',
				dataType : "text",
				data : json,
				//contentType: 'application/x-www-form-urlencoded; charset=utf-8',
				xhrFields : {
					withCredentials : true
				},
				headers : {
					'Authorization' : 'Basic ' + authorizationBasic
				},
				success : function(result, status, error) {
					var obj = JSON.parse(result);
					console.log("--------");
					window.location.href = obj.URL;
					return true;
				},
				error : function(req, status, error) {
					alert(error);
					console.log("error");
					console.log(req);
					console.log(status);
					console.log(error);
					//window.location.reload();
					return true;
				}
			});
		}
	</script>

</body>
</html>