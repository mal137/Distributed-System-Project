package com.vs.smarthome;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HttpHandler{
    private Socket s;
    private String reqString;

    //for getting the input from client
    private BufferedReader m_in;

    //for sending output to client
    private BufferedWriter m_out;

    private ArrayList<String> m_lines;


    public HttpHandler(ArrayList<String> lines, Socket clientSocket) throws IOException {
        reqString = "";
        this.s = clientSocket;
        this.m_lines = lines;
        this.m_in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.m_out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    /**
     * Starting service for http
     */
    public void run() {

        try {
            parseIntoString();

            HttpRequest req = new HttpRequest(reqString);
            //Display Request
            req.printRequest();

            HttpResponse res = new HttpResponse(req, m_out, m_lines);

            closingIO();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseIntoString() throws IOException {
        //read request from br
        while (m_in.ready() || (reqString.length() == 0)) {
            reqString += (char) m_in.read();
        }
    }

    /**
     * Closing all buffer and sockets
     * @throws IOException Whenever the buffer is faulty
     */
    private void closingIO() throws IOException {
        //Closing in lexicography order
        m_out.close();
        m_in.close();
        s.close();
    }

    public Socket getSocket() {
        return s;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    public String getReqString() {
        return reqString;
    }

    public void setReqString(String reqString) {
        this.reqString = reqString;
    }

    public BufferedReader getIn() {
        return m_in;
    }

    public void setIn(BufferedReader m_in) {
        this.m_in = m_in;
    }

    public BufferedWriter getOut() {
        return m_out;
    }

    public void setOut(BufferedWriter m_out) {
        this.m_out = m_out;
    }
}
