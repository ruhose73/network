package com.company;

import java.io.*;
import java.net.*;

class Client
{
    public static void main(String data[])
    {
        String ipAddress="localhost";

        int portNumber=Integer.parseInt("5001");
        int rollNumber=Integer.parseInt("101");

        String message="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String request=rollNumber+","+message+",#";

        try
        {
            Socket socket=new Socket(ipAddress , portNumber);

            OutputStream outputStream;
            OutputStreamWriter outputStreamWriter;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            StringBuffer stringBuffer;
            String response;
            int x;
            outputStream=socket.getOutputStream();
            outputStreamWriter=new OutputStreamWriter(outputStream);
            outputStreamWriter.write(request);
            outputStreamWriter.flush(); // request is sent
            inputStream=socket.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            stringBuffer=new StringBuffer();
            while(true)
            {
                x=inputStreamReader.read();
                if(x=='#' || x==-1) break; // reads till the terminator
                stringBuffer.append((char)x);
            }
            response=stringBuffer.toString();
            System.out.println(response);
            socket.close(); //closing the connection
        }catch(Exception exception)
        {
// Raised in case, connection is refused or some other technical issue
            System.out.println(exception);
        }
    }
}