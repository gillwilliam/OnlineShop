<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <jsp:include page="Header.jsp" />
  <body>
    <jsp:include page="Navigation.jsp" />
    <!-- BREADCRUMB -->
    <div id="breadcrumb">
      <div class="container">
        <ul class="breadcrumb">
          <li><a href="#">Home</a></li>
          <li class="active">Mailbox</li>
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
                                      <div class="inbox-body">
                                          <!-- Modal -->
                                          <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade" style="display: none;">
                                              <div class="modal-dialog">
                                                  <div class="modal-content">
                                                      <div class="modal-header">
                                                          <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                                                          <h4 class="modal-title">Compose</h4>
                                                      </div>
                                                      <div class="modal-body">
                                                          <form role="form" class="form-horizontal">
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">To</label>
                                                                  <div class="col-lg-10">
                                                                      <input type="text" placeholder="" id="inputEmail1" class="form-control">
                                                                  </div>
                                                              </div>
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">Cc / Bcc</label>
                                                                  <div class="col-lg-10">
                                                                      <input type="text" placeholder="" id="cc" class="form-control">
                                                                  </div>
                                                              </div>
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">Subject</label>
                                                                  <div class="col-lg-10">
                                                                      <input type="text" placeholder="" id="inputPassword1" class="form-control">
                                                                  </div>
                                                              </div>
                                                              <div class="form-group">
                                                                  <label class="col-lg-2 control-label">Message</label>
                                                                  <div class="col-lg-10">
                                                                      <textarea rows="10" cols="30" class="form-control" id="" name=""></textarea>
                                                                  </div>
                                                              </div>
                
                                                              <div class="form-group">
                                                                  <div class="col-lg-offset-2 col-lg-10">
                                                                      <span class="btn green fileinput-button">
                                                                        <i class="fa fa-plus fa fa-white"></i>
                                                                        <span>Attachment</span>
                                                                        <input type="file" name="files[]" multiple="">
                                                                      </span>
                                                                      <button class="btn btn-send" type="submit">Send</button>
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
                                          <h3 style="float: left;">Inbox</h3>
                                          <button class="main-btn" style="float: right;" a href="#myModal" data-toggle="modal"  title="Compose">
                                                Compose
                                          </button>
                                          <p>
                                      </div>
                                      <div class="inbox-body">
                                         <div class="mail-option">
                                         </div>
                                          <table class="table table-inbox table-hover">
                                            <tbody>
                                              <tr class="unread">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message  dont-show">PHPClass</td>
                                                  <td class="view-message ">Added a new class: Login Class Fast Site</td>
                                                  <td class="view-message  inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message  text-right">9:27 AM</td>
                                              </tr>
                                              <tr class="unread">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Google Webmaster </td>
                                                  <td class="view-message">Improve the search presence of WebSite</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">March 15</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">JW Player</td>
                                                  <td class="view-message">Last Chance: Upgrade to Pro for </td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">March 15</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Tim Reid, S P N</td>
                                                  <td class="view-message">Boost Your Website Traffic</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">April 01</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star inbox-started"></i></td>
                                                  <td class="view-message dont-show">Freelancer.com <span class="label label-danger pull-right">urgent</span></td>
                                                  <td class="view-message">Stop wasting your visitors </td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">May 23</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star inbox-started"></i></td>
                                                  <td class="view-message dont-show">WOW Slider </td>
                                                  <td class="view-message">New WOW Slider v7.8 - 67% off</td>
                                                  <td class="view-message inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message text-right">March 14</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star inbox-started"></i></td>
                                                  <td class="view-message dont-show">LinkedIn Pulse</td>
                                                  <td class="view-message">The One Sign Your Co-Worker Will Stab</td>
                                                  <td class="view-message inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message text-right">Feb 19</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Drupal Community<span class="label label-success pull-right">megazine</span></td>
                                                  <td class="view-message view-message">Welcome to the Drupal Community</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">March 04</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Facebook</td>
                                                  <td class="view-message view-message">Somebody requested a new password </td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">June 13</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Skype <span class="label label-info pull-right">family</span></td>
                                                  <td class="view-message view-message">Password successfully changed</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">March 24</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star inbox-started"></i></td>
                                                  <td class="view-message dont-show">Google+</td>
                                                  <td class="view-message">alireza, do you know</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">March 09</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star inbox-started"></i></td>
                                                  <td class="dont-show">Zoosk </td>
                                                  <td class="view-message">7 new singles we think you'll like</td>
                                                  <td class="view-message inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message text-right">May 14</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">LinkedIn </td>
                                                  <td class="view-message">Alireza: Nokia Networks, System Group and </td>
                                                  <td class="view-message inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message text-right">February 25</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="dont-show">Facebook</td>
                                                  <td class="view-message view-message">Your account was recently logged into</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">March 14</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Twitter</td>
                                                  <td class="view-message">Your Twitter password has been changed</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">April 07</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">InternetSeer Website Monitoring</td>
                                                  <td class="view-message">http://golddesigner.org/ Performance Report</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">July 14</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star inbox-started"></i></td>
                                                  <td class="view-message dont-show">AddMe.com</td>
                                                  <td class="view-message">Submit Your Website to the AddMe Business Directory</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">August 10</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Terri Rexer, S P N</td>
                                                  <td class="view-message view-message">Forget Google AdWords: Un-Limited Clicks fo</td>
                                                  <td class="view-message inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message text-right">April 14</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Bertina </td>
                                                  <td class="view-message">IMPORTANT: Don't lose your domains!</td>
                                                  <td class="view-message inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message text-right">June 16</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star inbox-started"></i></td>
                                                  <td class="view-message dont-show">Laura Gaffin, S P N </td>
                                                  <td class="view-message">Your Website On Google (Higher Rankings Are Better)</td>
                                                  <td class="view-message inbox-small-cells"></td>
                                                  <td class="view-message text-right">August 10</td>
                                              </tr>
                                              <tr class="">
                                                  <td class="inbox-small-cells"><i class="fa fa-star"></i></td>
                                                  <td class="view-message dont-show">Facebook</td>
                                                  <td class="view-message view-message">Alireza Zare Login faild</td>
                                                  <td class="view-message inbox-small-cells"><i class="fa fa-paperclip"></i></td>
                                                  <td class="view-message text-right">feb 14</td>
                                              </tr>
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
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/slick.min.js"></script>
    <script src="js/nouislider.min.js"></script>
    <script src="js/jquery.zoom.min.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>
