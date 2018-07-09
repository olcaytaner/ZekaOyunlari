package com.zekaoyunlari;

/**
 * User: Olcay
 * Date: Jun 3, 2007
 * Time: 4:27:35 PM
 */
public class EmptyListException extends RuntimeException{
    public EmptyListException(){
        super("List is empty");
    }
}
