<%-- 
    Document   : homepageFooter
    Created on : Oct 2, 2025, 4:04:37 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<footer>
    <div class="footer-top">
        <div class="pt-exebar">
            <div class="container">
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-12 col-sm-12 footer-col-4">
                    <div class="widget">
                        <h5 class="footer-title">Sign Up For A Newsletter</h5>
                        <p class="text-capitalize m-b20">Be the first to know about our hiring opportunities.</p>
                        <div class="subscribe-form m-b20">
                            <form class="subscription-form" action="http://educhamp.themetrades.com/demo/assets1/script/mailchamp.php" method="post">
                                <div class="ajax-message"></div>
                                <div class="input-group">
                                    <input name="email" required="required"  class="form-control" placeholder="Your Email Address" type="email">
                                    <span class="input-group-btn">
                                        <button name="submit" value="Submit" type="submit" class="btn"><i class="fa fa-arrow-right"></i></button>
                                    </span> 
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-lg-5 col-md-7 col-sm-12">
                    <div class="row">
                        <div class="col-6 col-lg-6 col-md-6 col-sm-6">
                            <div class="widget footer_widget">
                                <h5 class="footer-title">Company</h5>
                                <ul>
                                    <li><a href="homepage">Home</a></li>
                                    <li><a href="javascript:;">About</a></li>
                                    <li><a href="javascript:;">FAQs</a></li>
                                    <li><a href="javascript:;">Contact</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-6 col-lg-6 col-md-6 col-sm-6">
                            <div class="widget footer_widget">
                                <h5 class="footer-title">Get In Touch</h5>
                                <ul>
                                    <li><a href="javascript:;">News</a></li>
                                    <li><a href="javascript:;">Portfolio</a></li>
                                    <li><a href="javascript:;">Event</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-lg-3 col-md-5 col-sm-12 footer-col-4">
                    <div class="widget widget_gallery gallery-grid-4">
                        <h5 class="footer-title">Our Gallery</h5>
                        <ul class="magnific-image">
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic1.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic1.jpg" alt=""></a></li>
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic2.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic2.jpg" alt=""></a></li>
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic3.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic3.jpg" alt=""></a></li>
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic4.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic4.jpg" alt=""></a></li>
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic5.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic5.jpg" alt=""></a></li>
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic6.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic6.jpg" alt=""></a></li>
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic7.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic7.jpg" alt=""></a></li>
                            <li><a href="${pageContext.request.contextPath}/assets1/images/gallery/pic8.jpg" class="magnific-anchor"><img src="${pageContext.request.contextPath}/assets1/images/gallery/pic8.jpg" alt=""></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
