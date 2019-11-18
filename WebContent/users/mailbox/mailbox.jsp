<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="messages.MailMessage" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath }../../css/mailbox.css">
</head>
  <jsp:include page="../../Header.jsp" />
  <body>
  
  <%
  	String MessagesParamName = request.getServletContext().getInitParameter("message_array");
  
  	ArrayList<MailMessage> messages = new ArrayList<MailMessage>();
  
  	Object msgs = request.getSession().getAttribute(MessagesParamName);
  	
  	if (msgs != null && msgs instanceof ArrayList) {
  		messages = (ArrayList) msgs;
  	}
  	

  
  %>
    <jsp:include page="../../Navigation.jsp" />
    <!-- BREADCRUMB -->
    <div id="breadcrumb">
      <div class="container">
        <ul class="breadcrumb">
          <li><a href="${pageContext.request.contextPath}">Home</a></li>
          <li class="active">Messages</li>
        </ul>
      </div>
    </div>
    <!-- /BREADCRUMB -->

    <!-- section -->
    <div class="section">
      <!-- container -->
      <div class="container">
        <!-- row -->
        <div class="row">
            <div class="container">
                 <div class="mail-box">
                                  <aside class="sm-side">
                                      <div class="">
                                          <!-- Modal: Compose Message -->
                                          <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="composeModal" class="modal fade" style="display: none;">
                                              <div class="modal-dialog">
                                                  <div class="modal-content">
                                                      <div class="modal-header">
                                                          <button aria-hidden="true" data-dismiss="modal" class="close" type="button">X</button>
                                                          <h4 class="modal-title">Compose</h4>
                                                      </div>
                                                      <div class="modal-body">
                                                          <form role="form" class="form-horizontal" action="${pageContext.request.contextPath}/sendMessage.main" method="post">
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label" id="sendTo">To</label>
                                                                  <div class="col-lg-10">
                                                                      <input type="text" placeholder="" name="recipientEmail" id="recipientEmail" class="form-control">
                                                                  </div>
                                                              </div>
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">Subject</label>
                                                                  <div class="col-lg-10">
                                                                      <input type="text" placeholder="" name="subject" id="subject" class="form-control">
                                                                  </div>
                                                              </div>
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">Message</label>
                                                                  <div class="col-lg-10">
                                                                      <textarea rows="10" cols="30" class="form-control" id="messageContent" name="messageContent"></textarea>
                                                                  </div>
                                                              </div>
                
                                                              <div class="form-group">
                                                                  <div class="col-lg-offset-2 col-lg-10">
                                                                            
                                                                      <button class="main-btn" type="submit">Send</button>
                                                                  </div>
                                                              </div>
                                                          </form>
                                                      </div>
                                                  </div><!-- /.modal-content -->
                                              </div><!-- /.modal-dialog -->
                                          </div><!-- /.modal -->
                                          
                                          <!-- Modal: Read Message -->
                                          <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="readModal" class="modal fade" style="display: none;">
                                              <div class="modal-dialog">
                                                  <div class="modal-content">
                                                      <div class="modal-header">
                                                          <button aria-hidden="true" data-dismiss="modal" class="close" type="button">X</button>
                                                          <h4 class="modal-title">Message Title Here</h4>
                                                      </div>
                                                      <div class="modal-body">
                                                          <form role="form" class="form-horizontal" action="${pageContext.request.contextPath}/deleteMessage.main" method="post">
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">Sender:</label>
                                                                  <div class="col-lg-10">
                                                                  	<textarea readonly class="form-control" rows="1" cols="30" id="sender" name="sender"></textarea>
                                                                  </div>                                
                                                              </div>
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">Message Content:</label>
                                                                  <div class="col-lg-10">
                                                                      <textarea readonly rows="10" cols="30" class="form-control" id="message_content" name="message_content"></textarea>
                                                                  </div>
                                                              </div>
                                                              <div class="form-group">
                                                                  <div class="col-lg-10">
                                                                      <input type="text" placeholder="" name="message-id" id="message-id" style="display:none">
                                                                  </div>
                                                              </div>
                
                                                              <div class="form-group">
                                                                  <div class="col-lg-offset-2 col-lg-10">
                                                                  
                                                                  <!-- TODO: Complete Reply button --> 
                                                                                                                                   
                                                                      <button type="button" class="main-btn" id="reply-btn" data-toggle="modal">Reply</button>
                                                                      <button type="submit" class="primary-btn" id="delete-btn">Delete</button>
                                                                  </div>
                                                              </div>
                                                          </form>
                                                      </div>
                                                  </div><!-- /.modal-content -->
                                              </div><!-- /.modal-dialog -->
                                          </div><!-- /.modal -->
                                          
                                      </div>
                
                                  </aside>
                                  <aside class="lg-side">
                                      <div class="inbox-head">
                                          <h3 style="float: left;">Messages</h3>
                                          <form role="form" class="form-horizontal" action="${pageContext.request.contextPath}/readMessage.main" method="get">
                                          	<button class="main-btn" style = "float:right;" title="Load"> Load </button>
                                          </form>
                                          <button class="main-btn" style="float: right;" a href="#composeModal" data-toggle="modal"  title="Compose" id="Compose">
                                                Compose
                                          </button>
                                          <p>
                                      </div>
                                      <br>
                                      <div class="">
                                         <div class="mail-option">
                                         </div>
                                          <table class="table table-inbox table-hover">
                                            <tbody>
                                          <tr class="message">
                                          	<th class="view-message dont-show">Sender</th>
                                          	<th class="view message ">Subject</th>
                                          	<th class="view-message inbox-small-cells"></th>
                                          	<th class="view-message text-right" style="display:none"></th>
                                          </tr>
                                            <%
                                            	for (MailMessage message: messages) {
                                            		
                                            %>
                                              <tr class="message" a href="#readModal" data-toggle="modal" data-id=<%=message.getId() %> data-content="<%= message.getMessageContent() %>" data-subject="<%= message.getSubject() %>" data-sender="<%=message.getSenderName()%>" data-email="<%=message.getSenderEmail() %>">                                             
                                                  <td class="view-message  dont-show"><%= message.getSenderName() %></td>
                                                  <td class="view-message "><%= message.getSubject() %></td>
                                                  <td class="view-message  inbox-small-cells"></td>
                                                  <td class="view-message  text-right" id= "message-index" style="display:none"><%= messages.indexOf(message) %></td>
                                              </tr>   
                                             <% } %>                                   
                                          </tbody>
                                          </table>
                                      </div>
                                  </aside>
                              </div>
                </div>
        </div>
        <!-- /row -->
      </div>
      <!-- /container -->
    </div>
    <!-- /section -->

    <!-- FOOTER -->
    <footer id="footer" class="section section-grey">
      <!-- container -->
      <div class="container">
        <!-- row -->
        <div class="row">
          <!-- footer widget -->
          <div class="col-md-3 col-sm-6 col-xs-6">
            <div class="footer">
              <!-- footer logo -->
              <div class="footer-logo">
                <a class="logo" href="#">
                  <img src="./img/logo.png" alt="" />
                </a>
              </div>
              <!-- /footer logo -->

              <p>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                eiusmod tempor incididunt ut labore et dolore magna
              </p>

              <!-- footer social -->
              <ul class="footer-social">
                <li>
                  <a href="#"><i class="fa fa-facebook"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fa fa-twitter"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fa fa-instagram"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fa fa-google-plus"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fa fa-pinterest"></i></a>
                </li>
              </ul>
              <!-- /footer social -->
            </div>
          </div>
          <!-- /footer widget -->

          <!-- footer widget -->
          <div class="col-md-3 col-sm-6 col-xs-6">
            <div class="footer">
              <h3 class="footer-header">My Account</h3>
              <ul class="list-links">
                <li><a href="#">My Account</a></li>
                <li><a href="#">My Wishlist</a></li>
                <li><a href="#">Checkout</a></li>
                <li><a href="#">Login</a></li>
              </ul>
            </div>
          </div>
          <!-- /footer widget -->

          <div class="clearfix visible-sm visible-xs"></div>

          <!-- footer widget -->
          <div class="col-md-3 col-sm-6 col-xs-6">
            <div class="footer">
              <h3 class="footer-header">Customer Service</h3>
              <ul class="list-links">
                <li><a href="#">About Us</a></li>
                <li><a href="#">Shiping & Return</a></li>
                <li><a href="#">Shiping Guide</a></li>
                <li><a href="#">FAQ</a></li>
              </ul>
            </div>
          </div>
          <!-- /footer widget -->
        </div>
        <!-- /row -->
        <hr />
        <!-- row -->
        <div class="row">
          <div class="col-md-8 col-md-offset-2 text-center">
            <!-- footer copyright -->
            <div class="footer-copyright">
              <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
              Copyright &copy;<script>
                document.write(new Date().getFullYear());
              </script>
              All rights reserved | This template is made with
              <i class="fa fa-heart-o" aria-hidden="true"></i> by
              <a href="https://colorlib.com" target="_blank">Colorlib</a>
              <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
            </div>
            <!-- /footer copyright -->
          </div>
        </div>
        <!-- /row -->
      </div>
      <!-- /container -->
    </footer>
    <!-- /FOOTER -->

    <!-- jQuery Plugins -->
    <script src="../../js/jquery.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/slick.min.js"></script>
    <script src="../../js/nouislider.min.js"></script>
    <script src="../../js/jquery.zoom.min.js"></script>
    <script src="../../js/main.js"></script>
    <script src="../../js/modal.js"></script>
  </body>
</html>
