package com.company;

import java.io.*;
import java.net.*;


class RequestProcessor extends Thread //for multi-threaded server
{
    private Socket socket;
    RequestProcessor(Socket socket)
    {
        this.socket=socket;
        start(); // will load the run method
    }
    public void run()
    {
        try
        {

            OutputStream outputStream;
            OutputStreamWriter outputStreamWriter;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            StringBuffer stringBuffer;
            String response;
            String request;
            int x;
            int temp1,temp2;
            String part1,part2;
            int rollNumber;
            String message;

            inputStream=socket.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            stringBuffer=new StringBuffer();
            while(true)
            {
                x=inputStreamReader.read();
                if(x=='#' || x==-1) break; //reads until terminator
                stringBuffer.append((char)x);
            }
            request=stringBuffer.toString();
            System.out.println("Request : "+request);

            temp1=request.indexOf(",");
            temp2=request.indexOf(",",temp1+1);
            part1=request.substring(0,temp1);
            part2=request.substring(temp1+1,temp2);
            rollNumber=Integer.parseInt(part1);
            message=part2;
            System.out.println("Roll number : "+rollNumber);
            System.out.println("Message : "+message);

            response="Data saved#";
            outputStream=socket.getOutputStream();
            outputStreamWriter=new OutputStreamWriter(outputStream);
            outputStreamWriter.write(response);
            outputStreamWriter.flush(); // response sent
            System.out.println("Response sent");
            socket.close(); //terminating connection
        }catch(Exception exception)
        {
            System.out.println(exception);
        }
    }
}
class Server
{
    private ServerSocket serverSocket;
    private int portNumber;
    Server(int portNumber)
    {
        this.portNumber=portNumber;
        try
        {
//Initiating ServerSocket with TCP port
            serverSocket=new ServerSocket(this.portNumber);
            startListening();
        }catch(Exception e)
        {
            System.out.println(e);
            System.exit(0);
        }
    }
    private void startListening()
    {
        try
        {
            Socket socket;
            while(true)
            {
                System.out.println("Server is listening on port : "+this.portNumber);
                socket=serverSocket.accept(); // server is in listening mode
                System.out.println("Request arrived..");
                new RequestProcessor(socket);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public static void main(String data[])
    {
        int portNumber=Integer.parseInt("5001");
        Server server =new Server(portNumber);
    }
}
