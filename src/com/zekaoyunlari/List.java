package com.zekaoyunlari;

/**
 * User: Olcay
 * Date: Jun 3, 2007
 * Time: 4:03:30 PM
 */
public class List {
    public ListNode firstNode;
    public ListNode lastNode;

    public List(){
        firstNode = lastNode = null;
    }

    public synchronized boolean isEmpty(){
        return firstNode == null;
    }

    public synchronized void insertFront(Object item){
        if (isEmpty()){
            firstNode = lastNode = new ListNode(item);
        }
        else{
            firstNode = new ListNode(item, firstNode);
        }
    }

    public synchronized void insertBack(Object item){
        if (isEmpty()){
            firstNode = lastNode = new ListNode(item);
        }
        else{
            lastNode = lastNode.nextNode = new ListNode(item);
        }
    }

    public synchronized Object removeFront() throws EmptyListException{
        if (isEmpty()){
            throw new EmptyListException();
        }
        Object item = firstNode.data;
        if (firstNode == lastNode){
            firstNode = lastNode = null;
        }
        else{
            firstNode = firstNode.nextNode;
        }
        return item;
    }

    public synchronized void removeAfter(ListNode prevnode){
        if (prevnode == null){
            removeFront();
        }
        else{
            if (prevnode.nextNode == lastNode){
                lastNode = prevnode;
            }
            prevnode.nextNode = prevnode.nextNode.nextNode;
        }
    }

    public synchronized int elementCount(){
        int count = 0;
        ListNode current = firstNode;
        while (current != null){
            current = current.nextNode;
            count++;
        }
        return count;
    }

    public synchronized Object removeBack() throws EmptyListException{
        if (isEmpty()){
            throw new EmptyListException();
        }
        Object item = lastNode.data;
        if (firstNode == lastNode){
            firstNode = lastNode = null;
        }
        else{
            ListNode current = firstNode;
            while (current.nextNode != lastNode){
                current = current.nextNode;
            }
            lastNode = current;
            current.nextNode = null;
        }
        return item;
    }

}
