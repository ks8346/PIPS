package com.soprabanking.ips.helper;
/**
 * This is a helper class which contains the message which is used during the exception handling.
 * @author kavsharma
 */
public class Message {
	

    private String content;
    private String type;

    public Message(String content, String type) {
        super();
        this.content = content;
        this.type = type;
    }
    
    /**
     * Content of the exception message
     * @return Content of the message.
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Sets the content of the message
     * @param content:String text of the message
     */
    
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Retrieve the type pf the exception
     * @return String exception name
     */
    public String getType() {
        return type;
    }
    
    /**
     * Sets the particular exception
     * @param type:String text
     */
    public void setType(String type) {
        this.type = type;
    }


}
