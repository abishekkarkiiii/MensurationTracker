package Tracker.Mensuration.Model;


import jdk.jshell.JShell;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AppModel {

      HttpRequest request=HttpRequest.newBuilder().GET().uri(URI.create("")).build();


    {
        try {
           HttpResponse<String>response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
              System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
