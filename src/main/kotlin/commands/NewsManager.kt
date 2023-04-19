package commands

import org.json.JSONObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class NewsManager {
    private val  httpClient = HttpClient.newHttpClient()

    fun default(){
        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://newsapi.org/v2/everything?q=artificial%20intelligence&apiKey=8ff26db4ee184e4ca70681fd9da87a4e"))
            .GET()
            .build()

        val httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        val jsonResponse = JSONObject(httpResponse.body())
        val articles = jsonResponse.getJSONArray("articles")
        for (i in 0 until articles.length()) {
            val article = articles.getJSONObject(i)
            println("$i: ${article.getString("title")}")
        }
        println("What article would you like to read? (separated by spaces)")
        val selection = readln().split(" ")
        for(num in selection){
            val article = articles.getJSONObject(Integer.parseInt(num))
            println(num + ": " + article.getString("title") + "\n" + article.getString("url") + "\n")
        }
    }


}