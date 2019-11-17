// Get the modal
var modal = document.getElementById("composeModal");

// Get the button that opens the modal
var btn = document.getElementById("Compose");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}


// Trying to create a success feedback message
function successMessage() {
	var modalDiv = document.getElementById("modal-body");
	modalDiv.html("Success.");
}


$('#readModal').on('show.bs.modal', function (e) {
	
	var content = $(e.relatedTarget).data('content');
	var sender = $(e.relatedTarget).data('sender');
	var subject = $(e.relatedTarget).data('subject');
	
	$(e.currentTarget).find('h4').html(subject);
	$(e.currentTarget).find('textarea').first().val(sender);
    $(e.currentTarget).find('textarea').last().val(content);
})



