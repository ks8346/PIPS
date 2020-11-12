package com.soprabanking.ips.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.services.CommentService;
/**
 * 
 * 
 *Comments Controller
 * 
 * 		This is a controller class for comments to be seen on proposals.
 * 		The CommentController Class implements an application which does the following
 * 		<br>
 * 		<ul>
 * 			<li>add comment <br></li>
 * 			<li>delete comment <br></li>
 * 			<li>show all comments <br></li>
 * 		</ul>
 * 
 * @author mojha
 * <br>
 * 	@see org.springframework.web.bind.annotation.RestController
 * 	@see org.springframework.web.bind.annotation.CrossOrigin
 * 	@see org.springframework.web.bind.annotation.RequestMapping
 * 	@see org.springframework.beans.factory.annotation.Autowired
 * 	@see org.springframework.web.bind.annotation.PostMapping
 */


@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    private static final Logger LOGGER = LogManager.getLogger(CommentController.class);
    /**
     * This is a method used to Display Comments in a List which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>commentService : an Object of the CommentService<br></li>
     * 			<li>displayComments : a method in class CommentService called by object commentService<br></li>
     * </ul>
     * @param body of string type containing the comment object
     * @return Response Entity with a list of comments from commentService with a OK Http Status
     * @see com.soprabanking.ips.services.CommentService
     * @see com.soprabanking.ips.models.Comment
     * 
     */

    @PostMapping(value = "/all", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> displayComments(@RequestBody String body) {

        try {
            LOGGER.info("Inside CommentController: displayComments() method");
            List<Comment> comments=commentService.displayComments(body);
            LOGGER.info("Inside CommentController: displayComments() SUCCESS");
            return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Inside CommentController: displayComments() FAILURE");
            return new ResponseEntity<List<Comment>>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * This is a method used to Add comment in a string which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status 
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>commentService : an Object of the CommentService<br></li>
     * 			<li> addComment : a method in class CommentService called by object commentService<br></li>
     * </ul>
     * @param body of string type containing the comment object
     * @return Response Entity with a Success message in String with a OK Http Status
     * @see com.soprabanking.ips.services.CommentService
     * 
     */
    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addComment(@RequestBody String body) {
        try {
            LOGGER.info("Inside CommentController: addComment() method");
            commentService.addComment(body);
            LOGGER.info("Inside CommentController: addComment() SUCCESS");
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

        } catch (Exception ex) {
            LOGGER.error("Inside CommentController: addComment() FAILURE");
            return new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
        }
    }
    /**
     * This is a method used to Add comment in a string which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>commentService : an Object of the CommentService<br></li>
     * 			<li> deleteComment : a method in class CommentService called by object commentService<br></li>
     * </ul>
     * @param body of string type containing the comment object
     * @return Response Entity with a Success message in String with a OK Http Status
     * @see com.soprabanking.ips.services.CommentService     
     * */
    @PostMapping(value = "/delete", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteComment(@RequestBody String body) {

        try {
            LOGGER.info("Inside CommentController: deleteComment() method");
            commentService.deleteComment(body);
            LOGGER.info("Inside CommentController: deleteComment() SUCCESS");
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

        } catch (Exception ex) {
            LOGGER.error("Inside CommentController: deleteComment() FAILURE");
            return new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
