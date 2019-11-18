// For loading modals /////////////////
var modal = document.getElementById("composeModal");
var btn = document.getElementById("Compose");
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

var content;
var sender;
var subject;
var email;
var id;

// Reply Button
$("#reply-btn").click(function() {
     $("#readModal").modal('hide');
    $("#composeModal").modal('show');
    
    $('#recipientEmail').val(email);
    $('input#subject').val("Re:"+subject)
});

// Pass Data to Modal Popup
$('#readModal').on('show.bs.modal', function (e) {
	
	content = $(e.relatedTarget).data('content');
	sender = $(e.relatedTarget).data('sender');
	subject = $(e.relatedTarget).data('subject');
	email = $(e.relatedTarget).data('email');
	id = $(e.relatedTarget).data('id');
	
	$(e.currentTarget).find('h4').html(subject);
	$(e.currentTarget).find('textarea').first().val(sender);
	$(e.currentTarget).find('textarea').last().val(content);
    $(e.currentTarget).find('#message-id').val(id);
})



